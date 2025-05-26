public class RaiseLimitCard implements WildCard{


    @Override
    public String getDescription() {
        return "Zvýší limit z 21 na 24 pro současnou hru.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        BlackjackGame.currentBustLimit = 24;
    }
}
