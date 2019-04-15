package grinnell.edu.sortingvisualizer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;

class SortTest {
  private Random r = new Random();


  void testSelectionSort() {
    specificTest((arr) -> Sort.selectionSort(arr), "Selection Sort");
    randomTest((arr) -> Sort.selectionSort(arr), 10000, 10, "Selection Sort Random");
  }


  void testInsertionSort() {
    specificTest((arr) -> Sort.insertionSort(arr), "Insertion Sort");
    randomTest((arr) -> Sort.insertionSort(arr), 10000, 10, "Insertion Sort Random");
  }


  void testMergeSort() {
    specificTest((arr) -> Sort.mergeSort(arr), "Merge Sort");
    randomTest((arr) -> Sort.mergeSort(arr), 10000, 10, "Merge Sort Random");
  }


  void testQuickSort() {
    specificTest((arr) -> Sort.quickSort(arr), "Quick Sort");
    randomTest((arr) -> Sort.quickSort(arr), 10000, 10, "Quick Sort Random");
  }

  @Test
  void testBubbleSort() {
    specificTest((arr) -> Sort.bubbleSort(arr), "Bubble Sort");
    randomTest((arr) -> Sort.bubbleSort(arr), 10000, 10, "Bubble Sort Random");
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

  private <T extends Comparable<T>> void assertResult(T[] arr1, T[] arr2, String error) {
    // System.out.println(toStringArr(arr2));

    assertEquals(compareArray(arr1, arr2), true, error);
  }

  private <T extends Comparable<T>> void randomTest(Consumer<Integer[]> sorter, int arrLength,
      int repetition, String message) {
    Integer[] arr = new Integer[arrLength];

    for (int i = 0; i < repetition; i++) {
      for (int j = 0; j < arrLength; j++) {
        arr[j] = r.nextInt();
      }

      Integer[] arr1 = Arrays.copyOf(arr, arrLength);
      Arrays.sort(arr);
      sorter.accept(arr1);
      assertResult(arr, arr1, message);
    }
  }

  private <T extends Comparable<T>> void specificTest(Consumer<Integer[]> sorter, String message) {
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
    
    for(int i = 0; i < arrays.size(); i++) {
      Integer[] temp = Arrays.copyOf(arrays.get(i), arrays.get(i).length);
      String str = Arrays.toString(temp);
      
      Arrays.sort(arrays.get(i));
      sorter.accept(temp);
      
      assertResult(arrays.get(i), temp, message + ": " + str);
    }

  }
}
