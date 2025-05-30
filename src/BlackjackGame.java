import java.io.*;
import java.util.*;

public class BlackjackGame {
    static int currentBustLimit = 21;
    private static int dealerHandAdjustment = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file;
        Player player = null;
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        UserManager userManager = new UserManager();
        int p, d, bet;

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

        if (player.getMoney() == 0) {
            int amount;
            String choice;
            System.out.println("Nemáte žádné peníze.");
            System.out.println("1. Dobití peněz\n2. Konec");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    do {
                        System.out.println("Zadejte částku k dobití: ");
                        amount = Integer.parseInt(scanner.nextLine());
                        if (amount <= 0) {
                            System.out.println("Částka musí být kladná.");
                        }
                    } while (amount <= 0);
                        player.setMoney(player.getMoney() + amount);
                        System.out.println("Úspěšně jste dobili " + amount + " Kč.");
                    break;
                case "2":
                    System.out.println("Konec hry.");
                    return;
                default:
                    System.out.println("Neplatná volba.");
                    return;
            }
        }

        while (true) {
            currentBustLimit = 21;
            dealerHandAdjustment = 0;

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

            dealInitialCards(player, dealer, deck);

            System.out.println("Dealer ukazuje: " + dealer.getHand().getCards().getFirst() + " (hodnota: " + dealer.getHand().getCards().getFirst().getValue() + ")");
            player.performAction(deck, dealer);

            if (player.getHand().getValue() <= currentBustLimit) {
                dealer.play(deck);
            }

            p = player.getHand().getValue();
            d = dealer.getHand().getValue() + dealerHandAdjustment;

            if (p > currentBustLimit) {
                System.out.println("Prohrál jsi.");
            } else if (d > currentBustLimit || p > d) {
                System.out.println("Vyhrál jsi!");
                player.win();
            } else if (p == d) {
                System.out.println("Remíza.");
                player.push();
            } else {
                System.out.println("Prohrál jsi.");
            }

            file = new File("blackjack_log.csv");
            boolean notExists = !file.exists();
            try (FileWriter fw = new FileWriter("blackjack_log.csv", true)) {
                if (notExists) {
                    fw.write("datum;player;dealer;sazka\n");
                }
                fw.write(String.format(Locale.US, "%s;%d;%d;%d\n",
                        new Date(), p, d, player.getBet()));
            } catch (IOException e) {
                System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
            }
        }


        userManager.updatePlayer(player);
        System.out.println("Díky za hru!");
    }

    static void dealInitialCards(Player player, Dealer dealer, Deck deck) {
        int i;
        Card dealerCard;
        Card card;

        for (i = 0; i< 2; i++) {
            card = deck.dealCard();
            player.getHand().addCard(card);
            if (card.isWild()) {
                System.out.println("Vytáhl jsi divokou kartu! Rozdává se náhradní karta...");
                handleWildCard(player, card, deck);
            }
        }

        for (i = 0; i < 2; i++) {
            do {
                dealerCard = deck.dealCard();
                dealer.getHand().addCard(dealerCard);
                if (dealerCard.isWild()) {
                    System.out.println("Dealer vytáhl divokou kartu! Odebírá se a rozdává se náhradní karta...");
                    dealer.getHand().getCards().remove(dealerCard);
                }
            }while(dealerCard.isWild());
        }
    }

    static void handleWildCard(Player player, Card wildCard, Deck deck) {
        player.getHand().getCards().remove(wildCard);

        if (!player.getWildCards().contains(wildCard)) {
            player.getWildCards().add(wildCard);
        }

        Card newCard;
        do {
            newCard = deck.dealCard();
            if (newCard.isWild()) {
                System.out.println("Další divoká karta! Rozdává se náhradní karta...");
                player.getWildCards().add(newCard);
            }
        } while (newCard.isWild());

        player.getHand().addCard(newCard);
    }

    static void handleWildEffect(Card card, Player player, Dealer dealer) {
        IWildCard effect = card.getWildEffect();
        System.out.println("Divoká karta aktivována: " + card);

        if (effect instanceof RaiseLimitCardI) {
            effect.activate(player, dealer);
            System.out.println("Současný limit zvýšen na " + currentBustLimit);
        }
        else if (effect instanceof ResetHandCardI) {
            effect.activate(player, dealer);
            System.out.println("Vaše ruka byla obnovena!");
        }
        else if (effect instanceof AddToDealerCardI) {
            effect.activate(player, dealer);
            dealerHandAdjustment += ((AddToDealerCardI) effect).getValueToAdd();
            System.out.println("Dealer bude mít při evaluaci příčtenou hodnotu " +
                ((AddToDealerCardI) effect).getValueToAdd());
        }
    }
}
