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

  public void swapBrick(Brick brick) {
    if (isSwappableBrick(brick)) {
      attempts++;
      swapBricks(brick, emptyBrick);
      updateBoardPanel();
      updateAttemptsTextPane();
    }
  }

  public void shuffleBricks() {
    Random random = new Random();
    for (Brick brick : brickSlots) {
      int randomBrickIndex = random.nextInt(16);
      Brick randomBrick = brickSlots.get(randomBrickIndex);
      swapBricks(brick, randomBrick);
    }

    // Shuffle until bricks are not in correct order
    if (isInCorrectOrder()) {
      shuffleBricks();
      return;
    }

    attempts = 0;
    updateAttemptsTextPane();
    updateBoardPanel();
  }

  private void createBricks() {
    for (int i = 0; i < 15; i++) {
      int number = i + 1;
      brickSlots.add(new NumberedBrick(number, this));
    }
    brickSlots.add(emptyBrick);
  }

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

  private boolean isInRange(int index) {
    return index >= 0 && index < brickSlots.size();
  }

  private boolean isSwappableBrick(Brick brick) {
    return getAllSwappableBricks().contains(brick);
  }

  private List<Brick> getAllSwappableBricks() {
    List<Brick> swappableBricks = new ArrayList<>();

    int emptyBrickIndex = brickSlots.indexOf(emptyBrick);
    int aboveIndex = emptyBrickIndex - 4; // Slot above is 4 position before
    int belowIndex = emptyBrickIndex + 4; // Slot below is 4 position after
    int leftIndex = emptyBrickIndex - 1; // Slot above is 1 position before
    int rightIndex = emptyBrickIndex + 1; // Slot above is 1 position after

    // Get brick above empty brick, and also make sure we don't go out of range for array
    if (isInRange(aboveIndex)) {
      Brick brickAbove = brickSlots.get(aboveIndex);
      swappableBricks.add(brickAbove);
    }

    // Get brick below empty brick, and also make sure we don't go out of range for array
    if (isInRange(belowIndex)) {
      Brick brickBelow = brickSlots.get(belowIndex);
      swappableBricks.add(brickBelow);
    }

    // Get brick to left of empty brick, and also make sure we don't go out of range for array and that emptyBrick is not already in the leftmost column
    if (isInRange(leftIndex) && emptyBrickIndex % 4 != 0) {
      Brick brickBefore = brickSlots.get(leftIndex);
      swappableBricks.add(brickBefore);
    }

    // Get brick to right of empty brick, and also make sure we don't go out of range for array and that the brick after is not in the leftmost column
    if (isInRange(rightIndex) && rightIndex % 4 != 0) {
      Brick brickAfter = brickSlots.get(rightIndex);
      swappableBricks.add(brickAfter);
    }

    return swappableBricks;
  }

  private void swapBricks(Brick a, Brick b) {
    int indexA = brickSlots.indexOf(a);
    int indexB = brickSlots.indexOf(b);
    brickSlots.set(indexA, b);
    brickSlots.set(indexB, a);
  }

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

  private void updateAttemptsTextPane() {
    attemptsTextPane.setText("Antal knapptryck: " + attempts);
    attemptsTextPane.revalidate();
    attemptsTextPane.repaint();
  }
}
