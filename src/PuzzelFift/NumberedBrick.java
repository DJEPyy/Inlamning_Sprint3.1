package PuzzelFift;

public class NumberedBrick extends Brick {
  private final int number;

  public NumberedBrick(int number, Board board) {
    super(String.valueOf(number));
    this.number = number;
    this.button.addActionListener(event -> board.swapBrick(this));
  }

  public int getNumber() {
    return number;
  }
}
