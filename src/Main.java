import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String input;

    public static void main(String[] args) {

        Dices dices = new Dices();
        CartManager cartManager = new CartManager(dices);
        Cart player1cart = new Cart();
        Cart player2cart = new Cart();
        Player player1 = new Player("Gracz 1", cartManager, player1cart, dices);
        Player player2 = new Player("Gracz 2", cartManager, player2cart, dices);
        Player[] players = new Player[]{player1, player2};

        for (int i = 0; i < Figures.values().length; i++) { // pętla równa ilości figur
//            for (int i = 0; i < 1; i++) { // tymczasowo tury

                int round = i + 1;
            int rounds = Figures.values().length + 1;
            System.out.println("-------------------------------------------------------");
            System.out.println(">>>>>>> Tura " + round + " z " + rounds + " <<<<<<<<<<<<");
            for (int j = 0; j < players.length; j++) { //pętla - po jednej turze na zawodnika
                System.out.println();
                System.out.println(">>>>>>>>>>>>>>>> " + players[j].getName() + " <<<<<<<<<<<<<<");
                System.out.println();
                System.out.println("Twoja karta wyników: ");
                System.out.println(players[j].getCart().toString());
                System.out.println("Naciśnij enter aby rzucić koścmi");
                scanner.nextLine();
                dices.throwAll();
                System.out.println(dices.getList());
                for (int k = 0; k < 2; k++) { //dwukrotnie rzut wybranymi kostkami
                    throwAgain(player1);
                    System.out.println(dices.getList());
                }
                playerAddsPointsToCart(players[j].getCart(), players[j], cartManager);
                System.out.println("Twoja karta wyników:");
                System.out.println(players[j].getCart().toString());
                System.out.println("Naciśnij enter aby kontynuować");
                scanner.nextLine();
                clearScreen();
            }
        }

        Map<Player, Integer> resultMap = new HashMap<>();
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> W Y N I K I <<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();
        for (int l = 0; l < players.length; l++) {
            System.out.println(">>>>>>>>>>>>>>>>>> " + players[l].getName() + " <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("Tabela wyników: " + players[l].getCart().toString());
            System.out.println(">>>>> Zdobyłeś łącznie punktów: " + cartManager.sumCart(players[l].getCart()) + " <<<<<<<");
            System.out.println();
            resultMap.put(players[l], cartManager.sumCart(players[l].getCart()));
        }


    }//main

    private static void showFigures(Cart cart) {
        for (int l = 1; l <= Figures.values().length; l++) {
            System.out.println("Wpisz " + l + " dla: " + Figures.values()[l - 1].getMyName() + " (obecny wynik: " + cart.getResultCart().get(Figures.values()[l - 1]) + ").");
        }
    }

    private static void throwAgain(Player player) {
        System.out.println("Którymi kostkami chcesz rzucić ponownie?");
        while (true) {
            input = scanner.nextLine();
            if (player.choseDiceAndThrow(input)) {
                break;
            }
        }
    }

    private static void playerAddsPointsToCart (Cart cart, Player player, CartManager cartManager) {
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
                while (true) {
                    if (player.choseFigureToAddPointToCart(input)) {
                        break;
                    }
                    input = scanner.nextLine();
                }
            }
        } else {
            System.out.println("układ kości nie pasuje do żadnej pozostałej figury w tabeli");
            System.out.println("wybierz figurę do której zostanie wpisane 0");
            showFigures(cart);
            while (true) {
                input = scanner.nextLine();
                if (player.addZeroToCart(input)) {
                    break;
                }
            }
        }
    }

    public static void clearScreen() {
            try
            {
                final String os = System.getProperty("os.name");

                if (os.contains("Windows"))
                {
                    Runtime.getRuntime().exec("cls");
                }
                else
                {
                    Runtime.getRuntime().exec("clear");
                }
            }
            catch (final Exception e)
            {
                //  Handle any exceptions.

        }
    }



}//class
