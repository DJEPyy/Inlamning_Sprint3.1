package PuzzelFift;

import javax.swing.*;

public abstract class Brick {
  protected final JButton button;

  public Brick(String buttonText) {
    this.button = new JButton(buttonText);
    this.button.setFocusPainted(false);
  }

  public JButton getButton() {
    return button;
  }
}
