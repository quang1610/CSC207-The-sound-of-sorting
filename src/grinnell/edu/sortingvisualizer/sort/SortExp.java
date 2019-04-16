package grinnell.edu.sortingvisualizer.sort;

import grinnell.edu.sortingvisualizer.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SortExp {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Integer[] arr = new Integer[] {1,4,2,5, -1, 19, 18, 0, 0};
    Integer[] arr1 = Arrays.copyOf(arr, arr.length);
    ArrayList<SortEvent<Integer>> sortEventList = (ArrayList<SortEvent<Integer>>) Sort.selectionSort(arr);
    Sort.sortEvent(arr1, sortEventList);
    
    System.out.println(Arrays.toString(arr));
    System.out.println(Arrays.toString(arr1));
  }

}// 1 5 2 4
