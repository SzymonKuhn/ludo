import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String input;

    public static void main(String[] args) {

        Dices dices = new Dices();
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();

        CartManager cartManager = new CartManager(dices);
//        Scanner scanner = new Scanner(System.in);
        PlayerManager player1 = new PlayerManager(cartManager, cart1, dices);
        PlayerManager player2 = new PlayerManager(cartManager, cart2, dices);

        for (int i = 0; i < Figures.values().length; i++) { // pętla równa ilości figur

            System.out.println(cart1.toString());
            System.out.println("Naciśnij enter aby rzucić koścmi");
            scanner.nextLine();
            dices.throwAll();
            System.out.println(dices.getList());

            for (int j = 0; j < 2; j++) { //dwukrotnie rzut wybranymi kostkami

                throwAgain(player1);
                System.out.println(dices.getList());
            }
            playerAddsPointsToCart(cart1, player1, cartManager);
        }

    }//main

    private static void showFigures() {
        for (int l = 1; l <= Figures.values().length; l++) {
            System.out.println("Wpisz " + l + " dla: " + Figures.values()[l - 1].getMyName());
        }
    }

    private static void throwAgain(PlayerManager player) {
        System.out.println("Którymi kostkami chcesz rzucić ponownie?");
        while (true) {
            input = scanner.nextLine();
            if (player.choseDiceAndThrow(input)) {
                break;
            }
        }
    }

    private static void playerAddsPointsToCart (Cart cart, PlayerManager player, CartManager cartManager) {
//        Scanner scanner = new Scanner(System.in);

        if (cartManager.dicesCanBeAddedToCart(cart)) {
            System.out.println("do jakiej figury doliczyć punkty?");
            showFigures();
            System.out.println("Wpisz 0 jeśli nie chcesz dodawać wyniku (trzeba będzie wpisać 0 do wybranej figury w tabeli).");

            input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println("wybierz figurę do której zostanie wpisane 0");
                while (true) {
                    String inputIn = scanner.nextLine();
                    if (player.addZeroToCart(inputIn)) {
                        break;
                    }
                }
            } else {
                while (true) {
                    if (player.choseFigureToAddPointToCart(input)) {
                        break;
                    }
                }
            }
        } else {
            System.out.println("układ kości nie pasuje do żadnej pozostałej figury w tabeli");
            System.out.println("wybierz figurę do której zostanie wpisane 0");
            showFigures();
            while (true) {
                input = scanner.nextLine();
                if (player.addZeroToCart(input)) {
                    break;
                }
            }
        }
    }

}//class
