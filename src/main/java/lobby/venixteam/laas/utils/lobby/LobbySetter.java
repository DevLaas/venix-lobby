package lobby.venixteam.laas.utils.lobby;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.utils.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LobbySetter {

    private static String LOBBYLOCATION = "lobby";

    public void setLobbyLocation(Location location) {
        FileConfiguration warpSpawnConfig = Main.getInstance().getLobbyConfig().getLobbyConfiguration();
        String serializedLocation = BukkitUtils.serializeLocation(location);

        warpSpawnConfig.set(LOBBYLOCATION, serializedLocation);
        Main.getInstance().getLobbyConfig().saveLobbyConfiguration();
    }

    public Location getLobbyLocation() {
        FileConfiguration warpSpawnConfig = Main.getInstance().getLobbyConfig().getLobbyConfiguration();
        if (warpSpawnConfig.contains(LOBBYLOCATION)) {
            String serializedLocation = warpSpawnConfig.getString(LOBBYLOCATION);
            return BukkitUtils.deserializeLocation(serializedLocation);
        }
        World world = Main.getInstance().getServer().getWorlds().get(0);
        return world.getSpawnLocation();
    }
}
