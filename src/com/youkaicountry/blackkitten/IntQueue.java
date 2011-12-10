// Copyright (c) <2011> <Nathaniel Caldwell>
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.youkaicountry.blackkitten;

public class IntQueue
{
   int buffer[];
   int bread, bwrite;

   public IntQueue()
   {
      this.buffer = new int[8];
      this.bread = 0;
      this.bwrite = 0;
      return;
   }

   public static final int NO_SUCH_ELEMENT = 0x80000000;

   public int read()
   {
      if (bread == bwrite)
         return NO_SUCH_ELEMENT;
      int result = buffer[bread++];
      if (bread >= buffer.length)
         bread -= buffer.length;
      return result;
   }

   public void write(int x)
   {
      buffer[bwrite++] = x;
      if (bwrite >= buffer.length)
         bwrite -= buffer.length;
      if (bwrite == bread)
      {
         int temp[] = new int[buffer.length*2];
         System.arraycopy(buffer, bread, temp, 0, buffer.length-bread);
         System.arraycopy(buffer, 0, temp, buffer.length-bread, bread);
         bread = 0;
         bwrite = buffer.length;
         buffer = temp;
      }
      return;
   }

   public boolean isEmpty()
   {
      return (bwrite == bread);
   }

   public int[] toArray()
   {
      int result[];
      if (bwrite < bread)
      {
         result = new int[buffer.length-bread+bwrite];
         System.arraycopy(buffer, bread, result, 0, buffer.length-bread);
         System.arraycopy(buffer, 0, result, buffer.length-bread, bwrite);
      }
      else
      {
         result = new int[bwrite-bread];
         System.arraycopy(buffer, bread, result, 0, bwrite-bread);
      }
      return result;
   }
   
   public void clear()
   {
       bwrite = bread;
   }
}