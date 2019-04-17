package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class CopyEvent <T extends Comparable<T>> implements SortEvent<T>{
  private List<Integer> lstInd;
  private T value;
  
  //Constructor
  public CopyEvent(List<Integer> lstInd, T value) {
    this.lstInd = lstInd;
    this.value = value;
  }

  
  //Methods
  
  
  /** Copies value into the arr at the index in lstInd
   * 
   */
  public void apply(T[] arr) {
    arr[lstInd.get(0)] = this.value;
  }
  
  /**
   * returns lstInd
   */
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  /**
   * returns true, as copy events are to be emphasized
   */
  public boolean isEmphasized() {
    return true;
  }
}
