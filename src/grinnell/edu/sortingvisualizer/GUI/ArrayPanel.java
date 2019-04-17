package grinnell.edu.sortingvisualizer.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ArrayPanel extends JPanel {

  private NoteIndices notes;

  /**
   * Constructs a new ArrayPanel that renders the given note indices to the screen.
   * 
   * @param notes the indices to render
   * @param width the width of the panel
   * @param height the height of the panel
   */
  public ArrayPanel(NoteIndices notes, int width, int height) {
    this.notes = notes;
    this.setPreferredSize(new Dimension(width, height));
  }

  @Override
  public void paintComponent(Graphics g) {
    // clear the rectangle before painting
    g.clearRect(0, 0, this.getWidth(), this.getHeight());
    
    // set up values
    int length = notes.getNotes().length;
    int xTemp = 0;
    int yTemp = 0;
    int width = this.getWidth()/length;
    int heightTemp = 0;
    int unitHeight = this.getHeight()/length; 
    Color color;
    
    // for the length of notes...
    for(int i = 0; i < length; i++) {
      //...the x coordinate is at i * width...
      xTemp = i * width;
      // ...heightTemp is the number at the index i of the NoteIndices notes...
      heightTemp = unitHeight * (notes.getNotes()[i] + 1);
      // ...and the y coordinate is the total visualizer's height - our heightTemp value.
      yTemp = this.getHeight() - heightTemp;
      // The color of the bar changes depending on the height.
      color = new Color(notes.getNotes()[i] * 225 / length, 150, 150);
      g.setColor(color);
      
      // draw a filled rectangle
      g.fillRect(xTemp, yTemp, width, heightTemp);
      // if the note is highlighted, outline the rectangle with yellow
      if(notes.isHighlighted(i)) {
        g.setColor(Color.YELLOW);
        g.drawRect(xTemp, yTemp, width, heightTemp);
      }
    }
  }
}
