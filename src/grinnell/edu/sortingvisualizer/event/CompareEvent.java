package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class CompareEvent<T extends Comparable<T>> implements SortEvent<T> {
  private List<Integer> lstInd;
  
  public CompareEvent(List<Integer> lstInd) {
    this.lstInd = lstInd;
  }

  public void apply(T[] arr) {
    //int cmp = arr[this.lstInd.get(0)].compareTo(arr[lstInd.get(1)]); 
  }
  
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  public boolean isEmphasized() {
    return false;
  }
}
