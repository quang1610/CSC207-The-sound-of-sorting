package grinnell.edu.sortingvisualizer.sort;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;

class SortTest {
  private Random r = new Random();

  // ******************************************************************************************************************************************
  // public static helper method. Used by other test suites.
  public static <T extends Comparable<T>> void assertResult(T[] arr1, T[] arr2, String error) {
    assertEquals(compareArray(arr1, arr2), true, error);
  }

  // ******************************************************************************************************************************************
  // test methods
  @Test
  void testSelectionSort() {
    specificTest((arr) -> Sort.selectionSort(arr), "Selection Sort");
    randomTest((arr) -> Sort.selectionSort(arr), 1000, 10, "Selection Sort Random");
  }

  @Test
  void testInsertionSort() {
    specificTest((arr) -> Sort.insertionSort(arr), "Insertion Sort");
    randomTest((arr) -> Sort.insertionSort(arr), 1000, 10, "Insertion Sort Random");
  }

  @Test
  void testMergeSort() {
    specificTest((arr) -> Sort.mergeSort(arr), "Merge Sort");
    randomTest((arr) -> Sort.mergeSort(arr), 1000, 10, "Merge Sort Random");
  }

  @Test
  void testQuickSort() {
    specificTest((arr) -> Sort.quickSort(arr), "Quick Sort");
    randomTest((arr) -> Sort.quickSort(arr), 1000, 10, "Quick Sort Random");
  }

  @Test
  void testBubbleSort() {
    specificTest((arr) -> Sort.bubbleSort(arr), "Bubble Sort");
    randomTest((arr) -> Sort.bubbleSort(arr), 1000, 10, "Bubble Sort Random");
  }

  // ******************************************************************************************************************************************
  // Helper Methods
  private static <T extends Comparable<T>> boolean compareArray(T[] arr1, T[] arr2) {
    if (arr1.length != arr2.length) {
      return false;
    } else {
      for (int i = 0; i < arr2.length; i++) {
        if (arr1[i].compareTo(arr2[i]) != 0) {
          return false;
        }
      }
      return true;
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Comparable<T>> void randomTest(Consumer<T[]> sorter, int arrLength,
      int repetition, String message) {
    Integer[] arr = new Integer[arrLength];

    // repeat testing for 'repetition' times
    for (int i = 0; i < repetition; i++) {
      // initialize a random integer array and then normal sorting on it
      for (int j = 0; j < arrLength; j++) {
        arr[j] = r.nextInt();
      }
      testByNormalSort((T[]) arr, sorter, message);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Comparable<T>> void specificTest(Consumer<T[]> sorter, String message) {
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

    // for each array in the arrays list, we test normal sorting on it
    for (int i = 0; i < arrays.size(); i++) {
      testByNormalSort((T[]) arrays.get(i), sorter,
          message + ": " + Arrays.deepToString(arrays.get(i)));
    }
  }

  private <T extends Comparable<T>> void testByNormalSort(T[] arr, Consumer<T[]> sorter,
      String message) {
    T[] temp = (T[]) Arrays.copyOf(arr, arr.length);
    Arrays.parallelSort(arr);
    sorter.accept(temp);
    assertResult(arr, temp, message);
  }
}
