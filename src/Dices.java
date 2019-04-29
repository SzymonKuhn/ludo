import java.util.ArrayList;
import java.util.*;

class Dices {

    private List<Dice> dices = new ArrayList<>();

    Dices() {
        for (int i = 0; i <= 4; i++) {
            dices.add(new Dice(1));
        }
    }

    List<Dice> getList() {
        return dices;
    }

    void throwAll() {
        for (Dice dice : dices) {
            dice.throwDice();
        }
    }

    private boolean throwOneDice(int num) {
        if (num < 1 || num > 5) {
            wrongNumber();
            return false;
        }
        dices.get(num - 1).throwDice();
        return true;
    }

    boolean throwChosen(int... chosen) {
        // sprawdzenie czy wybrano kostkę
        if (chosen.length <= 0) {
            noDices();
            return true;
        }
        //sprawdzenie ilości wybranych kostek
        if (chosen.length > 5) {
            tooManyDices();
            return false;
        }

        //sprawdzenie powtórzenia numerów kostek
        for (int i = 0; i < chosen.length - 1; i++) {
            for (int j = i + 1; j < chosen.length; j++) {
                if (chosen[i] == chosen[j]) {
                    repetedDices();
                    return false;
                }
            }
        }
        //rzucanie
        for (int i1 : chosen) {
            if (!throwOneDice(i1)) {
                return false;
            }
        }
        return true;
    }

    Integer sumAll() {
        int sum = 0;
        for (Dice dice : dices) {
            sum = sum + dice.getValue();
        }
        return sum;
    }

    Integer sumNumber(Integer num) {
        int result = 0;
        for (Dice dice : dices) {
            if (dice.getValue() == num) {
                result = result + dice.getValue();
            }
        }
        return result;
    }

    private void wrongNumber() {
        System.out.println("Podano błędny numer kostki");
    }

    private void tooManyDices() {
        System.out.println("Zbyt duża ilość kostek");
    }

    private void repetedDices() {
        System.out.println("Powtórzono numery kostek");
    }

    private void noDices() {
        System.out.println("Nie wybrano kostek");
    }
}
