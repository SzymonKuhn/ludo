public class PlayerManager {
    CartManager cartManager;
    Cart cart;
    Dices dices;

    public PlayerManager(CartManager cartManager, Cart cart, Dices dices) {
        this.cartManager = cartManager;
        this.cart = cart;
        this.dices = dices;
    }

    public boolean choseFigureToAddPointToCart(String input) {

        int num = getNumberOfFigureFromString(input);

        for (int i = 0; i < Figures.values().length; i++ ) {
            if (Figures.values()[i].getNumberOfFigure() == num) {
                Figures figure = Figures.values()[i];
                if (cartManager.checkAndAddResult(figure, cart));
                return true;
            }
        }
        return false;
    }

    public boolean choseDiceAndThrow(String input) {
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
        if (dices.throwChosen(inputArray)) {
            return true;
        }
        return false;
    }

    public boolean addZeroToCart (String input) {
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
        int number=0;
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



} // class
