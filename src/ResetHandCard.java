public class ResetHandCard implements WildCard{

    @Override
    public String getDescription() {
        return "Resets the player's hand";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        player.resetHand();
    }
}
