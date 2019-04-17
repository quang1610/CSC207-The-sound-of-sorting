package grinnell.edu.sortingvisualizer.event;

import java.util.List;

public interface SortEvent<T extends Comparable<T>> {
  
  /** Applies the sortEvent on the given array
   * 
   * @param arr array to be acted on
   */
  void apply(T[] arr);
  
  
  /** returns a list of the affected indices
   * 
   * @return list of affected indices
   */
  List<Integer> getAffectedIndices();
  
  
  /**
   * 
   * @return true if the action should be emphasized
   */
  boolean isEmphasized();
  
}
