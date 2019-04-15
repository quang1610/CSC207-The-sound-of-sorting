package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class SwapEvent <T extends Comparable<T>> implements SortEvent<T> {
  private List<Integer> lstInd;
  
  public SwapEvent(List<Integer> lstInd) {
    this.lstInd = lstInd;
  }

  public void apply(T[] arr) {
    T temp = arr[this.lstInd.get(0)];
    arr[this.lstInd.get(0)] = arr[this.lstInd.get(1)];
    arr[this.lstInd.get(1)] = temp;
  }
  
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  public boolean isEmphasized() {
    return true;
  }
}
