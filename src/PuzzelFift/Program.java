package PuzzelFift;

import java.awt.*;
import javax.swing.*;

public class Program {
  public static void main(String[] args) {
    JFrame window = new JFrame("15 spelet");
    window.setSize(300, 400);
    Board board = new Board();
    JPanel topPanel = new JPanel();
    topPanel.setSize(300, 100);
    window.setLayout(new FlowLayout());
    JButton newGameButton = new JButton("Nytt spel");
    newGameButton.setFocusPainted(false);
    newGameButton.addActionListener(event -> board.shuffleBricks());
    topPanel.add(newGameButton);
    topPanel.add(board.getAttemptsTextPane());
    window.add(topPanel);
    window.add(board.getBoardPanel());
    window.setVisible(true);
    window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
  }
}
