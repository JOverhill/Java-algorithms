package oy.interact.tira.student;

import java.util.Comparator;

public class Algorithms {

   private Algorithms() {
      // nada
   }

   ///////////////////////////////////////////
   // Insertion Sort for the whole array
   ///////////////////////////////////////////

   public static <T extends Comparable<T>> void insertionSort(T[] array) {
      
      for (int inner = 1; inner < array.length; inner++) {
         T key = array[inner];
         int outer = inner - 1;
         while (outer >= 0 && key.compareTo(array[outer]) < 0) {
            array[outer + 1] = array[outer];
            outer--;
         }
         array[outer + 1] = key;
      }
   }

   ///////////////////////////////////////////
   // Insertion Sort for a slice of the array
   ///////////////////////////////////////////

   public static <T extends Comparable<T>> void insertionSort(T[] array, int fromIndex, int toIndex) {
      
      for (int inner = fromIndex; inner < toIndex; inner++) {
         T key = array[inner];
         int outer = inner - 1;
         while (outer >= 0 && key.compareTo(array[outer]) < 0) {
            array[outer + 1] = array[outer];
            outer--;
         }
         array[outer + 1] = key;

      }
   }

   //////////////////////////////////////////////////////////
   // Insertion Sort for the whole array using a Comparator
   //////////////////////////////////////////////////////////

   public static <T> void insertionSort(T[] array, Comparator<T> comparator) {
      
      for (int innerIndex = 1; innerIndex < array.length; innerIndex++) {
         T key = array[innerIndex];
         int outerIndex = innerIndex - 1;
         while (outerIndex >= 0 && comparator.compare(key, array[outerIndex]) < 0) {
            array[outerIndex + 1] = array[outerIndex];
            outerIndex--;
         }
         array[outerIndex + 1] = key;

      }
   }

   ////////////////////////////////////////////////////////////
   // Insertion Sort for slice of the array using a Comparator
   ////////////////////////////////////////////////////////////

   public static <T> void insertionSort(T[] array, int fromIndex, int toIndex, Comparator<T> comparator) {
      for (int innerIndex = fromIndex; innerIndex < toIndex; innerIndex++) {
         T key = array[innerIndex];
         int outerIndex = innerIndex - 1;
         while (outerIndex >= 0 && comparator.compare(key, array[outerIndex]) < 0) {
            array[outerIndex + 1] = array[outerIndex];
            outerIndex--;
         }
         array[outerIndex + 1] = key;

      }
   }

   ///////////////////////////////////////////
   // Reversing an array
   ///////////////////////////////////////////

   public static <T> void reverse(T[] array) {

      for(int i = 0; i < array.length / 2; i++) {
         T temp = array[i];
         array[i] = array[array.length - i - 1];
         array[array.length - i - 1] = temp;
      }
   }

   ///////////////////////////////////////////
   // Reversing a slice of an array
   ///////////////////////////////////////////

   public static <T> void reverse(T[] array, int fromIndex, int toIndex) {
      
      for (int index = fromIndex; index < toIndex / 2; index++) {
         T temp = array[index];
         array[index] = array[toIndex - index - 1];
         array[toIndex - index - 1] = temp;
      }
   }

   public static <T> void swap(T[] array, int first, int second) {
      T temp = array[first];
      array[first] = array[second];
      array[second] = temp;

   }


   ///////////////////////////////////////////
   // Binary search bw indices
   ///////////////////////////////////////////

   public static <T extends Comparable<T>> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
      
      if (toIndex >= fromIndex && fromIndex <= fromArray.length - 1) {
         int middleIndex = fromIndex + ( (toIndex - fromIndex) / 2 );
         
         if (aValue.compareTo(fromArray[middleIndex]) == 0) {
            return middleIndex;
         }
       
         if (aValue.compareTo(fromArray[middleIndex]) < 0) {
            return binarySearch(aValue, fromArray, fromIndex, middleIndex - 1);
         }
         else return binarySearch(aValue, fromArray, middleIndex + 1, toIndex);
      }
      return -1;
      
      
   }

   ///////////////////////////////////////////
   // Binary search using a Comparator
   ///////////////////////////////////////////

   public static <T> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex, Comparator<T> comparator) {
       if (toIndex >= fromIndex && fromIndex <= fromArray.length - 1) {
         int middleIndex = fromIndex + ( (toIndex - fromIndex) / 2 );
         
         if (comparator.compare(aValue, fromArray[middleIndex]) == 0) {
            return middleIndex;
         }
       
         if (comparator.compare(aValue, fromArray[middleIndex]) < 0) {
            return binarySearch(aValue, fromArray, fromIndex, middleIndex - 1, comparator);
         }
         else return binarySearch(aValue, fromArray, middleIndex + 1, toIndex, comparator);
      }
      
      
      return -1;
   }

   public static <E extends Comparable<E>> void fastSort(E [] array) {
      heapify(array, array.length -1);
      int end = array.length -1;
      while(end > 0) {
         swap(array, end, 0);
         end -= 1;
         siftDown(array, 0, end);
      }
   }

   public static <E> void fastSort(E [] array, Comparator<E> comparator) {
      heapify(array, array.length -1, comparator);
      int end = array.length -1;
      while(end > 0) {
         swap(array, end, 0);
         end -= 1;
         siftDown(array, 0, end, comparator);
      }
   }

   public static <E> void fastSort(E [] array, int fromIndex, int toIndex, Comparator<E> comparator) {
      heapify(array, toIndex, comparator);
      int end = toIndex;
      while(end > fromIndex) {
         swap(array, end, 0);
         end -= 1;
         siftDown(array, 0, end, comparator);
      }
   }

   public static <E extends Comparable<E>> void heapify(E [] array, int end) {
      int start = parent(end);
      while(start >= 0) {
         siftDown(array, start, end);
         start -= 1;
      }
   }
   public static <E> void heapify(E [] array, int end, Comparator<E> comparator) {
      int start = parent(end);
      while(start >= 0) {
         siftDown(array, start, end, comparator);
         start -= 1;
      }
   }
   

   private static int parent(int i) {
      return ((i-1)/2);
   }
   private static int leftChild(int i) {
      return 2 * i + 1;
   }
   private static int rightChild(int i) {
      return 2 * i +2;
   }

   private static <E extends Comparable<E>> void siftDown(E [] array, int start, int end) {
      int root = start;
      while (leftChild(root) <= end) {
         int child = leftChild(root);
         int swap = root;
         if(array[swap].compareTo(array[child]) < 0) {
            swap = child;
         }
         if(child + 1 <= end && array[swap].compareTo(array[child +1]) < 0) {
            swap = child +1;
         }
         if(swap == root) {
            return;
         }
         else{
            swap(array, root, swap);
            root = swap;
         }
      }
   }
   private static <E> void siftDown(E [] array, int start, int end, Comparator<E> comparator) {
      int root = start;
      while (leftChild(root) <= end) {
         int child = leftChild(root);
         int swap = root;
         if(comparator.compare(array[swap], array[child]) < 0) {
            swap = child;
         }
         if(child + 1 <= end && comparator.compare(array[swap], array[child +1]) < 0) {
            swap = child +1;
         }
         if(swap == root) {
            return;
         }
         else{
            swap(array, root, swap);
            root = swap;
         }
      }
   }
}
