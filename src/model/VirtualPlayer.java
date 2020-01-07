package model;

import model.Cart;
import model.Dices;
import model.Figures;
import model.Player;
import service.CartManager;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class VirtualPlayer extends Player {


    public VirtualPlayer(String name, CartManager cartManager, Cart cart, Dices dices, Scanner scanner) {
        super(name, cartManager, cart, dices, scanner);
    }


    @Override
    public void throwAgain() {

        System.out.println("Gracz wirtualny rzuca ponownie");
//        System.out.println("Mapa powtarzalnych kości");
//        System.out.println(getMapOfRepetedDices().toString());
//        System.out.println("Kości niepowtarzalne: ");
        System.out.println("Rzucam koścmi numer:");
        int[] dicesToThrow = getArrayOfDicesToThrow();
        for (int dice : dicesToThrow) {
            System.out.print(dice + ", ");
        }
        System.out.println();
//        System.out.println("Mapa możiwości z punktami: " + checkPossibleFiguresToAddAndCalculatePoints().toString());
//        System.out.println("Mapa procentów maksymalnych punktów: " + getMapOfPercentageOfMaxPoints());
        dices.throwChosen(dicesToThrow);
        waiting();
//        scanner.nextLine();
    }


    @Override
    public void playerAddsPointsToCart() {
        System.out.println("Gracz wirtualny dodaje punkty do tabeli");
        Map<Figures, Integer> mapOfPossibleFiguresToAddAndCaluculatePoints = checkPossibleFiguresToAddAndCalculatePoints();
        Map<Figures, Double> getMapOfPercentageOfMaxPoints = getMapOfPercentageOfMaxPoints();
        List<Figures> listOfFiguresCanBeAdded = new ArrayList<>(getMapOfPercentageOfMaxPoints.keySet());
        Figures figure = null;
        Double maxPercentage = 0.0;
        if (mapOfPossibleFiguresToAddAndCaluculatePoints.isEmpty()) { //jeśli nie ma wmożliwości dopisania punktów
            for (Figures figures2 : Figures.values()) {
                if (cart.getResultCart().get(figures2) == null) {
                    cart.addResult(figures2, 0); //dodaję zero do pierwszej wolnej
                    return;
                }
            }
        } else if (mapOfPossibleFiguresToAddAndCaluculatePoints.size() == 1  && mapOfPossibleFiguresToAddAndCaluculatePoints.containsKey(Figures.CHANCE)){ //jeśli jedyną możliwością dodania jest Szansa
            cart.addResult(listOfFiguresCanBeAdded.get(0), mapOfPossibleFiguresToAddAndCaluculatePoints.get(listOfFiguresCanBeAdded.get(0))); //dodaję szansę
        } else { //jeśli są wolne miejsca
            for (int i = 11; i > 5; i--) {
                if (listOfFiguresCanBeAdded.contains(Figures.values()[i])) { //iteruję od Generała do 3 takie same, jeśli mogę dodać to dodaję
                    figure = Figures.values()[i];
                    System.out.println("Dodaję do karty wyników: " + figure.getMyName());
                    cart.addResult(figure, mapOfPossibleFiguresToAddAndCaluculatePoints.get(figure));
                    return;
                }
            }
            for (int i = 0; i < 6; i++) { //iteruję od jedynek do szóstek
                Figures temp = Figures.values()[i];
                if (listOfFiguresCanBeAdded.contains(temp)) { //jeśli jedną z nich można oddać
                    if (getMapOfPercentageOfMaxPoints.get(temp) > maxPercentage) {
                        figure = temp;
                        maxPercentage = getMapOfPercentageOfMaxPoints.get(temp);
                    }
                }
            }
            System.out.println("Dodaję do karty wyników: " + figure.getMyName());
            cart.addResult(figure, mapOfPossibleFiguresToAddAndCaluculatePoints.get(figure));
        }
    }

    public void accept() {
        System.out.println("Gracz wirtualny akceptuje");
        waiting();
    }


    private void waiting() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException ie) {
            ie.getMessage();
        }
    }

    private Map<Integer, List<Integer>> getMapOfRepetedDices() { //klucz: oczka, wartość: lista kości z tymi oczkami
        Map<Integer, List<Integer>> mapOfRepetedDices = new HashMap<>();
        for (int i = 0; i < dices.getList().size(); i++) {
            Integer value = dices.getList().get(i).getValue();
            Integer numberOfDice = i + 1;
            List<Integer> list = new ArrayList<>();
            if (mapOfRepetedDices.keySet().contains(value)) {
                list = mapOfRepetedDices.get(value);
                list.add(numberOfDice);
                mapOfRepetedDices.put(value, list);
            } else {
                list.add(numberOfDice);
                mapOfRepetedDices.put(value, list);
            }
        }
        return mapOfRepetedDices;
    }

