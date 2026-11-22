package com.misanthropy.collections_of_optimizations.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.BasicVerifier;
import org.objectweb.asm.tree.analysis.Frame;

public final class HugeMethodSplitter {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static final int JIT_LIMIT = 8000;
   private static final int TARGET = 6000;

   private HugeMethodSplitter() {
   }

   public static void split(ClassNode owner) {
      List<MethodNode> added = new ArrayList<>();

      for (MethodNode method : new ArrayList<>(owner.methods)) {
         if ((method.access & 1280) == 0 && !method.name.startsWith("<") && Type.getReturnType(method.desc) == Type.VOID_TYPE) {
            int size = size(method.instructions.toArray(), 0, method.instructions.size());
            if (size > 8000) {
               try {
                  List<MethodNode> parts = trySplit(owner, method);
                  if (parts == null) {
                     LOGGER.info("Left {}.{} ({} bytes) whole, no safe place to cut it", owner.name, method.name, size);
                  } else {
                     added.addAll(parts);
                     LOGGER.info("Cut {}.{} ({} bytes, over the JIT limit) into {} compilable parts", owner.name, method.name, size, parts.size());
                  }
               } catch (Throwable var6) {
                  LOGGER.warn("Left {}.{} whole, splitting it failed", owner.name, method.name, var6);
               }
            }
         }
      }

      owner.methods.addAll(added);
   }

