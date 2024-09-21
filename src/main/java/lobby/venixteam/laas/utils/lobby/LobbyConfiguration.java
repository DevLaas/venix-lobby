package lobby.venixteam.laas.utils.lobby;

import lobby.venixteam.laas.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class LobbyConfiguration {

    private File file;
    private FileConfiguration lobbyConfig;

    public void lobbyConfiguration() {
        file = new File(Main.getInstance().getDataFolder(), "location.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            Main.getInstance().saveResource("location.yml", false);
            lobbyConfig = YamlConfiguration.loadConfiguration(file);
            Main.getInstance().getLogger().log(Level.WARNING, "Arquivo location.yml criado com sucesso.");
        } else {
            lobbyConfig = YamlConfiguration.loadConfiguration(file);
        }
    }

    public void saveLobbyConfiguration() {
        try {
            lobbyConfig.save(file);
        } catch (IOException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Não foi possível salvar location.yml.", e);
        }
    }

    public void reloadLobbyConfiguration() {
        lobbyConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getLobbyConfiguration() {
        return lobbyConfig;
    }
}