//    private int[] getArrayOfDicesNotRepeted() {
//        Map<Integer, List<Integer>> mapOfRepetedDices = getMapOfRepetedDices();
//        Set<Integer> setOfKeys = mapOfRepetedDices.keySet();
//        List<Integer> tempList = new ArrayList<>();
//        for (Integer key : setOfKeys) {
//            if (mapOfRepetedDices.get(key).size() == 1) {
//                int numberOfDice = mapOfRepetedDices.get(key).get(0);
//                tempList.add(numberOfDice);
//            }
//        }
//        int[] arrayOfDicesNotRepeted = new int[tempList.size()];
//        for (int i = 0; i < tempList.size(); i++) {
//            arrayOfDicesNotRepeted[i] = tempList.get(i);
//        }
//        return arrayOfDicesNotRepeted;
//    }


    private Map<Figures, Integer> checkPossibleFiguresToAddAndCalculatePoints() {
        Map<Figures, Integer> mapOfPossibleFiguresToAddAndCaluculatePoints = new HashMap<>();
        for (Figures figure : Figures.values()) {
            if (cartManager.checkResult(figure)) {
                if (cart.getResultCart().get(figure) == null) {
                    mapOfPossibleFiguresToAddAndCaluculatePoints.put(figure, cartManager.calculatePoints(figure));
                }
            }
        }
        return mapOfPossibleFiguresToAddAndCaluculatePoints;
    }

    private Map<Figures, Double> getMapOfPercentageOfMaxPoints() {
        Map<Figures, Integer> mapOfPossibleFiguresToAdd = checkPossibleFiguresToAddAndCalculatePoints();
        Set<Figures> keySet = mapOfPossibleFiguresToAdd.keySet();
        Map<Figures, Double> mapOfPercentageOfMaxPoints = new HashMap<>();
        for (Figures figure : keySet) {
            Double points = mapOfPossibleFiguresToAdd.get(figure).doubleValue();
            int maxPoints = figure.getMaxPoints();
            Double value = (points / maxPoints);
            mapOfPercentageOfMaxPoints.put(figure, value);
        }
        return mapOfPercentageOfMaxPoints;
    }

    private int[] getArrayOfDicesToThrow() {
        Map<Integer, List<Integer>> mapOfRepetedDices = getMapOfRepetedDices(); //mapa powtarzalnych wyników kości
        List<Integer> listOfDicesResults = new ArrayList<>(mapOfRepetedDices.keySet()); //lista wyników na oczkach
        List<Integer> listOfDicesToThrow = new ArrayList<>();
        int maxRepetedDices = 0; // ilość kości powtarzalnych w największej ilości
        int valueOfMaxRepetedDices = 0; // wartość na kościach powtarzalnych w największej ilości
        int minRepetedDices = 6; // ilość kości powtarzalnych w najmniejszej ilości
        int valuesOfMinRepetedDices = 7; //wartość na kościach powtarzalnych w najmniejszej ilości
        for (Integer integer : listOfDicesResults) {
            if (mapOfRepetedDices.get(integer).size() > maxRepetedDices) {
                maxRepetedDices = mapOfRepetedDices.get(integer).size();
                valueOfMaxRepetedDices = integer;
            }
            if (mapOfRepetedDices.get(integer).size() < minRepetedDices) {
                minRepetedDices = mapOfRepetedDices.get(integer).size();
                valuesOfMinRepetedDices = integer;
            }
        }

        if (mapOfRepetedDices.size() == 1) { //jeśli układ 5 takich samych
            int num = listOfDicesResults.get(0);
            Figures figure = Figures.values()[num];
            if (cart.getResultCart().get(Figures.GENERAL) == null) { //jeśli generał jest wolny
                //zwrócenie pustej listy
                System.out.println("5 takich samych na kostkach, GENERAŁ wolny, nie rzucam");
            } else if (cart.getResultCart().get(figure) == null) { //jeśli oczka są wolne
                //zwrócenie pustej listy
                System.out.println("5 takich samych na kostkach, oczka wolne, nie rzucam");
            } else {
                listOfDicesToThrow.addAll(addAllDicesToListToThrow()); //dodanie wszystkich kości do listy kości do rzutu
                System.out.println("5 takich samych na kostkach, oczka i GENERAŁ zajęte, rzucam wszystkimi");
            }
        } else if (mapOfRepetedDices.size() == 2) { // jeśli układ 1/4 lub 2/3
            if (maxRepetedDices == 3) { //jeśli układ 2/3
                if (cart.getResultCart().get(Figures.FULL) == null) { //jeśli full jest wolny
                    //zwrócenie pustej listy
                    System.out.println("Układ 2/3, full wolny, nie rzucam");
                } else { //jeśli full zajęty to rzut dwiema powtarzalnymi kostkami
                    System.out.println("Układ 2/3, full zajęty, rzucam dwiema powtarzalnymi kostkami");
                    for (Integer integer : listOfDicesResults) {
                        if (mapOfRepetedDices.get(integer).size() == 2) { //sprawdzenie który wynik ma tylko dwie kości
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer)); //dodanie kości do listy kości do rzutu
                        }
                    }
                }
            } else { //jeśli układ 1/4
                if (cart.getResultCart().get(Figures.GENERAL) == null) { //jeśli generał jest wolny
                    System.out.println("Układ 1/4, GENERAŁ wolny, rzucam pojedynczą");
                    for (Integer integer : listOfDicesResults) {
                        if (mapOfRepetedDices.get(integer).size() == 1) {
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer));
                        }
                    }
                } else if (cart.getResultCart().get(Figures.FOUR_SAME) == null) { //jeśli 4 takie same są wolne
                    System.out.println("Układ 1/4, GENERAŁ zajęty, 4 takie same wolne, nie rzucam");
                    //zwrócenie pustej listy,
                    //
                    // DODAĆ SPRAWDZENIE CZY POJEDYNCZA JEST MNIEJSZA NIŻ 3 I JEŚLI TAK TO RZUT
                    //
                } else if (cart.getResultCart().get(Figures.FULL) == null) { //jeśli full jest wolny
                    System.out.println("Układ 1/4, full wolny, rzucam jedną z 4 powtarzalnych");
                    for (Integer integer : listOfDicesResults) {
                        if (mapOfRepetedDices.get(integer).size() == 4) {
                            listOfDicesResults.add(mapOfRepetedDices.get(integer).get(0)); //dodanie do listy rzucanych kości jednej z 4 powtórzonych kości
                        }
                    }
                } else { //jeśli generał, 4 takie same i full są zajęte
                    System.out.println("Układ 1/4, GENERAŁ, 4 takie same i full zajęty");
                    for (Integer integer : listOfDicesResults) {
                        if (mapOfRepetedDices.get(integer).size() == 4) {
                            Figures figure = Figures.values()[integer - 1];
                            if (cart.getResultCart().get(figure) == null) { //jeśli oczka są wolne
                                System.out.println("Oczka powtarzalnych wolne, rzucam pojedynczą");
                                for (Integer integer2 : listOfDicesResults) {
                                    if (mapOfRepetedDices.get(integer2).size() == 1) {
                                        listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer2)); //dodanie pojedydnczej kości do listy kości do rzutu
                                    }
                                }
                            } else { //jeśli oczka sa zajęte
                                System.out.println("Oczka zajęte, rzucam wszystkimi");
                                listOfDicesToThrow.addAll(addAllDicesToListToThrow()); //dodanie wszystkich kości do listy kości do rzutu
                            }
                        }
                    }
                }
            }
        } else if (mapOfRepetedDices.size() == 3) { // 1 2 333 lub 1 22 33
            if (maxRepetedDices == 3) { //jeśli układ 1 2 333
                if (cart.getResultCart().get(Figures.FOUR_SAME) == null
                        || cart.getResultCart().get(Figures.THREE_SAME) == null || cart.getResultCart().get(Figures.values()[valueOfMaxRepetedDices - 1]) == null) {
                    //4 takie same lub 3 takie same lub oczka są wolne
                    System.out.println("Układ 1 2 333, 4 takie same lub 3 takie same lub oczka wolne, rzucam pojedynczymi");
                    for (Integer integer : listOfDicesResults) {
                        if (mapOfRepetedDices.get(integer).size() < 3) {
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer)); //dodanie pojedynczych kości do listy kości do rzutu
                        }
                    }
                } else { //4 takie same i 3 takie same i oczka są zajęte
                    System.out.println("Układ 1 2 333, 4 takie same lub 3 takie same lub oczka zajęte, rzucam wszystkimi");
                    listOfDicesToThrow.addAll(addAllDicesToListToThrow()); //dodanie wszystkich kości do rzutu  <- MOŻNA JESZCZE POPRAWIĆ, ROZBIĆ NA WIĘCEJ MOŻLIWOŚCI
                }
            } else { // jeśli układ 1 22 33
                System.out.println("Układ 1 22 33");
                Figures figureSingle = Figures.values()[valuesOfMinRepetedDices - 1];
                Figures figureRepeted1 = Figures.values()[valueOfMaxRepetedDices - 1];
                Figures figureRepeted2 = null;
                for (Integer integer : listOfDicesResults) {
                    if (integer != valueOfMaxRepetedDices && integer != valuesOfMinRepetedDices) {
                        figureRepeted2 = Figures.values()[integer - 1];
                    }
                }
                if (cart.getResultCart().get(Figures.FULL) == null) { //jeśli full jest wolny
                    System.out.println("Full wolny, rzucam pojedynczą");
                    listOfDicesToThrow.addAll(mapOfRepetedDices.get(valuesOfMinRepetedDices)); //dodanie pojedynczej kostki do listy kości do rzutu
                } else if (cart.getResultCart().get(Figures.THREE_SAME) == null || cart.getResultCart().get(Figures.FOUR_SAME) == null) { //jeśli trzy takie same lub cztery takie same są wolne
                    System.out.println("4 takie same lub 3 takie same wolne");
                    if (cart.getResultCart().get(Figures.values()[valueOfMaxRepetedDices - 1]) == null) { //jeśli oczka powtarzalnych maksymalnych kości są wolne
                        System.out.println("oczka powtarzalnych maksymalnych wolne, rzucam pozostałymi");
                        for (Integer integer : listOfDicesResults) {
                            if (integer != valueOfMaxRepetedDices) {
                                listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer)); //dodaj do listy do rzucania pozostałe kości
                            }
                        }
                    } else { //jeśli oczka powtarzalnych maksymalnych kości są zajęte
                        System.out.println("oczka powtarzalnych maksymalnych zajęte");

                        for (Integer integer : listOfDicesResults) {
                            if (integer != valuesOfMinRepetedDices || integer != valueOfMaxRepetedDices) {
                                if (cart.getResultCart().get(Figures.values()[integer - 1]) == null) { //jeśli oczka drugiej pary są wolne
                                    System.out.println("Oczka pozostałej pary wolne, rzucam resztą");
                                    listOfDicesToThrow.addAll(mapOfRepetedDices.get(valueOfMaxRepetedDices)); //dodaj kości powtarzalne pierwszej pary do listy kości do rzutu
                                    listOfDicesToThrow.addAll(mapOfRepetedDices.get(valuesOfMinRepetedDices)); //dodaj kość pojedynczą do listy kości do rzutu
                                } else { //jeśli oczka drugiej pary również sa zajęte
                                    System.out.println("Oczka pozostałej pary zajęte, rzucam do streeta");
                                    listOfDicesToThrow = addDicesToListToThrowForStreet(mapOfRepetedDices, listOfDicesResults, maxRepetedDices, valueOfMaxRepetedDices, minRepetedDices, valuesOfMinRepetedDices);
                                }
                            }
                        }
                    }
                } else if (cart.getResultCart().get(Figures.STREET_BIG) == null || cart.getResultCart().get(Figures.STREET_SMALL) == null) { //jeśli streety są wolne
                    System.out.println("Street mały lub duży wolny");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!rzucamy do streetów!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    waiting();
                    listOfDicesToThrow = addDicesToListToThrowForStreet(mapOfRepetedDices, listOfDicesResults, maxRepetedDices, valueOfMaxRepetedDices, minRepetedDices, valuesOfMinRepetedDices);
                } else if (cart.getResultCart().get(figureRepeted1) == null) { //jeśli oczka powtarzalnych maksymalnych są wolne
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!rzucamy do kości powtarzalnych, maksymalnych!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    waiting();
                    for (Integer integer : listOfDicesResults) {
                        if (integer != valueOfMaxRepetedDices) {
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer)); // dodajemy pozostałe kości do rzutu
                        }
                    }
                } else if (cart.getResultCart().get(figureRepeted2) == null) { //jeśli oczka powtarzalnych niemaksymalnych są wolne
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!rzucamy do kości powtarzalnych, niemaksymalnych!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    waiting();
                    for (Integer integer : listOfDicesResults) {
                        if (integer == valueOfMaxRepetedDices || integer == valuesOfMinRepetedDices) {
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer)); // dodajemy pozostałe kości do rzutu
                        }
                    }
                } else if (cart.getResultCart().get(figureSingle) == null) { // jeśli oczka pojedynczej kostki są wolne
                    System.out.println("oczka pojedynczej kostki wolne, rzucam pozostałymi");
                    for (Integer integer : listOfDicesResults) {
                        if (integer != valuesOfMinRepetedDices) {
                            listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer));
                        }
                    }
                } else { // wszystko zajęte, rzucam wszystkimi
                    System.out.println("Wszystkie oczka zajęte, rzucam wszystkimi");
                    listOfDicesToThrow.addAll(addAllDicesToListToThrow());
                }
            }
        } else if (mapOfRepetedDices.size() == 4) { // 1 2 3 44
            if (valueOfMaxRepetedDices == 5 && cart.getResultCart().get(Figures.FIVES) == null) {
                System.out.println("Układ 1 2 3 44, para 5, 5 wolne, rzucam pozostałymi");
                for (Integer integer : listOfDicesResults) {
                    if (integer != valueOfMaxRepetedDices) {
                        listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer));
                    }
                }
            } else if (valueOfMaxRepetedDices == 6 && cart.getResultCart().get(Figures.SIXES) == null) {
                System.out.println("Układ 1 2 3 44, para 6, 6 wolne, rzucam pozostałymi");
                for (Integer integer : listOfDicesResults) {
                    if (integer != valueOfMaxRepetedDices) {
                        listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer));
                    }
                }
            } else if (cart.getResultCart().get(Figures.STREET_BIG) == null || cart.getResultCart().get(Figures.STREET_SMALL) == null){
                System.out.println("Układ 1 2 3 44, 5 i 6 zajęte, duży lub mały street wolny, rzucam do streeta");
                listOfDicesToThrow.addAll(addDicesToListToThrowForStreet(mapOfRepetedDices, listOfDicesResults, maxRepetedDices, valueOfMaxRepetedDices, minRepetedDices, valuesOfMinRepetedDices));
            } else {
                System.out.println("Układ 1 2 3 44, para jest niższa niż 5 lub 6 lub są zajęte, streety zajęte, rzucam wszystkimi");
                listOfDicesToThrow.addAll(addAllDicesToListToThrow());
            }

        } else if (mapOfRepetedDices.size() == 5) { // 1 2 3 4 5 || 1 3 4 5 6 || 1 2 4 5 6 .....
            if (cart.getResultCart().get(Figures.STREET_BIG) == null || cart.getResultCart().get(Figures.STREET_SMALL) == null) {
                System.out.println("Układ 1 2 3 4 5, mały lub duży street wolny, rzucam do streeta");
                listOfDicesToThrow.addAll(addDicesToListToThrowForStreet(mapOfRepetedDices, listOfDicesResults, maxRepetedDices, valueOfMaxRepetedDices, minRepetedDices, valuesOfMinRepetedDices));
            } else {
                System.out.println("Układ 1 2 3 4 5 zajęty, wybieram najwyższą kostkę i rzucam pozostałymi");
                for (Integer integer : listOfDicesResults) {
                    if (integer != valueOfMaxRepetedDices) {
                        listOfDicesToThrow.addAll(mapOfRepetedDices.get(integer));
                    }
                }
            }
        }
        return getArrayFromList(listOfDicesToThrow);
    }


    private int[] getArrayFromList(List<Integer> list) {
        if (list.size() == 0) {
            int[] array = new int[0];
            return array;
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    private List<Integer> addAllDicesToListToThrow() {
        List<Integer> listOfDicesToThrow = new ArrayList<>();
        for (int i = 1; i < 6; i++) { // dodanie wszystkich kości do listy kości do rzutu
            listOfDicesToThrow.add(i);
        }
        return listOfDicesToThrow;
    }

    private List<Integer> addDicesToListToThrowForStreet(Map<Integer, List<Integer>> mapOfRepetedDices, //DO KOREKTY, BO W PRZYPADKU 1 3 4 5 6 ITP NIE BĘDZIE RZUCAŁ DO DUŻEGO STREETA
                                                         List<Integer> listOfDicesResults, int maxRepetedDices,
                                                         int valueOfMaxRepetedDices, int minRepetedDices,
                                                         int valuesOfMinRepetedDices) {
        List<Integer> listOfDicesToThrow = new ArrayList<>();

        if (mapOfRepetedDices.size() == 5) { //w układzie 1 2 3 4 5
            //zwrócenie pustej listy
        } else if (mapOfRepetedDices.size() == 4) { // w układzie 11 2 3 4
            listOfDicesToThrow.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(0)); //dodanie kostki powtarzalnej
        } else if (mapOfRepetedDices.size() == 3) { // w układzie 111 2 3 lub 11 22 3
            if (maxRepetedDices == 3) { // w układzie 111 2 3
                listOfDicesToThrow.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(0)); //dodanie pierwszej z 3 powtarzalnych kostek
                listOfDicesToThrow.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(1)); //dodanie drugiej z 3 powitarzalnych kostek
            } else { // w układzie 11 22 3
                for (Integer integer : listOfDicesResults) {
                    if (mapOfRepetedDices.get(integer).size() > 1) { //jeśli kostka jest powtarzalna
                        listOfDicesToThrow.add(mapOfRepetedDices.get(integer).get(0)); //dodaj pierwszą z powtarzalnych do listy
                    }
                }
            }
        } else if (mapOfRepetedDices.size() == 2) { //w układzie 1 2222 lub 11 222
            if (maxRepetedDices == 4) { // w układzie 1 2222
                for (int i = 0; i < 3; i++) {
                    listOfDicesToThrow.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(i)); //dodanie trzech kostek z 4 powtarzalnych
                }
            } else { // w układzie 11 222
                listOfDicesResults.add(mapOfRepetedDices.get(valuesOfMinRepetedDices).get(0)); //dodanie 1 z 2 powtarzalnych
                listOfDicesResults.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(0)); //dodanie 1 z 3 powtarzalnych
                listOfDicesResults.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(1)); //dodanie 2 z 3 powtarzlanych
            }
        } else { //w układzie 11111
            for (int i = 0; i < 4; i++) {
                listOfDicesToThrow.add(mapOfRepetedDices.get(valueOfMaxRepetedDices).get(i)); //dodanie czterech kostek z 5 powtarzalnych
            }
        }
        return listOfDicesToThrow;
    }

}//class


