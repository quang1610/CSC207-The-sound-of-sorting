package grinnell.edu.sortingvisualizer.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import grinnell.edu.sortingvisualizer.event.CompareEvent;
import grinnell.edu.sortingvisualizer.event.SortEvent;
import grinnell.edu.sortingvisualizer.sort.Sort;

/**
 * The Control Panel houses the GUI for interacting with the Sounds of Sorting application.
 */
public class ControlPanel extends JPanel {

  private static final long serialVersionUID = 3988453646682174194L;

  /** The frames-per-second that the program should render at */
  private static final int FPS = 20;

  /** The MIDI note values for the B minor pentatonic scale */
  public static final int[] bMinorPentatonicValues =
      new int[] {46, 49, 51, 53, 56, 58, 61, 63, 65, 68, 70, 73, 75, 78, 82, 85, 87};

  /** The MIDI note values for the chromatic scale */
  public static final int[] chromaticValues =
      new int[] {40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
          61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79};

  /**
   * Generates a list of sorting events by sorting the given array using the specified sorting
   * algorithm.
   * 
   * @param sort the name of the sorting algorithm to use
   * @param arr the array to sort
   * @return the list sorting events generated by sorting the array
   */
  private static List<SortEvent<Integer>> generateEvents(String sort, Integer[] arr) {
    switch (sort) {
      case "Selection":
        return Sort.selectionSort(arr);
      case "Insertion":
        return Sort.insertionSort(arr);
      case "Bubble":
        return Sort.bubbleSort(arr);
      case "Merge":
        return Sort.mergeSort(arr);
      case ("Quick"):
        return Sort.quickSort(arr);
      default:
        throw new IllegalArgumentException("generateEvents");
    }
  }

  /**
   * Generates the named scale.
   * 
   * @param name the name of the scale
   * @return a new Scale object that contains the desired scale
   */
  public static Scale generateScale(String name) {
    switch (name) {
      case "Pentatonic":
        return new Scale(bMinorPentatonicValues);
      case "Chromatic":
        return new Scale(chromaticValues);
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * @param fps the frames-per-second the simulation should run in
   * @return the period (in milliseconds) between steps of the simulation
   */
  private static int toPeriod(int fps) {
    return 1000 / fps;
  }

  private Scale scale;
  private ArrayPanel panel;
  private boolean isSorting;

  /**
   * Constructs a new ControlPanel.
   * 
   * @param notes the Notes object that this control panel manages
   * @param panel the ArrayPanel that this panel renders to
   */
  public ControlPanel(NoteIndices notes, ArrayPanel panel) {
    scale = new Scale(bMinorPentatonicValues);
    notes.initializeAndShuffle(scale.size());
    this.panel = panel;

    ///// The sort selection combo box /////
    JComboBox<String> sorts =
        new JComboBox<>(new String[] {"Selection", "Insertion", "Bubble", "Merge", "Quick"});
    add(sorts);

    ///// The scale selection combo box /////
    JComboBox<String> scales = new JComboBox<>(new String[] {"Pentatonic", "Chromatic"});
    add(scales);

    ///// The make scale button /////
    JButton makeScaleButton = new JButton("Make Scale");
    makeScaleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!isSorting) {
          scale = generateScale((String) scales.getSelectedItem());
          notes.initializeAndShuffle(scale.size());
          ControlPanel.this.panel.repaint();
        }
      }
    });
    add(makeScaleButton);

    ///// The play button /////
    JButton playButton = new JButton("Play");
    playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isSorting) {
          return;
        }
        isSorting = true;

        // TODO: fill me in
        // 1. Create the sorting events list
        // 2. Add in the compare events to the end of the list
        List<SortEvent<Integer>> events =
            generateEvents((String) sorts.getSelectedItem(), notes.getNotes());

        // NOTE: The Timer class repetitively invokes a method at a
        // fixed interval. Here we are specifying that method
        // by creating an _anonymous subclass_ of the TimeTask
        // class. We'll discuss this later in the course.
        // For now, you can interpret the run() method as the
        // method that fires on every "tick" of the program.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          private int index = 0;

          @Override
          public void run() {
            if (index < events.size()) {
              SortEvent<Integer> e = events.get(index++);
              // 1. Apply the next sort event.
              // 3. Play the corresponding notes denoted by the
              // affected indices logged in the event.
              // 4. Highlight those affected indices.
              e.apply(notes.getNotes());
              if (e.getAffectedIndices().size() == 1) {
                scale.playNote(e.getAffectedIndices().get(0), e.isEmphasized());
                notes.highlightNote(e.getAffectedIndices().get(0));
              } else {
                scale.playNote(e.getAffectedIndices().get(0), e.isEmphasized());
                notes.highlightNote(e.getAffectedIndices().get(0));
                scale.playNote(e.getAffectedIndices().get(1), e.isEmphasized());
                notes.highlightNote(e.getAffectedIndices().get(1));
              }

              panel.repaint();
            } else {
              this.cancel();
              panel.repaint();
              isSorting = false;
            }
          }
        }, 0, toPeriod(FPS));

      }
    });
    add(playButton);
  }
}
