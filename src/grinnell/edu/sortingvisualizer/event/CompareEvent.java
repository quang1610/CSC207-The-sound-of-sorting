package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class CompareEvent<T extends Comparable<T>> implements SortEvent<T> {
  private List<Integer> lstInd;
  
  //Constructor
  public CompareEvent(List<Integer> lstInd) {
    this.lstInd = lstInd;
  }

  /**
   * Compares the two elements in the indices listed in lstInd
   */
  public void apply(T[] arr) {
    int cmp = arr[this.lstInd.get(0)].compareTo(arr[lstInd.get(1)]); 
  }
  
  /**
   * returns lstInd
   */
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  /**
   * returns false, as compare events are not to be emphasized
   */
  public boolean isEmphasized() {
    return false;
  }
}
