import java.util.Scanner;

class Player {
    CartManager cartManager;
    Cart cart;
    Dices dices;
    private String name;
    Scanner scanner;
    private String input;

    Player(String name, CartManager cartManager, Cart cart, Dices dices, Scanner scanner) {
        this.cartManager = cartManager;
        this.cart = cart;
        this.dices = dices;
        this.name = name;
        this.scanner = scanner;
    }

    Cart getCart() {
        return cart;
    }

    String getName() {
        return name;
    }

    public void accept() {
        scanner.nextLine();
    }

    void throwAgain() {
        System.out.println("Którymi kostkami chcesz rzucić ponownie?");
        do {
            input = scanner.nextLine();
        } while (!choseDiceAndThrow(input));
    }

    private boolean choseFigureToAddPointToCart(String input) {
        int num = getNumberOfFigureFromString(input);
        for (int i = 0; i < Figures.values().length; i++ ) {
            if (Figures.values()[i].getNumberOfFigure() == num) {
                Figures figure = Figures.values()[i];
                if (cartManager.checkAndAddResult(figure, cart)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean choseDiceAndThrow(String input) {
        if (input.equals("")) {
            return true;
        }
        String[] stringInput = input.split(" ");
        int[] inputArray = new int[stringInput.length];
        for (int j = 0; j < stringInput.length; j++) {
            try {
                inputArray[j] = Integer.parseInt(stringInput[j]);
            } catch (NumberFormatException nfe) {
                System.out.println("Podano błędne dane");
                return false;
            }
        }
        return dices.throwChosen(inputArray);
    }

    private boolean addZeroToCart(String input) {
        int num = getNumberOfFigureFromString(input);

        for (int i = 0; i < Figures.values().length; i++ ) {
            if (Figures.values()[i].getNumberOfFigure() == num) {
                Figures figure = Figures.values()[i];
                if (cart.getResultCart().get(figure) == null) {
                    cart.addResult(figure, 0);
                    return true;
                }
                System.out.println("Podana figura jest już zajęta w tabeli");
            }
        }
        return false;
    }

    private int getNumberOfFigureFromString(String num) throws WrongNumberException {
        int number = 0;
        try {
            number = Integer.parseInt(num);
            if (number < 1 || number > 13) {
                System.out.println("podano zbyt niską lub zbyt wysoką liczbę, podaj liczbę z zakresu od 1 do 13");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Podano błędne dane");
        }

        return number;
    }


    void playerAddsPointsToCart() {
        if (cartManager.dicesCanBeAddedToCart(cart)) {
            System.out.println("do jakiej figury doliczyć punkty?");
            showFigures();
            System.out.println("Wpisz 0 jeśli nie chcesz dodawać wyniku (trzeba będzie wpisać 0 do wybranej figury w tabeli).");
            input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println("wybierz figurę do której zostanie wpisane 0");
                while (true) {
                    String inputIn = scanner.nextLine();
                    if (addZeroToCart(inputIn)) {
                        break;
                    }
                }
            } else {
                while (!choseFigureToAddPointToCart(input)) {
                    input = scanner.nextLine();
                }
            }
        } else {
            System.out.println("układ kości nie pasuje do żadnej pozostałej figury w tabeli");
            System.out.println("wybierz figurę do której zostanie wpisane 0");
            showFigures();
            do {
                input = scanner.nextLine();
            } while (!addZeroToCart(input));
        }
    }

    private void showFigures() {
        for (int l = 1; l <= Figures.values().length; l++) {
            System.out.println("Wpisz " + l + " dla: " + Figures.values()[l - 1].getMyName() + " (obecny wynik: " + cart.getResultCart().get(Figures.values()[l - 1]) + ").");
        }
    }

} // class
