import java.util.*;

class CartManager {

    private Dices dices;

    CartManager(Dices dices) {
        this.dices = dices;
    }

    boolean checkAndAddResult(Figures figure, Cart cart) {
        if (checkResult(figure)) {
            if (cart.getResultCart().get(figure) == null) {
                cart.addResult(figure, calculatePoints(figure));
                return true;
            } else {
                figureOccupied();
                return false;
            }
        } else {
            wrongDices();
            return false;
        }
    }

    boolean dicesCanBeAddedToCart(Cart cart) {
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


    boolean checkResult(Figures figure) { // sprawdzenie czy kości odpowiadają układdowi figury
        //przepisanie wartości z kości do listy roboczej
        List<Integer> tempDiceList = new ArrayList<>();
        for (int i = 0; i < dices.getList().size(); i++) {
            tempDiceList.add(dices.getList().get(i).getValue());
        }
        tempDiceList.sort(null);

        switch (figure) {
            case ONES: {
                for (Integer integer : tempDiceList) {
                    if (integer == 1) {
                        return true;
                    }
                }
                break;
            }
            case TWOS: {
                for (Integer integer : tempDiceList) {
                    if (integer == 2) {
                        return true;
                    }
                }
                break;
            }
            case THREES: {
                for (Integer integer : tempDiceList) {
                    if (integer == 3) {
                        return true;
                    }
                }
                break;
            }
            case FOURS: {
                for (Integer integer : tempDiceList) {
                    if (integer == 4) {
                        return true;
                    }
                }
                break;
            }
            case FIVES: {
                for (Integer integer : tempDiceList) {
                    if (integer == 5) {
                        return true;
                    }
                }
                break;
            }
            case SIXES: {
                for (Integer integer : tempDiceList) {
                    if (integer == 6) {
                        return true;
                    }
                }
                break;
            }
            case THREE_SAME: {
                for (int i = 0; i < tempDiceList.size() - 2; i++) {
                    for (int j = i + 1; j < tempDiceList.size() - 1; j++) {
                        if (tempDiceList.get(i).equals(tempDiceList.get(j))) {
                            for (int k = j + 1; k < tempDiceList.size(); k++) {
                                if (tempDiceList.get(i).equals(tempDiceList.get(k))) {
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
                        if (tempDiceList.get(i).equals(tempDiceList.get(j))) {
                            for (int k = j + 1; k < tempDiceList.size() - 1; k++) {
                                if (tempDiceList.get(i).equals(tempDiceList.get(k))) {
                                    for (int l = k + 1; l < tempDiceList.size(); l++) {
                                        if (tempDiceList.get(i).equals(tempDiceList.get(l))) {
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
                if (tempDiceList.get(0).equals(tempDiceList.get(1)) && tempDiceList.get(3).equals(tempDiceList.get(4)) && ((tempDiceList.get(2).equals(tempDiceList.get(0))) || (tempDiceList.get(2).equals(tempDiceList.get(4))))) {
                    return true;
                }
                break;
            }
            case STREET_SMALL: {
                List<Integer> tempStreetList = new ArrayList<>();
                for (Integer integer : tempDiceList) {
                    if (tempStreetList.size() == 0) {
                        tempStreetList.add(integer);
                    } else {
                        if (!integer.equals(tempStreetList.get(tempStreetList.size() - 1))) {
                            tempStreetList.add(integer);
                        }
                    }
                }
                if (tempStreetList.size() < 4) {
                    return false;
                }
                for (int j = 0; j < tempStreetList.size() - 1; j++) {
                    if (!tempStreetList.get(j).equals((tempStreetList.get(j + 1) - 1))) {
                        return false;
                    }
                }
                return true;
            }

            case STREET_BIG: {
                List<Integer> tempStreetList = new ArrayList<>();
                for (Integer integer : tempDiceList) {
                    if (tempStreetList.size() == 0) {
                        tempStreetList.add(integer);
                    } else {
                        if (!integer.equals(tempStreetList.get(tempStreetList.size() - 1))) {
                            tempStreetList.add(integer);
                        }
                    }
                }
                if (tempStreetList.size() < 5) {
                    return false;
                }
                for (int j = 0; j < tempStreetList.size() - 1; j++) {
                    if (!tempStreetList.get(j).equals((tempStreetList.get(j + 1) - 1))) {
                        return false;
                    }
                }
                return true;
            }

            case GENERAL: {
                if ((tempDiceList.get(0).equals(tempDiceList.get(1))) &&
                        (tempDiceList.get(1).equals(tempDiceList.get(2))) &&
                        (tempDiceList.get(2).equals(tempDiceList.get(3))) &&
                        (tempDiceList.get(3).equals(tempDiceList.get(4)))) {
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

    int sumCart(Cart cart) {
        int sum = 0;
        for (int i = 0; i < Figures.values().length; i++) {
            Figures figure = Figures.values()[i];
            if (cart.getResultCart().get(figure) != null) {
                sum = sum + cart.getResultCart().get(figure);
            }
        }
        return sum;
    }


    int calculatePoints(Figures figure) {
        int result = 0;
        switch (figure) {
            case ONES: {
                result = dices.sumNumber(1);
                break;
            }
            case TWOS: {
                result = dices.sumNumber(2);
                break;
            }
            case THREES: {
                result = dices.sumNumber(3);
                break;
            }
            case FOURS: {
                result = dices.sumNumber(4);
                break;
            }
            case FIVES: {
                result = dices.sumNumber(5);
                break;
            }
            case SIXES: {
                result = dices.sumNumber(6);
                break;
            }
            case THREE_SAME: {
                result = dices.sumAll();
                break;
            }
            case FOUR_SAME: {
                result = dices.sumAll();
                break;
            }
            case FULL: {
                result = 25;
                break;
            }
            case STREET_SMALL: {
                result = 30;
                break;
            }
            case STREET_BIG: {
                result = 40;
                break;
            }
            case GENERAL: {
                result = 50;
                break;
            }
            case CHANCE: {
                result = dices.sumAll();
                break;
            }
        }
        return result;
    }

}//class