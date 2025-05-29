public class Dealer extends Participant {
    public Dealer() {
        super("Dealer");
    }

    public void play(Deck deck) {
        while (hand.getValue() < 17) {
            Card dealerCard = deck.dealCard();
            this.getHand().addCard(dealerCard);
            if (dealerCard.isWild()) {
                this.getHand().getCards().remove(dealerCard);
            }
        }
        System.out.println("Dealer's final hand: " + hand);
    }
}
