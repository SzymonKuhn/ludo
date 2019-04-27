import java.util.Random;

public class Dice {
    private int value;
    private Random random = new Random();

    public Dice(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void throwDice () {
        this.value = random.nextInt(6) + 1;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }
}
