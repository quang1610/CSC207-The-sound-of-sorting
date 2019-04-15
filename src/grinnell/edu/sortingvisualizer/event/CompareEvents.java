package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public class CompareEvents<T extends Comparable<T>> implements SortEvent<T> {

  public <T extends Comparable<T>> void apply(T[] arr) {

  }
  
  public List<Integer> getAffectedIndices() {
    return null;
  }
  
  public boolean isEmphasized() {
    return false;
  }
}
