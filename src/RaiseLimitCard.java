public class RaiseLimitCard implements WildCard{


    @Override
    public String getDescription() {
        return "Zvýší limit současné hry z 21 na 24.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        BlackjackGame.currentBustLimit = 24;
    }
}
