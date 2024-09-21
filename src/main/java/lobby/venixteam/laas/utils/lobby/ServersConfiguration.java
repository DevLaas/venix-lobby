package lobby.venixteam.laas.utils.lobby;

import lobby.venixteam.laas.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class ServersConfiguration {

    private File serversFile;
    private FileConfiguration serversConfig;

    public void serversConfiguration() {
        serversFile = new File(Main.getInstance().getDataFolder(), "servers.yml");

        if (!serversFile.exists()) {
            serversFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("servers.yml", false);
            serversConfig = YamlConfiguration.loadConfiguration(serversFile);
            Main.getInstance().getLogger().log(Level.WARNING, "Arquivo servers.yml criado com sucesso.");
        } else {
            serversConfig = YamlConfiguration.loadConfiguration(serversFile);
        }
    }

    public void reloadServersConfiguration() {
        serversConfig = YamlConfiguration.loadConfiguration(serversFile);
    }

    public FileConfiguration getServersConfig() {
        return serversConfig;
    }
}
