import java.util.ArrayList;
import java.util.List;

public class CartManager {

    Dices dices;

    public CartManager(Dices dices) {
        this.dices = dices;
    }

    public boolean checkAndAddResult(Figures figure, Cart cart) {
        Integer result = 0;
        if (checkResult(figure)) {
            switch (figure) {
                case ONES: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(1));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case TWOS: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(2));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case THREES: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(3));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case FOURS: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(4));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case FIVES: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(5));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case SIXES: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumNumber(6));
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case THREE_SAME: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumAll());
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case FOUR_SAME: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumAll());
                        break;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case FULL: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, 25);
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case STREET_SMALL: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, 30);
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case STREET_BIG: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, 40);
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case GENERAL: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, 50);
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
                case CHANCE: {
                    if (cart.getResultCart().get(figure) == null) {
                        cart.addResult(figure, dices.sumAll());
                        return true;
                    } else {
                        figureOccupied();
                        return false;
                    }
                }
            }
        } else {
            wrongDices();
            return false;
        }
        return false;
    }

    public boolean dicesCanBeAddedToCart(Cart cart) {
        //sprawdzenie czy układ kości może być wpisany do jakiejkolwiek rubryki tabeli wyników
        for (Figures figure : Figures.values()) {
            if (cart.getResultCart().get(figure) == null) {
                if (checkResult(figure)) {
                    return true;
                }
            }
        }
        return false;
    }



    private boolean checkResult(Figures figure) {
        //przepisanie wartości z kości do listy roboczej
        List<Integer> tempDiceList = new ArrayList<>();
        for (int i = 0; i < dices.getList().size(); i++) {
            tempDiceList.add(dices.getList().get(i).getValue());
        }
        tempDiceList.sort(null);

        switch (figure) {
            case ONES: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 1) {
                        return true;
                    }
                }
                break;
            }
            case TWOS: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 2) {
                        return true;
                    }
                }
                break;
            }
            case THREES: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 3) {
                        return true;
                    }
                }
                break;
            }
            case FOURS: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 4) {
                        return true;
                    }
                }
                break;
            }
            case FIVES: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 5) {
                        return true;
                    }
                }
                break;
            }
            case SIXES: {
                for (int i = 0; i < tempDiceList.size(); i++) {
                    if (tempDiceList.get(i) == 6) {
                        return true;
                    }
                }
                break;
            }
            case THREE_SAME: {
                for (int i = 0; i < tempDiceList.size() - 2; i++) {
                    for (int j = i + 1; j < tempDiceList.size() - 1; j++) {
                        if (tempDiceList.get(i) == tempDiceList.get(j)) {
                            for (int k = j + 1; k < tempDiceList.size(); k++) {
                                if (tempDiceList.get(i) == tempDiceList.get(k)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case FOUR_SAME: {
                for (int i = 0; i < tempDiceList.size() - 3; i++) {
                    for (int j = i + 1; j < tempDiceList.size() - 2; j++) {
                        if (tempDiceList.get(i) == tempDiceList.get(j)) {
                            for (int k = j + 1; k < tempDiceList.size() - 1; k++) {
                                if (tempDiceList.get(i) == tempDiceList.get(k)) {
                                    for (int l = k + 1; l < tempDiceList.size(); l++) {
                                        if (tempDiceList.get(i) == tempDiceList.get(l)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
            case FULL: {
                if (tempDiceList.get(0) == tempDiceList.get(1) && tempDiceList.get(3) == tempDiceList.get(4) && ((tempDiceList.get(2) == tempDiceList.get(0)) || (tempDiceList.get(2) == tempDiceList.get(4)))) {
                    return true;
                }
                break;
            }
            case STREET_SMALL: { // błąd, przy ustawieniu np. 1 1 2 3 4 nie spełnia wymogów figury!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if ((tempDiceList.get(0) == tempDiceList.get(1) - 1) &&
                        (tempDiceList.get(1) - 1 == tempDiceList.get(2) - 2) &&
                        (tempDiceList.get(2) - 2 == tempDiceList.get(3) - 3)) {
                    return true;
                }
                if ((tempDiceList.get(1) == tempDiceList.get(2) - 1) &&
                        (tempDiceList.get(2) - 1 == tempDiceList.get(3) - 2) &&
                        (tempDiceList.get(3) - 2 == tempDiceList.get(4) - 3)) {
                    return true;
                }
                break;
            }
            case STREET_BIG: { // błąd, przy ustawieniu np. 1 1 2 3 4 nie spełnia wymogów figury!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if ((tempDiceList.get(0) == tempDiceList.get(1) - 1) &&
                        (tempDiceList.get(1) - 1 == tempDiceList.get(2) - 2) &&
                        (tempDiceList.get(2) - 2 == tempDiceList.get(3) - 3) &&
                        (tempDiceList.get(3) - 3 == tempDiceList.get(4) - 4)) {
                    return true;
                }
                break;
            }
            case GENERAL: {
                if ((tempDiceList.get(0) == tempDiceList.get(1)) &&
                        (tempDiceList.get(1) == tempDiceList.get(2)) &&
                        (tempDiceList.get(2) == tempDiceList.get(3)) &&
                        (tempDiceList.get(3) == tempDiceList.get(4))) {
                    return true;
                }
                break;
            }
            case CHANCE: {
                return true;
            }
        }
        return false;
    }



    private void wrongDices() {
        System.out.println("Układ kości nie spełnia wymogu figury");
    }

    private void figureOccupied() {
        System.out.println("Wybrana figura już jest zajęta");
    }

}//class
