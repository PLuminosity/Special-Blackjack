import java.util.Random;

public class AddToDealerCardI implements IWildCard {
    private int valueToAdd;

    @Override
    public String getDescription() {
        return "Přidá dealerovy náhodnou hodnotu od 2 do 5.";
    }

    @Override
    public void activate(Player player, Dealer dealer) {
        //generate random value between 2 and 5
        Random rand = new Random();
        valueToAdd = rand.nextInt(2, 6);
    }

    public int getValueToAdd() {
        return valueToAdd;
    }
}
