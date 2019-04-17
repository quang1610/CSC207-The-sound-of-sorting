package grinnell.edu.sortingvisualizer.GUI;

import java.util.Random;

/**
 * A collection of indices into a Scale object.
 * These indices are the subject of the various sorting algorithms
 * in the program.
 */
public class NoteIndices {
    
    Integer[] noteArray;
    boolean[] highlightIndex;
    /**
     * @param n the size of the scale object that these indices map into
     */
    public NoteIndices(int n) {
        this.noteArray = new Integer[n];
        this.highlightIndex = new boolean[n];
        for(int i = 0; i < n; i++) {
          this.noteArray[i] = i;
          this.highlightIndex[i] = false;
        }
    }
    
    /**
     * Reinitializes this collection of indices to map into a new scale object
     * of the given size.  The collection is also shuffled to provide an
     * initial starting point for the sorting process.
     * @param n the size of the scale object that these indices map into
     */
    public void initializeAndShuffle(int n) {
      // re-declare
      this.noteArray = new Integer[n];
      this.highlightIndex = new boolean[n];
      
      // initialize
      for(int i = 0; i < n; i++) {
        this.noteArray[i] = i;
        this.highlightIndex[i] = false;
      }
      
      // shuffle
      Random r = new Random();
        for(int i = 0; i < 2*n; i++) {
          int ind1 = r.nextInt(n);
          int ind2 = r.nextInt(n);
          
          // swap
          int temp = this.noteArray[ind1];
          this.noteArray[ind1] = this.noteArray[ind2];
          this.noteArray[ind2] = temp;
        }
    }
    
    /** @return the indices of this NoteIndices object */
    public Integer[] getNotes() { 
        return this.noteArray;
    }
    
    /**
     * Highlights the given index of the note array
     * @param index the index to highlight
     */
    public void highlightNote(int index) {
        this.highlightIndex[index] = true;
    }
    
    /** @return true if the given index is highlighted */
    public boolean isHighlighted(int index) {
        return this.highlightIndex[index];
    }
    
    /** Clears all highlighted indices from this collection */
    public void clearAllHighlighted() {
        for(int i = 0; i < this.noteArray.length; i++) {
          this.highlightIndex[i] = false;
        }
    }
}
