import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String input;

    public static void main(String[] args) {

        Dices dices = new Dices();
        CartManager cartManager = new CartManager(dices);
        Cart player1cart = new Cart();
        Cart player2cart = new Cart();
        Player player1 = new Player("Gracz 1", cartManager, player1cart, dices, false);
        Player player2 = new Player("Gracz 2", cartManager, player2cart, dices, false);
        Player[] players = new Player[]{player1, player2};

        for (int i = 0; i < Figures.values().length; i++) { // pętla równa ilości figur
//            for (int i = 0; i < 1; i++) { // tymczasowo tury

            int round = i + 1;
            int rounds = Figures.values().length;
            System.out.println("-------------------------------------------------------");
            System.out.println(">>>>>>> Tura " + round + " z " + rounds + " <<<<<<<<<<<<");

            for (Player player : players) { //pętla - po jednej turze na zawodnika
                System.out.println();
                System.out.println(">>>>>>>>>>>>>>>> " + player.getName() + " <<<<<<<<<<<<<<");
                System.out.println();
                System.out.println("Twoja karta wyników: ");
                System.out.println(player.getCart().toString());
                System.out.println("Naciśnij enter aby rzucić koścmi");
                if (player.virtualPlayer) {
                    // gracz wirtualny potwierdza
                } else {
                    scanner.nextLine(); //gracz niewirtualny potiwerdza
                }
                dices.throwAll();
                System.out.println(dices.getList());
                for (int k = 0; k < 2; k++) { //dwukrotnie rzut wybranymi kostkami
                    if (player.virtualPlayer) {
                        //gracz wirtualny wybiera i rzuca
                    } else {
                        throwAgain(player);
                    }
                    System.out.println(dices.getList());
                }
                if (player.virtualPlayer) {
                    //gracz wirtualny dodaje punkty do tabeli wyników
                } else {
                    playerAddsPointsToCart(player.getCart(), player, cartManager);
                }
                System.out.println("Twoja karta wyników:");
                System.out.println(player.getCart().toString());
                System.out.println("Naciśnij enter aby kontynuować");
                if (player.virtualPlayer) {
                    //gracz wirtualny potwierdza
                } else {
                    scanner.nextLine(); // gracz niewirtualny potwierdza
                }
                clearScreen();
            }
        }

        Map<Player, Integer> resultMap = new HashMap<>();
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> W Y N I K I <<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();
        for (Player player : players) {
            System.out.println(">>>>>>>>>>>>>>>>>> " + player.getName() + " <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("Tabela wyników: " + player.getCart().toString());
            System.out.println(">>>>> Zdobyłeś łącznie punktów: " + cartManager.sumCart(player.getCart()) + " <<<<<<<");
            System.out.println();
            resultMap.put(player, cartManager.sumCart(player.getCart()));
        }
        System.out.println("--------------------- GRĘ WYGRYWA ---------------------");
        if (resultMap.get(player1) > resultMap.get(player2)) {
            System.out.println(" >>>>>>>>>>>>>>>>> " + player1.getName() + " <<<<<<<<<<<<<<<<");
        } else if (resultMap.get(player1).equals(resultMap.get(player2))) {
            System.out.println(" >>>>>>>>>>>>>>>>> nikt (remis) <<<<<<<<<<<<<<<<");
        } else {
            System.out.println(" >>>>>>>>>>>>>>>>> " + player2.getName() + " <<<<<<<<<<<<<<<<");
        }


    }//main

    private static void showFigures(Cart cart) {
        for (int l = 1; l <= Figures.values().length; l++) {
            System.out.println("Wpisz " + l + " dla: " + Figures.values()[l - 1].getMyName() + " (obecny wynik: " + cart.getResultCart().get(Figures.values()[l - 1]) + ").");
        }
    }

    private static void throwAgain(Player player) {
        System.out.println("Którymi kostkami chcesz rzucić ponownie?");
        do {
            input = scanner.nextLine();
        } while (!player.choseDiceAndThrow(input));
    }

    private static void playerAddsPointsToCart(Cart cart, Player player, CartManager cartManager) {
//        Scanner scanner = new Scanner(System.in);

        if (cartManager.dicesCanBeAddedToCart(cart)) {
            System.out.println("do jakiej figury doliczyć punkty?");
            showFigures(cart);
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
                while (!player.choseFigureToAddPointToCart(input)) {
                    input = scanner.nextLine();
                }
            }
        } else {
            System.out.println("układ kości nie pasuje do żadnej pozostałej figury w tabeli");
            System.out.println("wybierz figurę do której zostanie wpisane 0");
            showFigures(cart);
            do {
                input = scanner.nextLine();
            } while (!player.addZeroToCart(input));
        }
    }

    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.

        }
    }


}//class
