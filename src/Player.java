import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Participant implements Actionable {
    // Tohle by asi melo byt zahashovany (heslo
    private final String password;
    private int money;
    private int bet;
    private final List<Card> wildCards = new ArrayList<>();

    public Player(String name, String password, int money) {
        super(name);
        this.password = password;
        this.money = money;
    }

    public List<Card> getWildCards() {
        return wildCards;
    }

    public void placeBet(int amount) {
        if (amount > money) {
            throw new IllegalArgumentException("Nedostatek peněz na sázku.");
        }
        this.bet = amount;
        this.money -= amount;
    }

    public int getBet() {
        return bet;
    }

    public void win() {
        money += bet * 2;
    }

    public void push() {
        money += bet;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }
    public String serialize() {
        return name + "," + password + "," + money;
    }

    public static Player deserialize(String line) {
        String[] parts = line.split(",");
        return new Player(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    @Override
    public void performAction(Deck deck, Dealer dealer) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + name + "ova ruka: " + hand);

            if (!wildCards.isEmpty()) {
                for (Card wild : wildCards) {
                    System.out.println("  " + wild);
                }
                System.out.println("Vyberte si divokou kartu k použití (1-" + wildCards.size() +
                                 ") nebo 0 k přeskočení");
                for (int i = 0; i < wildCards.size(); i++) {
                    System.out.println((i+1) + ") " + wildCards.get(i));
                }
            }

            System.out.print("Vyberte akci: hit / stand / double > ");
            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "hit":
                    Card drawnCard = deck.dealCard();
                    if (drawnCard.isWild()) {
                        System.out.println("Vytáhl jste divokou kartu! Odkládá se stranou.");
                        wildCards.add(drawnCard);
                        drawnCard = deck.dealCard();
                        System.out.println("Vytažena náhradní karta: " + drawnCard);
                    }
                    hand.addCard(drawnCard);

                    if (hand.getValue() > BlackjackGame.currentBustLimit) {
                        System.out.println("Bust! Hodnota ruky: " + hand.getValue());
                        return;
                    }
                    break;
                case "stand":
                    return;
                case "double":
                    if (hand.getCards().size() == 2 && money >= bet) {
                        money -= bet;
                        bet *= 2;
                        hand.addCard(deck.dealCard());
                        return;
                    } else {
                        System.out.println("Double není možný.");
                    }
                    break;
                default:
                    try {
                        int wildChoice = Integer.parseInt(action);
                        if (wildChoice > 0 && wildChoice <= wildCards.size()) {
                            Card selectedWild = wildCards.get(wildChoice - 1);
                            BlackjackGame.handleWildEffect(selectedWild, this, dealer);
                            wildCards.remove(selectedWild);
                            continue;
                        }
                    } catch (NumberFormatException _) {
                    }
                    System.out.println("Neplatná volba.");
            }
        }
    }
}
