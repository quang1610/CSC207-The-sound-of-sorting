package grinnell.edu.sortingvisualizer.sort;

import static org.junit.jupiter.api.Assertions.*;
import grinnell.edu.sortingvisualizer.event.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.Random;

class SortByEventList {
  private static Random r = new Random();

  // ******************************************************************************************************************************************
  @Test
  void testSelectionSort() {
    randomTest((arr) -> Sort.selectionSort(arr), 10, 10, "Selection Sort Random Test: ");
    specificTest((arr) -> Sort.selectionSort(arr), "Selection Sort");
  }

  @Test
  void testInsertionSort() {
    randomTest((arr) -> Sort.insertionSort(arr), 10, 10, "Insertion Sort Random Test: ");
    specificTest((arr) -> Sort.insertionSort(arr), "Insertion Sort");
  }
  
  @Test
  void testMergeSort() {
    randomTest((arr) -> Sort.mergeSort(arr), 1000, 10, "Merge Sort Random Test: ");
    specificTest((arr) -> Sort.mergeSort(arr), "Merge Sort");
  }
  
  @Test
  void testQuickSort() {
    randomTest((arr) -> Sort.quickSort(arr), 1000, 10, "Quick Sort Random Test: ");
    specificTest((arr) -> Sort.quickSort(arr), "Quick Sort");
  }
  
  @Test
  void testBubbleSort() {
    randomTest((arr) -> Sort.bubbleSort(arr), 1000, 10, "Bubble Sort Random Test: ");
    specificTest((arr) -> Sort.bubbleSort(arr), "Bubble Sort");
  }
  
  // ******************************************************************************************************************************************
  // Helper methods
  @SuppressWarnings("unchecked")
  private <T extends Comparable<T>> void randomTest(Function<T[], List<SortEvent<T>>> func,
      int arrLength, int repetition, String message) {
    Integer[] arr = new Integer[arrLength];

    // initialize the random Integer array, and sort them using t
    for (int i = 0; i < repetition; i++) {
      for (int j = 0; j < arrLength; j++) {
        arr[j] = r.nextInt();
      }
      testByEventList((T[]) arr, func, message);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Comparable<T>> void specificTest(Function<T[], List<SortEvent<T>>> func,
      String message) {
    ArrayList<Integer[]> arrays = new ArrayList<Integer[]>();
    Integer[] arr0 = new Integer[] {1};
    Integer[] arr1 = new Integer[] {1, 2};
    Integer[] arr2 = new Integer[] {2, 1};
    Integer[] arr3 = new Integer[] {1, 2, 3, 4, 5};
    Integer[] arr4 = new Integer[] {5, 4, 3, 2, 1};

    arrays.add(arr0);
    arrays.add(arr1);
    arrays.add(arr2);
    arrays.add(arr3);
    arrays.add(arr4);

    for (int i = 0; i < arrays.size(); i++) {
      testByEventList((T[]) arrays.get(i), func, message + ": " + Arrays.toString(arrays.get(i)));
    }
  }

  private <T extends Comparable<T>> void testByEventList(T[] arr,
      Function<T[], List<SortEvent<T>>> func, String message) {
    T[] arr1 = (T[]) Arrays.copyOf(arr, arr.length);
    T[] arr2 = (T[]) Arrays.copyOf(arr, arr.length);

    // capture the sortEventList
    ArrayList<SortEvent<T>> sortEventList = (ArrayList<SortEvent<T>>) func.apply(arr);
    // using that sortEventList to sort the array.
    Sort.sortEvent(arr1, sortEventList);
    Arrays.sort(arr2);
    
    //using the assertResult from the SortTest Class
    SortTest.assertResult(arr1, arr2, message);
  }
}
