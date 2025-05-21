public abstract class Participant {
    protected Hand hand = new Hand();
    protected String name;

    public Participant(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void resetHand() {
        hand.clear();
    }
}
