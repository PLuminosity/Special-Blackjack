import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private static final int WILDCARD_COUNT = 4;

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            if (suit != Card.Suit.WILDCARD) {
                for (Card.Rank rank : Card.Rank.values()) {
                    if (!isWildRank(rank)) {
                        cards.add(new Card(suit, rank));
                    }
                }
            }
        }

        for (int i = 0; i < WILDCARD_COUNT; i++) {
            Card.Rank wildRank = getRandomWildRank();
            cards.add(new Card(Card.Suit.WILDCARD, wildRank));
        }

        shuffle();
    }

    private Card.Rank getRandomWildRank() {
        Card.Rank[] wildRanks = {
            Card.Rank.WILD_RAISE_24,
            Card.Rank.WILD_RESET_HAND,
            Card.Rank.WILD_ADD_DEALER
        };
        Random random = new Random();
        return wildRanks[random.nextInt(wildRanks.length)];
    }

    private boolean isWildRank(Card.Rank rank) {
        return rank == Card.Rank.WILD_RAISE_24 ||
               rank == Card.Rank.WILD_RESET_HAND ||
               rank == Card.Rank.WILD_ADD_DEALER;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) throw new IllegalStateException("Deck is empty.");
        return cards.removeFirst();
    }
}
