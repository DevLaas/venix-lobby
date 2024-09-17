package lobby.venixteam.laas.utils;

import lobby.venixteam.laas.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LobbyLocation {

    private static Location lobby;
    private static FileConfiguration locationConfig;
    private static File locationFile;

    public static void setLobby(Location location) {
        lobby = location;
        saveLobbyLocation();
    }

    public static Location getLobby() {
        return lobby;
    }

    public static void initialize() {
        FileConfiguration config = getLocationConfig();
        if (config == null) {
            Bukkit.getLogger().warning("A configuração do location.yml não está carregada.");
            return;
        }

        String serializedLocation = config.getString("lobby");
        if (serializedLocation != null && !serializedLocation.isEmpty()) {
            try {
                lobby = BukkitUtils.deserializeLocation(serializedLocation);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("Erro ao desserializar a localização do lobby: " + e.getMessage());
                loadDefaultLobbyLocation();
                saveLobbyLocation();
            }
        } else {
            loadDefaultLobbyLocation();
            saveLobbyLocation();
        }
    }

    private static void saveLobbyLocation() {
        FileConfiguration config = getLocationConfig();
        if (lobby != null) {
            config.set("lobby", BukkitUtils.serializeLocation(lobby));
            saveLocationConfig();
        }
    }

    public static void createLocationFile() {
        locationFile = new File(Main.getInstance().getDataFolder(), "location.yml"); // Corrigir variável
        if (!locationFile.exists()) {
            Main.getInstance().saveResource("location.yml", false);
        }
    }

    public static void loadLocationConfig() {
        locationFile = new File(Main.getInstance().getDataFolder(), "location.yml"); // Corrigir variável
        if (locationFile.exists()) {
            locationConfig = YamlConfiguration.loadConfiguration(locationFile);
            Main.getInstance().getLogger().info("location.yml carregado com sucesso.");
        } else {
            Main.getInstance().getLogger().warning("O arquivo location.yml não foi encontrado. Criando um novo.");
            Main.getInstance().saveResource("location.yml", false);
            locationConfig = YamlConfiguration.loadConfiguration(locationFile);
        }
    }

    public static FileConfiguration getLocationConfig() {
        return locationConfig;
    }

    public static void saveLocationConfig() {
        if (locationConfig == null || locationFile == null) {
            return;
        }
        try {
            locationConfig.save(locationFile);
        } catch (Exception e) {
            Main.getInstance().getLogger().warning("Não foi possível salvar o arquivo location.yml: " + e.getMessage());
        }
    }

    private static void loadDefaultLobbyLocation() {
        lobby = Bukkit.getWorlds().get(0).getSpawnLocation();
    }
}
