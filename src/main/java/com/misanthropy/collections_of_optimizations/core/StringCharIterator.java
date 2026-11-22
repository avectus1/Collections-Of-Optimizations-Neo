package com.misanthropy.collections_of_optimizations.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class StringCharIterator implements Iterator<Character> {
   private final String text;
   private int index;

   public StringCharIterator(String text) {
      this.text = text;
   }

   @Override
   public boolean hasNext() {
      return this.index < this.text.length();
   }

   public Character next() {
      if (this.index >= this.text.length()) {
         throw new NoSuchElementException();
      } else {
         return this.text.charAt(this.index++);
      }
   }
}
