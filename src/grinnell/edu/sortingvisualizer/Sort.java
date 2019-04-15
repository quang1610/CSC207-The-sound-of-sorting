package grinnell.edu.sortingvisualizer;

import java.util.Arrays;
import java.util.Random;

public class Sort<T extends Comparable<T>> {
  private static Random r = new Random();

  // Insertion Sort
  public static <T extends Comparable<T>> T[] insertionSort(T[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      T key = arr[i];
      int j = i - 1;
      while (j >= 0 && key.compareTo(arr[j]) < 0) {
        swap(arr, j + 1, j);
        j--;
      }
    }
    return arr;
  }

  // *****************************************************************************************************************************************
  // Selection Sort
  public static <T extends Comparable<T>> T[] selectionSort(T[] arr) {
    int n = arr.length;

    for (int i = 0; i < n - 1; i++) {
      int minInd = i;
      for (int j = i + 1; j < n; j++) {
        // update minInd index if element at minInd is > element at j
        if (arr[minInd].compareTo(arr[j]) > 0) {
          minInd = j;
        }
      }
      swap(arr, minInd, i);
    }
    return arr;
  }

  // *****************************************************************************************************************************************
  // Merge Sort

  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>> T[] mergeSort(T[] arr) {
    // some set up. Temp is an array to reduce the space complexity, it is the temporary array to
    // write down the sorted part of the array.
    int n = arr.length;
    T[] temp = (T[]) new Comparable[n];

    // start is inclusive, and end is exclusive.
    int start = 0;
    int end = n;
    int mid = (start + end) / 2;

    if (end - start < 1) {
      return arr;
    } else {
      splitForMergeSort(arr, start, mid, temp);
      splitForMergeSort(arr, mid, end, temp);
      merge(arr, start, mid, end, temp);
      return arr;
    }
  }

  private static <T extends Comparable<T>> void splitForMergeSort(T[] arr, int start, int end,
      T[] temp) {
    int mid = (start + end) / 2;
    if (end - start > 1) {
      splitForMergeSort(arr, start, mid, temp);
      splitForMergeSort(arr, mid, end, temp);
      merge(arr, start, mid, end, temp);
    }
  }

  private static <T extends Comparable<T>> void merge(T[] arr, int start, int mid, int end,
      T[] temp) {
    int counter1 = 0;
    int counter2 = 0;

    while (start + counter1 < mid && mid + counter2 < end) {
      if (arr[start + counter1].compareTo(arr[mid + counter2]) <= 0) {
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
      arr[start + i] = temp[start + i];
    }
  }

  // *****************************************************************************************************************************************
  // Quick Sort
  public static <T extends Comparable<T>> T[] quickSort(T[] arr) {
    quickSortHelper(arr, 0, arr.length);
    return arr;
  }

  private static <T extends Comparable<T>> void quickSortHelper(T[] arr, int start, int end) {
    if (end - start == 2) {
      if (arr[start].compareTo(arr[end - 1]) > 0) {
        swap(arr, start, end - 1);
      }
    } else if (end - start > 1) {
      // find the pivot index, and swap it to the end of the arr temporary
      int pivotInd = start + r.nextInt(end - start);
      int lastInd = end - 1;
      swap(arr, pivotInd, lastInd);

      int left = start;
      int right = lastInd - 1;

      while (right - left > 1) {
        while (arr[left].compareTo(arr[lastInd]) < 0 && left < lastInd - 1) {
          left++;
        }
        while (arr[right].compareTo(arr[lastInd]) > 0 && right > 0) {
          right--;
        }
        if (left < right) {
          swap(arr, left, right);
        }
      }
      
      if(arr[left].compareTo(arr[right]) > 0 && right - left == 1) {
        swap(arr,left, right);
      }
      
      // swap the pivot back to the place where it is
      if (arr[left].compareTo(arr[lastInd]) >= 0) {
        swap(arr, left, lastInd);
        right = left;
      } else if (arr[right].compareTo(arr[lastInd]) >= 0){
        swap(arr, right, lastInd);
      } else {
        right = lastInd;
      }

      quickSortHelper(arr, start, right);
      quickSortHelper(arr, right + 1, end);
    }
  }

  // ****************************************************************************************************************************************
  // Bubble Sort
  public static <T extends Comparable<T>> T[] bubbleSort(T[] arr) {
    int n = arr.length;

    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (arr[j].compareTo(arr[j + 1]) > 0) {
          swap(arr, j, j + 1);
        }
      }
    }
    return arr;
  }

  // ****************************************************************************************************************************************
  // helper method
  private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
    T temp = arr[j];
    arr[j] = arr[i];
    arr[i] = temp;
  }
}
