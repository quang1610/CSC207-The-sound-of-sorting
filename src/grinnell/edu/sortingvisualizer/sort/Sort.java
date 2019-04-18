package grinnell.edu.sortingvisualizer.sort;

import grinnell.edu.sortingvisualizer.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Sort<T extends Comparable<T>> {
  private static Random r = new Random();

  public static <T extends Comparable<T>> void sortEvent(T[] arr, List<SortEvent<T>> sortEventLst) {
    Iterator<SortEvent<T>> i = sortEventLst.iterator();
    while (i.hasNext()) {
      i.next().apply(arr);
    }
  }

  // *****************************************************************************************************************************************
 /** 
  * Insertion Sort
  * 
  * @param arr, array to be sorted
  * @return eventList
  * @pre arr is not empty
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  public static <T extends Comparable<T>> List<SortEvent<T>> insertionSort(T[] arr) {
    // create a new ArrayList
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;
    
    for (int i = 0; i < n; i++) {
      T key = arr[i];
      int j = i - 1;
      // while j is not less than 0 and key is smaller than arr[j]
      while (j >= 0 && key.compareTo(arr[j]) < 0) {
        // add a CompareEvent and a SwapEvent to eventList, swap arr[j] and arr[j + 1]
        eventList.add(createCompareEvent(i, j));
        swap(arr, j + 1, j);
        eventList.add(createSwapEvent(j + 1, j));
        j--;
      }
    }
    // return the record of comparisons and swaps
    return eventList;
  }

  // *****************************************************************************************************************************************
 /** 
  * Selection Sort
  * 
  * @param arr, array to be sorted
  * @return eventList
  * @pre arr is not empty
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  public static <T extends Comparable<T>> List<SortEvent<T>> selectionSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;

    for (int i = 0; i < n - 1; i++) {
      int minInd = i;
      for (int j = i + 1; j < n; j++) {
        // add CompareEvent to eventList
        eventList.add(createCompareEvent(minInd, j));
        // update minInd index if element at minInd is > element at j
        if (arr[minInd].compareTo(arr[j]) > 0) {
          minInd = j;
        }
      }
      // add SwapEvent to eventList and swap arr[minInd] with arr[i]
      eventList.add(createSwapEvent(minInd, i));
      swap(arr, minInd, i);
    }
    return eventList;
  }

  // *****************************************************************************************************************************************
 /** 
  * Merge Sort
  * 
  * @param arr, array to be sorted
  * @return eventList
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> List<SortEvent<T>> mergeSort(T[] arr) {
    // some set up. Temp is an array to reduce the space complexity, it is the temporary array to
    // write down the sorted part of the array.
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;
    T[] temp = (T[]) new Comparable[n];

    // start is inclusive, and end is exclusive.
    int start = 0;
    int end = n;
    int mid = (start + end) / 2;

    // if the array is empty, return the eventList
    if (end - start < 1) {
      return eventList;
      // otherwise, run the helper functions to sort and return the eventList
    } else {
      splitForMergeSort(arr, start, mid, temp, eventList);
      splitForMergeSort(arr, mid, end, temp, eventList);
      merge(arr, start, mid, end, temp, eventList);
      return eventList;
    }
  }

 /** 
  * Helper function for merge sort that recursively splits the array into subarrays
  * 
  * @param arr, array to be sorted
  * @param start, inclusive start index
  * @param end, exclusive end index
  * @param eventList
  * @pre start <= end
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  private static <T extends Comparable<T>> void splitForMergeSort(T[] arr, int start, int end,
      T[] temp, List<SortEvent<T>> eventList) {
    int mid = (start + end) / 2;
    
    // if the array is not empty, recursively split the list, then merge and sort
    if (end - start > 1) {
      splitForMergeSort(arr, start, mid, temp, eventList);
      splitForMergeSort(arr, mid, end, temp, eventList);
      merge(arr, start, mid, end, temp, eventList);
    }
  }

 /** 
  * Helper function for merge sort that merges already sorted subarrays
  * 
  * @param arr, array to be sorted
  * @param start, inclusive start index
  * @param mid, mid index
  * @param end, exclusive end index
  * @param temp
  * @param eventList
  * @return eventList
  * @pre start <= mid <= end < arr.length
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  private static <T extends Comparable<T>> void merge(T[] arr, int start, int mid, int end,
      T[] temp, List<SortEvent<T>> eventList) {
    int counter1 = 0;
    int counter2 = 0;

    // the loop continues while the functions apply on subsections of arr from start to mid 
    // and from mid to end
    while (start + counter1 < mid && mid + counter2 < end) {
      eventList.add(createCompareEvent(start + counter1, mid + counter2));
      // copy elements in a sorted order into the temp array
      if (arr[start + counter1].compareTo(arr[mid + counter2]) <= 0) {
        temp[start + counter1 + counter2] = arr[start + counter1];
        counter1++;
      } else {
        temp[start + counter1 + counter2] = arr[mid + counter2];
        counter2++;
      } // if/else
    } // while

    // if the first loop has finished with the mid to end subsection, finish copying sorted
    // elements from the start to mid subsection
    while (start + counter1 < mid) {
      temp[start + counter1 + counter2] = arr[start + counter1];
      counter1++;
    } // while

    // if the first loop has finished with the start to mid subsection, finish copying sorted
    // elements from the mid to end subsection
    while (mid + counter2 < end) {
      temp[start + counter1 + counter2] = arr[mid + counter2];
      counter2++;
    } // while

    // copy the contents of the temporary array into arr
    for (int i = 0; i < end - start; i++) {
      eventList.add(createCopyEvent(start + i, temp[start + i]));
      arr[start + i] = temp[start + i];
    } // for copy
  }

  // *****************************************************************************************************************************************
 /** 
  * Quick Sort
  * 
  * @param arr, array to be sorted
  * @return eventList
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  public static <T extends Comparable<T>> List<SortEvent<T>> quickSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    quickSortHelper(arr, 0, arr.length, eventList);
    return eventList;
  }

 /** 
  * Helper function for quick sort which sorts the given array
  * 
  * @param arr, array to be sorted
  * @param start, inclusive start index
  * @param end, exclusive end index
  * @param eventList
  * @return eventList
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  private static <T extends Comparable<T>> void quickSortHelper(T[] arr, int start, int end, List<SortEvent<T>> eventList) {
    // if the subsection from start to end contains only two elements, compare them and swap if necessary
    if (end - start == 2) {
      eventList.add(createCompareEvent(start, end - 1));
      if (arr[start].compareTo(arr[end - 1]) > 0) {
        eventList.add(createSwapEvent(start, end - 1));
        swap(arr, start, end - 1);
      }
      // otherwise, if the subsection contains more than two elements...
    } else if (end - start > 1) {
      // ...find the pivot index, and swap it to the end of the arr temporary
      int pivotInd = start + r.nextInt(end - start);
      int lastInd = end - 1;
      eventList.add(createSwapEvent(pivotInd, lastInd));
      swap(arr, pivotInd, lastInd);

      int left = start;
      int right = lastInd - 1;

      // check whether the right and left values are >, <, or == the pivot and swap/don't swap accordingly
      while (right - left > 1) {
        while (arr[left].compareTo(arr[lastInd]) < 0 && left < lastInd - 1) {
          eventList.add(createCompareEvent(left, lastInd));
          left++;
        }
        eventList.add(createCompareEvent(left, lastInd));
        while (arr[right].compareTo(arr[lastInd]) > 0 && right > 0) {
          eventList.add(createCompareEvent(right, lastInd));
          right--;
        }
        eventList.add(createCompareEvent(right, lastInd));
        if (left < right) {
          eventList.add(createSwapEvent(left, right));
          swap(arr, left, right);
        }
      }

      // once there is one remaining element in the subsection, check whether it needs to be swapped
      eventList.add(createCompareEvent(left, right));
      if (arr[left].compareTo(arr[right]) > 0 && right - left == 1) {
        eventList.add(createSwapEvent(left, right));
        swap(arr, left, right);
      }

      // swap the pivot into its place in the array
      if (arr[left].compareTo(arr[lastInd]) >= 0) {
        eventList.add(createCompareEvent(left, lastInd));
        eventList.add(createSwapEvent(left, lastInd));
        
        swap(arr, left, lastInd);
        right = left;
      } else if (arr[right].compareTo(arr[lastInd]) >= 0) {
        eventList.add(createCompareEvent(left, lastInd));
        eventList.add(createCompareEvent(right, lastInd));
        eventList.add(createSwapEvent(right, lastInd));
        
        swap(arr, right, lastInd);
      } else {
        eventList.add(createCompareEvent(left, lastInd));
        eventList.add(createCompareEvent(right, lastInd));
        
        right = lastInd;
      }

      // recurse until sorted
      quickSortHelper(arr, start, right, eventList);
      quickSortHelper(arr, right + 1, end, eventList);
    }
  }

  // ****************************************************************************************************************************************
  /** 
  * Bubble Sort
  * 
  * @param arr, array to be sorted
  * @return eventList
  * @post eventList contains CompareEvents and SwapEvents in the order they occurred
  * @post arr is a sorted permutation of the input arr
  */
  public static <T extends Comparable<T>> List<SortEvent<T>> bubbleSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;

    // check each element against every element with a higher index and swap/don't swap accordingly
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        eventList.add(createCompareEvent(j, j + 1));
        if (arr[j].compareTo(arr[j + 1]) > 0) {
          eventList.add(createSwapEvent(j, j + 1));
          swap(arr, j, j + 1);
        }
      }
    }
    return eventList;
  }

  // ****************************************************************************************************************************************
  // Helpers
  
  /** 
  * Helper function to swap elements
  * 
  * @param arr
  * @param i, an index
  * @param j, an index
  * @pre i and j are both < arr.length
  * @pre arr is not empty
  * @post the element that was at arr[j] is now at arr[i] and vice versa
  */
  private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
    T temp = arr[j];
    arr[j] = arr[i];
    arr[i] = temp;
  }

  /** 
  * Helper function to create CompareEvents
  * 
  * @param arr
  * @param firstInd, the index of an element being compared
  * @param secondInd, the index of an element being compared
  * @return cmpEvent
  * @pre firstInd and secondInd are both < arr.length
  * @pre arr is not empty
  * @post cmpEvent contains the indices of which elements were compared
  */
  private static <T extends Comparable<T>> CompareEvent<T> createCompareEvent(int firstInd,
      int secondInd) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);
    ind.add(secondInd);

    CompareEvent<T> cmpEvent = new CompareEvent<T>(ind);
    return cmpEvent;
  }

  /** 
  * Helper function to create SwapEvents
  * 
  * @param arr
  * @param firstInd, the index of an element being swapped
  * @param secondInd, the index of an element being swapped
  * @return swapEvent
  * @pre firstInd and secondInd are both < arr.length
  * @pre arr is not empty
  * @post swapEvent contains the indices of which elements were swapped
  */
  private static <T extends Comparable<T>> SwapEvent<T> createSwapEvent(int firstInd,
      int secondInd) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);
    ind.add(secondInd);

    SwapEvent<T> swapEvent = new SwapEvent<T>(ind);
    return swapEvent;
  }

  /** 
  * Helper function to create CopyEvents
  * 
  * @param arr
  * @param firstInd, the index of an element being copied
  * @param secondInd, the index of an element being copied
  * @return copyEvent
  * @pre firstInd and secondInd are both < arr.length
  * @pre arr is not empty
  * @post copyEvent contains the indices of which elements were copied
  */
  private static <T extends Comparable<T>> CopyEvent<T> createCopyEvent(int firstInd, T value) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);

    CopyEvent<T> copyEvent = new CopyEvent<T>(ind, value);
    return copyEvent;
  }
}
