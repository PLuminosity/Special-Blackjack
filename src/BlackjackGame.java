import java.io.*;
import java.util.*;

public class BlackjackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player = null;
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        UserManager userManager = new UserManager();
        int p = 0, d = 0, bet;

        System.out.println("Vítejte ve hře Blackjack!");
        while (player == null) {
            System.out.println("Přihlásit/Registrovat? (p/r): ");
            String choice = scanner.nextLine();

            System.out.println("Zadejte uživatelské jméno: ");
            String name = scanner.nextLine();
            System.out.println("Zadejte heslo: ");
            String password = scanner.nextLine();
            if (choice.equalsIgnoreCase("p")) {
                player = userManager.loginPlayer(name, password);
                if(player == null) {
                    System.out.println("Přihlášení selhalo.");
                } else {
                    System.out.println("Vítejte zpět, " + player.getName() + "!");
                }
            } else if (choice.equalsIgnoreCase("r")) {
                if (userManager.registerPlayer(name, password)) {
                    player = userManager.loginPlayer(name, password);
                    System.out.println("Registrace úspěšná!");
                    System.out.println("Vítejte, " + player.getName() + "!");
                } else {
                    System.out.println("Zadaný uživatel již existuje.");
                }
            } else {
                System.out.println("Neplatná volba.");
            }

        }

        while (true) {
            System.out.println("\n--- Nová hra ---");
            System.out.println("Zůstatek: " + player.getMoney() + " Kč");
            System.out.print("Zadej sázku (0 = konec): ");


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

            System.out.println("Dealer ukazuje: " + dealer.getHand().getCards().getFirst());
            player.performAction(deck);

            if (player.getHand().getValue() <= 21) {
                dealer.play(deck);
            }

            p = player.getHand().getValue();
            d = dealer.getHand().getValue();

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
        }
        File file = new File("blackjack_log.csv");
        boolean notExists = !file.exists();
        try (FileWriter fw = new FileWriter("blackjack_log.csv", true)) {
            if (notExists) {
                fw.write("date;player;dealer;bet\n");
            }
            fw.write(String.format(Locale.US, "%s;%d;%d;%d\n",
                    new Date(), p, d, player.getBet()));
        } catch (IOException e) {
            System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
        }

        userManager.updatePlayer(player);
        System.out.println("Díky za hru!");
    }
}
