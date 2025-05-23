public class RaiseLimitCard implements WildCard{


    @Override
    public String getDescription() {
        return "Raise bust limit to 24 for current hand";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        BlackjackGame.currentBustLimit = 24;
    }
}