   private static List<MethodNode> trySplit(ClassNode owner, MethodNode method) throws Exception {
      AbstractInsnNode[] insns = method.instructions.toArray();
      int n = insns.length;
      Map<LabelNode, Integer> at = new HashMap<>();

      for (int i = 0; i < n; i++) {
         if (insns[i] instanceof LabelNode label) {
            at.put(label, i);
         }
      }

      boolean isStatic = (method.access & 8) != 0;
      Type[] args = Type.getArgumentTypes(method.desc);
      int paramSlots = isStatic ? 0 : 1;

      for (Type arg : args) {
         paramSlots += arg.getSize();
      }

      method.maxStack += 4;
      Frame<BasicValue>[] frames = new Analyzer(new BasicInterpreter()).analyze(owner.name, method);
      method.maxStack -= 4;
      List<int[]> edges = new ArrayList<>();
      List<int[]> successors = new ArrayList<>(n);

      for (int ix = 0; ix < n; ix++) {
         int[] next = successorsOf(insns, ix, at);
         if (next == null) {
            return null;
         }

         successors.add(next);

         for (int target : next) {
            if (target != ix + 1) {
               edges.add(new int[]{ix, target});
            }
         }
      }

      for (TryCatchBlockNode block : method.tryCatchBlocks) {
         int start = at.get(block.start);
         int end = at.get(block.end);
         int handler = at.get(block.handler);
         edges.add(new int[]{start, handler});
         edges.add(new int[]{Math.max(start, end - 1), handler});
         edges.add(new int[]{start, end});

         for (int ix = start; ix < end; ix++) {
            int[] old = successors.get(ix);
            int[] grown = Arrays.copyOf(old, old.length + 1);
            grown[old.length] = handler;
            successors.set(ix, grown);
         }
      }

      int[] crossing = new int[n + 2];

      for (int[] edge : edges) {
         int lo = Math.min(edge[0], edge[1]) + 1;
         int hi = Math.max(edge[0], edge[1]);
         if (lo <= hi) {
            crossing[lo]++;
            crossing[hi + 1]--;
         }
      }

      BitSet[] liveIn = liveness(insns, successors);
      boolean[] valid = new boolean[n];
      int running = 0;

      for (int p = 0; p < n; p++) {
         running += crossing[p];
         valid[p] = p > 0 && running == 0 && frames[p] != null && frames[p].getStackSize() == 0 && liveIn[p].nextSetBit(paramSlots) < 0;
      }

      int[] prefix = new int[n + 1];

      for (int ix = 0; ix < n; ix++) {
         prefix[ix + 1] = prefix[ix] + size(insns, ix, ix + 1);
      }

      List<int[]> segments = new ArrayList<>();
      int segStart = 0;
      int lastValid = -1;

      for (int ix = 0; ix < n; ix++) {
         if (valid[ix] && ix > segStart) {
            lastValid = ix;
         }

         if (prefix[ix + 1] - prefix[segStart] > 6000 && lastValid > segStart) {
            segments.add(new int[]{segStart, lastValid});
            segStart = lastValid;
            lastValid = -1;

            for (int j = segStart + 1; j <= ix; j++) {
               if (valid[j]) {
                  lastValid = j;
               }
            }
         }
      }

      segments.add(new int[]{segStart, n});
      if (segments.size() < 2) {
         return null;
      } else {
         String partDesc = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, args);
         int partAccess = 4098 | (isStatic ? 8 : 0);
         boolean itf = (owner.access & 512) != 0;
         List<MethodNode> parts = new ArrayList<>();
         InsnList body = new InsnList();

         for (int k = 0; k < segments.size(); k++) {
            int a = segments.get(k)[0];
            int b = segments.get(k)[1];
            String name = method.name + "$coo$part" + k;
            MethodNode part = new MethodNode(partAccess, name, partDesc, null, method.exceptions.toArray(new String[0]));
            Map<LabelNode, LabelNode> map = new HashMap<>();

            for (LabelNode label : at.keySet()) {
               map.put(label, new LabelNode());
            }

            for (int ix = a; ix < b; ix++) {
               AbstractInsnNode insn = insns[ix];
               if (!(insn instanceof FrameNode)) {
                  if (insn instanceof LineNumberNode) {
                     LineNumberNode line = (LineNumberNode)insn;
                     int labelAt = at.get(line.start);
                     if (labelAt < a || labelAt >= b) {
                        continue;
                     }
                  }

                  if (insn.getOpcode() == 177) {
                     part.instructions.add(new InsnNode(4));
                     part.instructions.add(new InsnNode(172));
                  } else {
                     part.instructions.add(insn.clone(map));
                  }
               }
            }

            part.instructions.add(new InsnNode(3));
            part.instructions.add(new InsnNode(172));

            for (TryCatchBlockNode block : method.tryCatchBlocks) {
               int start = at.get(block.start);
               if (start >= a && start < b) {
                  part.tryCatchBlocks.add(new TryCatchBlockNode(map.get(block.start), map.get(block.end), map.get(block.handler), block.type));
               }
            }

            part.maxStack = method.maxStack + 2;
            part.maxLocals = Math.max(method.maxLocals, paramSlots);
            new Analyzer(new BasicVerifier()).analyze(owner.name, part);
            parts.add(part);
            int slot = 0;
            if (!isStatic) {
               body.add(new VarInsnNode(25, 0));
               slot = 1;
            }

            for (Type arg : args) {
               body.add(new VarInsnNode(arg.getOpcode(21), slot));
               slot += arg.getSize();
            }

            body.add(new MethodInsnNode(isStatic ? 184 : 183, owner.name, name, partDesc, itf));
            LabelNode next = new LabelNode();
            body.add(new JumpInsnNode(153, next));
            body.add(new InsnNode(177));
            body.add(next);
         }

         body.add(new InsnNode(177));
         MethodNode rewritten = new MethodNode(method.access, method.name, method.desc, method.signature, method.exceptions.toArray(new String[0]));
         rewritten.instructions = body;
         rewritten.maxStack = paramSlots + 2;
         rewritten.maxLocals = Math.max(method.maxLocals, paramSlots);
         new Analyzer(new BasicVerifier()).analyze(owner.name, rewritten);
         method.instructions = body;
         method.tryCatchBlocks = new ArrayList();
         method.localVariables = null;
         method.visibleLocalVariableAnnotations = null;
         method.invisibleLocalVariableAnnotations = null;
         method.maxStack = rewritten.maxStack;
         method.maxLocals = rewritten.maxLocals;
         return parts;
      }
   }

   private static int[] successorsOf(AbstractInsnNode[] insns, int i, Map<LabelNode, Integer> at) {
      AbstractInsnNode insn = insns[i];
      int op = insn.getOpcode();
      if (op == 168 || op == 169) {
         return null;
      } else if (insn instanceof JumpInsnNode jump) {
         int target = at.get(jump.label);
         return op == 167 ? new int[]{target} : new int[]{i + 1, target};
      } else if (insn instanceof TableSwitchInsnNode table) {
         int[] out = new int[table.labels.size() + 1];
         out[0] = at.get(table.dflt);

         for (int k = 0; k < table.labels.size(); k++) {
            out[k + 1] = at.get(table.labels.get(k));
         }

         return out;
      } else if (insn instanceof LookupSwitchInsnNode lookup) {
         int[] out = new int[lookup.labels.size() + 1];
         out[0] = at.get(lookup.dflt);

         for (int k = 0; k < lookup.labels.size(); k++) {
            out[k + 1] = at.get(lookup.labels.get(k));
         }

         return out;
      } else if ((op < 172 || op > 177) && op != 191) {
         return i + 1 < insns.length ? new int[]{i + 1} : new int[0];
      } else {
         return new int[0];
      }
   }

   private static BitSet[] liveness(AbstractInsnNode[] insns, List<int[]> successors) {
      int n = insns.length;
      BitSet[] in = new BitSet[n];

      for (int i = 0; i < n; i++) {
         in[i] = new BitSet();
      }

      boolean changed = true;

      while (changed) {
         changed = false;

         for (int i = n - 1; i >= 0; i--) {
            BitSet out = new BitSet();

            for (int s : successors.get(i)) {
               if (s < n) {
                  out.or(in[s]);
               }
            }

            AbstractInsnNode insn = insns[i];
            if (insn instanceof VarInsnNode var) {
               int op = var.getOpcode();
               if (op >= 54 && op <= 58) {
                  out.clear(var.var);
               } else {
                  out.set(var.var);
               }
            } else if (insn instanceof IincInsnNode iinc) {
               out.set(iinc.var);
            }

            if (!out.equals(in[i])) {
               in[i] = out;
               changed = true;
            }
         }
      }

      return in;
   }

   private static int size(AbstractInsnNode[] insns, int from, int to) {
      int total = 0;

      for (int i = from; i < to; i++) {
         AbstractInsnNode insn = insns[i];
         int op = insn.getOpcode();
         if (op >= 0) {
            if (insn instanceof VarInsnNode var) {
               total += var.var < 4 ? 1 : (var.var < 256 ? 2 : 4);
            } else if (insn instanceof IntInsnNode) {
               total += op == 17 ? 3 : 2;
            } else if (insn instanceof IincInsnNode) {
               total += 3;
            } else if (insn instanceof TableSwitchInsnNode table) {
               total += 16 + 4 * table.labels.size();
            } else if (insn instanceof LookupSwitchInsnNode lookup) {
               total += 12 + 8 * lookup.labels.size();
            } else if (insn instanceof MethodInsnNode m) {
               total += m.getOpcode() == 185 ? 5 : 3;
            } else if (insn.getType() == 6) {
               total += 5;
            } else if (insn.getType() == 13) {
               total += 4;
            } else if (insn instanceof InsnNode) {
               total++;
            } else {
               total += 3;
            }
         }
      }

      return total;
   }
}
