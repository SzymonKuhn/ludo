import java.util.ArrayList;
import java.util.*;

public class Dices {
    private Dice dice1 = new Dice(1);
    private Dice dice2 = new Dice(1);
    private Dice dice3 = new Dice(1);
    private Dice dice4 = new Dice(1);
    private Dice dice5 = new Dice(1);
    List<Dice> dices = new ArrayList<>();

    public Dices() {
        dices.add(dice1);
        dices.add(dice2);
        dices.add(dice3);
        dices.add(dice4);
        dices.add(dice5);
    }

    public List<Dice> getList() {
        return dices;
    }


    public void throwAll() {
        for (int i = 0; i < dices.size(); i++) {
            dices.get(i).throwDice();
        }
    }

    public void throwOneDice(int num) {
        if (num < 1 || num > 5) {
            wrongNumber();
        }
        switch (num) {
            case 1: {
                dice1.throwDice();
                break;
            }
            case 2: {
                dice2.throwDice();
                break;
            }
            case 3: {
                dice3.throwDice();
                break;
            }
            case 4: {
                dice4.throwDice();
                break;
            }
            case 5: {
                dice5.throwDice();
                break;
            }
        }
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
            throwOneDice(chosen[i]);
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
