import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String PLAYER_FILE = "players.txt";
    private final Map<String, Player> players = new HashMap<>();

    public UserManager() {
        loadPlayers();
    }

    public void loadPlayers() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Player player = Player.deserialize(line);
                players.put(player.getName(), player);
            }
        } catch (IOException e) {
            System.out.println("Error při načítnání hráčů: " + e.getMessage());
        }
    }

    public void savePlayers() {
        try (FileWriter fw = new FileWriter(PLAYER_FILE)) {
            for (Player player : players.values()) {
                fw.write(player.serialize() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error při ukládání hráčů: " + e.getMessage());
        }
    }

    public void updatePlayer(Player player) {
            players.put(player.getName(), player);
            savePlayers();
    }

    public boolean registerPlayer(String name, String password) {
        if (players.containsKey(name)) {
            System.out.println("Hráč už existuje.");
            return false;
        }
        Player newPlayer = new Player(name, password, 1000);
        players.put(name, newPlayer);
        savePlayers();
        return true;
    }

    public Player loginPlayer(String name, String password) {
        Player player = players.get(name);
        if (player != null && player.getPassword().equals(password)) {
            return player;
        }
        System.out.println("Neplatné uživatelské jméno nebo heslo.");
        return null;
    }
}
