import java.util.Scanner;

public class Player extends Participant implements Actionable {
    private final String password;
    private int money;
    private int bet;
    private boolean doubleUsed = false;

    public Player(String name, String password, int money) {
        super(name);
        this.password = password;
        this.money = money;
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

    public boolean isDoubleUsed() {
        return doubleUsed;
    }

    @Override
    public void performAction(Deck deck) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + name + "'s hand: " + hand);
            System.out.print("Choose action: hit / stand / double > ");
            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "hit":
                    hand.addCard(deck.dealCard());
                    if (hand.getValue() > 21) {
                        System.out.println("Bust! Hand value: " + hand.getValue());
                        return;
                    }
                    break;
                case "stand":
                    return;
                case "double":
                    if (hand.getCards().size() == 2 && money >= bet) {
                        money -= bet;
                        bet *= 2;
                        doubleUsed = true;
                        hand.addCard(deck.dealCard());
                        return;
                    } else {
                        System.out.println("Double není možný.");
                    }
                    break;
                default:
                    System.out.println("Neplatná volba.");
            }
        }
    }
}
