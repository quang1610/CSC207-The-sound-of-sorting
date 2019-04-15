package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class CopyEvent <T extends Comparable<T>> implements SortEvent<T>{
  private List<Integer> lstInd;
  private T value;
  
  public CopyEvent(List<Integer> lstInd, T value) {
    this.lstInd = lstInd;
    this.value = value;
  }

  public void apply(T[] arr) {
    arr[lstInd.get(0)] = this.value;
  }
  
  public List<Integer> getAffectedIndices() {
    return this.lstInd;
  }
  
  public boolean isEmphasized() {
    return true;
  }
}
