package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class SwapEvent <T extends Comparable<T>> implements SortEvent<T> {
  private List<Integer> lstInd;
  
  //Constructor
  public SwapEvent(List<Integer> lstInd) {
    this.lstInd = lstInd;
  }

  //Methods
  
  /** Swaps the two elements in arr at the indices listed in lstInd
   * 
   */
  public void apply(T[] arr) {
    T temp = arr[this.lstInd.get(0)];
    arr[this.lstInd.get(0)] = arr[this.lstInd.get(1)];
    arr[this.lstInd.get(1)] = temp;
  }
  
  /** returns lstInd
   *
   */
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  /** returns true, as swap events are to be emphasized
   * 
   */
  public boolean isEmphasized() {
    return true;
  }
}
