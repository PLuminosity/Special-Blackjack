public class ResetHandCardI implements IWildCard {

    @Override
    public String getDescription() {
        return "Obnovení ruky hráče.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        Card firstCard = player.getHand().getCards().getFirst();
        Card secondCard = player.getHand().getCards().get(1);
        player.resetHand();
        player.getHand().addCard(firstCard);
        player.getHand().addCard(secondCard);
    }
}
