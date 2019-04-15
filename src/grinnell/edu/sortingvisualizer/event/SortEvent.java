package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public interface SortEvent<T extends Comparable<T>> {
  void apply(T[] arr);
  
  List<Integer> getAffectedIndices();
  
  boolean isEmphasized();
  
}
