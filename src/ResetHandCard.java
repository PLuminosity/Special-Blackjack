public class ResetHandCard implements WildCard{

    @Override
    public String getDescription() {
        return "Obnoví ruku hráče.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        player.resetHand();
    }
}
