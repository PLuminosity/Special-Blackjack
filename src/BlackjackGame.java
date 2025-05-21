import java.io.*;
import java.util.*;

public class BlackjackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player = new Player("Hráč", 100);
        Dealer dealer = new Dealer();
        Deck deck = new Deck();

        while (true) {
            System.out.println("\n--- Nová hra ---");
            System.out.println("Zůstatek: " + player.getMoney() + " Kč");
            System.out.print("Zadej sázku (0 = konec): ");

            int bet;
            try {
                bet = Integer.parseInt(scanner.nextLine());
                if (bet == 0) break;
                player.placeBet(bet);
            } catch (Exception e) {
                System.out.println("Neplatný vstup nebo nedostatek peněz.");
                continue;
            }

            player.resetHand();
            dealer.resetHand();
            deck.shuffle();

            player.getHand().addCard(deck.dealCard());
            player.getHand().addCard(deck.dealCard());
            dealer.getHand().addCard(deck.dealCard());
            dealer.getHand().addCard(deck.dealCard());

            System.out.println("Dealer ukazuje: " + dealer.getHand().getCards().get(0));
            player.performAction(deck);

            if (player.getHand().getValue() <= 21) {
                dealer.play(deck);
            }

            int p = player.getHand().getValue();
            int d = dealer.getHand().getValue();

            if (p > 21) {
                System.out.println("Prohrál jsi.");
            } else if (d > 21 || p > d) {
                System.out.println("Vyhrál jsi!");
                player.win();
            } else if (p == d) {
                System.out.println("Remíza.");
                player.push();
            } else {
                System.out.println("Prohrál jsi.");
            }

            try (FileWriter fw = new FileWriter("blackjack_log.csv", true)) {
                fw.write(String.format(Locale.US, "%s;%d;%d;%d\n",
                        new Date().toString(), p, d, player.getBet()));
            } catch (IOException e) {
                System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
            }
        }

        System.out.println("Díky za hru!");
    }
}
