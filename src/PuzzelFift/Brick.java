package PuzzelFift;

import javax.swing.*;
import java.awt.*;

public abstract class Brick {
  protected final JButton button;

  //Skapar upp en new button.
  public Brick(String buttonText) {
    this.button = new JButton(buttonText);
    this.button.setFocusPainted(false);
    this.button.setPreferredSize(new Dimension(75,75));
  }

  public JButton getButton() {
    return button;
  }
}
