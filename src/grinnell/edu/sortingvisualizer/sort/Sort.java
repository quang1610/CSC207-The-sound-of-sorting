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
  // Insertion Sort
  public static <T extends Comparable<T>> List<SortEvent<T>> insertionSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      T key = arr[i];
      int j = i - 1;
      while (j >= 0 && key.compareTo(arr[j]) < 0) {
        eventList.add(createCompareEvent(i, j));
        swap(arr, j + 1, j);
        eventList.add(createSwapEvent(j + 1, j));
        j--;
      }
    }
    return eventList;
  }

  // *****************************************************************************************************************************************
  // Selection Sort
  public static <T extends Comparable<T>> List<SortEvent<T>> selectionSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;

    for (int i = 0; i < n - 1; i++) {
      int minInd = i;
      for (int j = i + 1; j < n; j++) {
        // update minInd index if element at minInd is > element at j
        eventList.add(createCompareEvent(minInd, j));
        if (arr[minInd].compareTo(arr[j]) > 0) {
          minInd = j;
        }
      }
      eventList.add(createSwapEvent(minInd, i));
      swap(arr, minInd, i);
    }
    return eventList;
  }

  // *****************************************************************************************************************************************
  // Merge Sort

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

    if (end - start < 1) {
      return eventList;
    } else {
      splitForMergeSort(arr, start, mid, temp, eventList);
      splitForMergeSort(arr, mid, end, temp, eventList);
      merge(arr, start, mid, end, temp, eventList);
      return eventList;
    }
  }

  private static <T extends Comparable<T>> void splitForMergeSort(T[] arr, int start, int end,
      T[] temp, List<SortEvent<T>> eventList) {
    int mid = (start + end) / 2;
    if (end - start > 1) {
      splitForMergeSort(arr, start, mid, temp, eventList);
      splitForMergeSort(arr, mid, end, temp, eventList);
      merge(arr, start, mid, end, temp, eventList);
    }
  }

  private static <T extends Comparable<T>> void merge(T[] arr, int start, int mid, int end,
      T[] temp, List<SortEvent<T>> eventList) {
    int counter1 = 0;
    int counter2 = 0;

    while (start + counter1 < mid && mid + counter2 < end) {
      eventList.add(createCompareEvent(start + counter1, mid + counter2));
      if (arr[start + counter1].compareTo(arr[mid + counter2]) <= 0) {
        // need some review (temp copy)
        temp[start + counter1 + counter2] = arr[start + counter1];
        counter1++;
      } else {
        temp[start + counter1 + counter2] = arr[mid + counter2];
        counter2++;
      }
    }

    while (start + counter1 < mid) {
      temp[start + counter1 + counter2] = arr[start + counter1];
      counter1++;
    }

    while (mid + counter2 < end) {
      temp[start + counter1 + counter2] = arr[mid + counter2];
      counter2++;
    }

    for (int i = 0; i < end - start; i++) {
      eventList.add(createCopyEvent(start + i, temp[start + i]));
      arr[start + i] = temp[start + i];
    }
  }

  // *****************************************************************************************************************************************
  // Quick Sort
  public static <T extends Comparable<T>> List<SortEvent<T>> quickSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    quickSortHelper(arr, 0, arr.length, eventList);
    return eventList;
  }

  private static <T extends Comparable<T>> void quickSortHelper(T[] arr, int start, int end, List<SortEvent<T>> eventList) {
    if (end - start == 2) {
      eventList.add(createCompareEvent(start, end - 1));
      if (arr[start].compareTo(arr[end - 1]) > 0) {
        eventList.add(createSwapEvent(start, end - 1));
        swap(arr, start, end - 1);
      }
    } else if (end - start > 1) {
      // find the pivot index, and swap it to the end of the arr temporary
      int pivotInd = start + r.nextInt(end - start);
      int lastInd = end - 1;
      eventList.add(createSwapEvent(pivotInd, lastInd));
      swap(arr, pivotInd, lastInd);

      int left = start;
      int right = lastInd - 1;

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

      eventList.add(createCompareEvent(left, right));
      if (arr[left].compareTo(arr[right]) > 0 && right - left == 1) {
        eventList.add(createSwapEvent(left, right));
        swap(arr, left, right);
      }

      // swap the pivot back to the place where it is
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

      quickSortHelper(arr, start, right, eventList);
      quickSortHelper(arr, right + 1, end, eventList);
    }
  }

  // ****************************************************************************************************************************************
  // Bubble Sort
  public static <T extends Comparable<T>> List<SortEvent<T>> bubbleSort(T[] arr) {
    ArrayList<SortEvent<T>> eventList = new ArrayList<SortEvent<T>>();
    int n = arr.length;

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
  // helper method
  private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
    T temp = arr[j];
    arr[j] = arr[i];
    arr[i] = temp;
  }

  private static <T extends Comparable<T>> CompareEvent<T> createCompareEvent(int firstInd,
      int secondInd) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);
    ind.add(secondInd);

    CompareEvent<T> cmpEvent = new CompareEvent<T>(ind);
    return cmpEvent;
  }

  private static <T extends Comparable<T>> SwapEvent<T> createSwapEvent(int firstInd,
      int secondInd) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);
    ind.add(secondInd);

    SwapEvent<T> swapEvent = new SwapEvent<T>(ind);
    return swapEvent;
  }

  private static <T extends Comparable<T>> CopyEvent<T> createCopyEvent(int firstInd, T value) {
    ArrayList<Integer> ind = new ArrayList<Integer>();
    ind.add(firstInd);

    CopyEvent<T> copyEvent = new CopyEvent<T>(ind, value);
    return copyEvent;
  }
}
