public class Card {
    public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES, WILDCARD }
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11),
        WILD_RAISE_24(0), WILD_RESET_HAND(0), WILD_ADD_DEALER(0);

        private final int value;
        Rank(int value) { this.value = value; }
        public int getValue() { return value; }
    }

    private final Suit suit;
    private final Rank rank;
    private WildCard wildEffect;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;

        if (suit == Suit.WILDCARD) {
            switch(rank) {
                case WILD_RAISE_24:
                    wildEffect = new RaiseLimitCard();
                    break;
                case WILD_RESET_HAND:
                    wildEffect = new ResetHandCard();
                    break;
                case WILD_ADD_DEALER:
                    wildEffect = new AddToDealerCard();
                    break;
                default:
                    wildEffect = null;
            }
        }
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        if (isWild()) {
            return "[Divoká] " + wildEffect.getDescription();
        }
        return rank + " of " + suit;
    }
    public boolean isWild() {
        return wildEffect != null;
    }
    public WildCard getWildEffect() {
        return wildEffect;
    }
}
