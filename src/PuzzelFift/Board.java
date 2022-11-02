package PuzzelFift;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Board {
  private final List<Brick> brickSlots = new ArrayList<>();
  private final EmptyBrick emptyBrick = new EmptyBrick();
  private final JPanel boardPanel;
  private final JTextPane attemptsTextPane;
  private int attempts = 0;

  //Skapar upp bricks och boardpanelen här och shufflar bricksen direkt.
  public Board() {
    createBricks();
    attemptsTextPane = new JTextPane();
    this.boardPanel = new JPanel();
    attemptsTextPane.setBackground(boardPanel.getBackground());
    this.boardPanel.setSize(300, 300);
    this.boardPanel.setLayout(new GridLayout(4, 4));
    shuffleBricks();
  }

  public JPanel getBoardPanel() {
    return this.boardPanel;
  }

  public JTextPane getAttemptsTextPane() {
    return this.attemptsTextPane;
  }

  //Swappar plats på bricks och uppdaterar attempts countern, sedan uppdaterar boardpanelen och attempts texten.
  public void swapBrick(Brick brick) {
    if (isSwappableBrick(brick)) {
      attempts++;
      swapBricks(brick, emptyBrick);
      updateBoardPanel();
      updateAttemptsTextPane();
    }
  }

  //Shufflar bricksen i en random ordning.
  public void shuffleBricks() {
    Random random = new Random();
    for (Brick brick : brickSlots) {
      int randomBrickIndex = random.nextInt(16);
      Brick randomBrick = brickSlots.get(randomBrickIndex);
      swapBricks(brick, randomBrick);
    }

    //Shufflar bricksen tills dom inte är i korrekt ordning.
    if (isInCorrectOrder()) {
      shuffleBricks();
      return;
    }

    attempts = 0;
    updateAttemptsTextPane();
    updateBoardPanel();
  }

  //Skapar upp 15st numbered bricks och sätter sedan 1 tom brick sist i vår lista.
  private void createBricks() {
    for (int i = 0; i < 15; i++) {
      int number = i + 1;
      brickSlots.add(new NumberedBrick(number, this));
    }
    brickSlots.add(emptyBrick);
  }

  //Kollar om bricks är i rätt ordning
  private boolean isInCorrectOrder() {
    for (int i = 0; i < brickSlots.size() - 1; i++) {
      if (brickSlots.get(i) instanceof NumberedBrick numberedBrick) {
        if (numberedBrick.getNumber() != i + 1) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }

  //Kollar så att vi inte går utanför range av array och får indexError
  private boolean isInRange(int index) {
    return index >= 0 && index < brickSlots.size();
  }

  //Kollar om DEN brick vi klickar på finns med i listan i swappableBricks. (actionListener i numberedBrick class).
  private boolean isSwappableBrick(Brick brick) {
    return getAllSwappableBricks().contains(brick);
  }

  // Skapar upp en lista som håller reda på vilka bricks som är swappable
  //Utgår från emptyBrick's index
  private List<Brick> getAllSwappableBricks() {
    List<Brick> swappableBricks = new ArrayList<>();

    int emptyBrickIndex = brickSlots.indexOf(emptyBrick);
    int aboveIndex = emptyBrickIndex - 4; // Slot above is 4 position before
    int belowIndex = emptyBrickIndex + 4; // Slot below is 4 position after
    int leftIndex = emptyBrickIndex - 1; // Slot above is 1 position before
    int rightIndex = emptyBrickIndex + 1; // Slot above is 1 position after

    //Hämta brick ovanför empty brick, och kolla så att vi inte går utanför range av array
    if (isInRange(aboveIndex)) {
      Brick brickAbove = brickSlots.get(aboveIndex);
      swappableBricks.add(brickAbove);
    }

    //Hämta brick under empty brick, och kolla så att vi inte går utanför range av array
    if (isInRange(belowIndex)) {
      Brick brickBelow = brickSlots.get(belowIndex);
      swappableBricks.add(brickBelow);
    }

    //Hämta brick till vänster om empty brick, och kolla så vi inte går går utanför range av array, och även se att empty brick inte redan är i column längst till vänster
    if (isInRange(leftIndex) && emptyBrickIndex % 4 != 0) {
      Brick brickBefore = brickSlots.get(leftIndex);
      swappableBricks.add(brickBefore);
    }

    //Hämta brick till vänster om empty brick, och kolla så vi inte går går utanför range av array, och även se att nästa brick inte redan är i column längst till vänster
    if (isInRange(rightIndex) && rightIndex % 4 != 0) {
      Brick brickAfter = brickSlots.get(rightIndex);
      swappableBricks.add(brickAfter);
    }

    return swappableBricks;
  }

  //Swappar plats på bricks i listan
  private void swapBricks(Brick a, Brick b) {
    int indexA = brickSlots.indexOf(a);
    int indexB = brickSlots.indexOf(b);
    brickSlots.set(indexA, b);
    brickSlots.set(indexB, a);
  }


  //Uppdaterar boardPanel, raderar allt och uppdaterar boarden med uppdaterade listan, och kollar om man har vunnit spelet.
  private void updateBoardPanel() {
    boardPanel.removeAll();
    for (Brick brick : brickSlots) {
      boardPanel.add(brick.getButton());
    }
    boardPanel.revalidate();
    boardPanel.repaint();

    if (isInCorrectOrder()) {
      JOptionPane.showMessageDialog(boardPanel, "Grattis, du klarade spelet på " + attempts + " knapptryck!");
      shuffleBricks();
    }
  }

  //Uppdaterar textPane, som visar antal knapptryck.
  private void updateAttemptsTextPane() {
    attemptsTextPane.setText("Antal knapptryck: " + attempts);
    attemptsTextPane.revalidate();
    attemptsTextPane.repaint();
  }
}
