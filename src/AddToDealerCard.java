import java.util.Random;

public class AddToDealerCard implements WildCard {
    private int valueToAdd;

    @Override
    public String getDescription() {
        return "Adds a random value from 2 to 5 to the dealer's hand";
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
