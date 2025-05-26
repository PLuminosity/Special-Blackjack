public class ResetHandCard implements WildCard{

    @Override
    public String getDescription() {
        return "Obnovení ruky hráče.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        player.resetHand();
    }
}
