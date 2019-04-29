import java.util.Random;

public class Dice {
    private int value;
    private Random random = new Random();

    Dice(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    void throwDice() {
        this.value = random.nextInt(6) + 1;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }
}
