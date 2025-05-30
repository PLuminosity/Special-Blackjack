public class ResetHandCardI implements IWildCard {

    @Override
    public String getDescription() {
        return "Obnovení ruky hráče.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        player.resetHand();
    }
}
