public class Dealer extends Participant {
    public Dealer() {
        super("Dealer");
    }

    public void play(Deck deck) {
        while (hand.getValue() < 17) {
            hand.addCard(deck.dealCard());
        }
        System.out.println("Dealer's final hand: " + hand);
    }
}
