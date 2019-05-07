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
        Player player1 = new Player("Gracz 1", cartManager, player1cart, dices, scanner);
//        VirtualPlayer player1 = new VirtualPlayer("Gracz 1", cartManager, player1cart, dices, scanner);
        VirtualPlayer player2 = new VirtualPlayer("Gracz 2", cartManager, player2cart, dices, scanner);
        Player[] players = new Player[]{player1, player2};

        for (int i = 0; i < Figures.values().length; i++) { // pętla równa ilości figur
//            for (int i = 0; i < 1; i++) { // tymczasowo mniejsza ilość tur
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
                player.accept();
                dices.throwAll();
                System.out.println(dices.getList());

                for (int k = 0; k < 2; k++) { //dwukrotnie rzut wybranymi kostkami
                    player.throwAgain();
                    System.out.println(dices.getList());
                }

                player.playerAddsPointsToCart();
                System.out.println("Twoja karta wyników:");
                System.out.println(player.getCart().toString());
                System.out.println("Naciśnij enter aby kontynuować");
                player.accept();
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