package com.misanthropy.collections_of_optimizations.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public final class ParkingNotifier {
   private static final long MAX_PARK_NANOS = TimeUnit.MILLISECONDS.toNanos(50L);
   private final AtomicInteger generation = new AtomicInteger();
   private final ConcurrentLinkedQueue<Thread> parked = new ConcurrentLinkedQueue<>();

   public void await() {
      int seen = this.generation.get();
      Thread self = Thread.currentThread();
      this.parked.add(self);
      if (this.generation.get() == seen) {
         LockSupport.parkNanos(this, MAX_PARK_NANOS);
      }

      this.parked.remove(self);
   }

   public void post() {
      this.generation.incrementAndGet();
      Thread thread = this.parked.poll();
      if (thread != null) {
         LockSupport.unpark(thread);
      }
   }

   public void postAll() {
      this.generation.incrementAndGet();

      for (Thread thread : this.parked) {
         LockSupport.unpark(thread);
      }
   }

   public interface Holder {
      ParkingNotifier coo$parking();
   }
}
