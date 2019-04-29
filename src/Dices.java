import java.util.ArrayList;
import java.util.*;

public class Dices {

    List<Dice> dices = new ArrayList<>();

    public Dices() {
        for (int i = 0; i <= 4; i++) {
            dices.add(new Dice(1));
        }
    }

    public List<Dice> getList() {
        return dices;
    }


    public void throwAll() {
        for (int i = 0; i < dices.size(); i++) {
            dices.get(i).throwDice();
        }
    }

    public boolean throwOneDice(int num) {
        if (num < 1 || num > 5) {
            wrongNumber();
            return false;
        }
        dices.get(num - 1).throwDice();
        return true;
    }

    public boolean throwChosen(int... chosen) {
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
        for (int i = 0; i < chosen.length; i++) {
            if (!throwOneDice(chosen[i])) {
                return false;
            }
        }
        return true;
    }

    public Integer sumAll() {
        Integer sum = 0;
        for (int i = 0; i < dices.size(); i++) {
            sum = sum + dices.get(i).getValue();
        }
        return sum;
    }

    public Integer sumNumber(Integer num) {
        Integer result = 0;
        for (int i = 0; i < dices.size(); i++) {
            if (dices.get(i).getValue() == num) {
                result = result + dices.get(i).getValue();
            }
        }
        return result;
    }

    public void wrongNumber() {
        System.out.println("Podano błędny numer kostki");
    }

    public void tooManyDices() {
        System.out.println("Zbyt duża ilość kostek");
    }

    public void repetedDices() {
        System.out.println("Powtórzono numery kostek");
    }

    private void noDices() {
        System.out.println("Nie wybrano kostek");
    }
}
