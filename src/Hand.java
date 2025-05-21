import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getValue() {
        int sum = 0;
        int aceCount = 0;
        for (Card c : cards) {
            sum += c.getValue();
            if (c.getValue() == 11) aceCount++;
        }
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void clear() {
        cards.clear();
    }

    public String toString() {
        return cards + " (value: " + getValue() + ")";
    }
}
