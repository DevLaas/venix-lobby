package lobby.venixteam.laas;

import lobby.venixteam.laas.utils.LobbyLocation;
import lobby.venixteam.laas.cmd.Commands;
import lobby.venixteam.laas.listeners.Listeners;
import lobby.venixteam.laas.scoreboard.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    private LuckPerms luckPerms;
    private FileConfiguration serversConfig;
    private File serversFile;

    @Override
    public void onLoad() {
        instance = this;
        // Load and reload config.yml
        saveDefaultConfig();
        reloadConfig();
        createServersFile();
        loadServersConfig();
        initializeLocationFile();

        Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §f...");
    }

    @Override
    public void onEnable() {

        luckPerms = LuckPermsProvider.get();
        ScoreboardManager.setupScoreboardHook();
        LobbyLocation.initialize();
        Listeners.getInstanceListener();
        Commands.getInstanceCommands();

        if (luckPerms == null) {
            Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §cA dependência LuckPerms, não foi encontrada!");
        } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null
                || !Bukkit.getPluginManager().getPlugin("PlaceholderAPI").getDescription().getVersion().equals("2.10.5")) {
            Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §cA dependência PlaceholderAPI - v2.10.5, não foi encontrada!");
        } else {
            Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §fDepedências carregadas com sucesso: §eLuckPerms, PlaceholderAPI§f.");
        }

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §fRecursos carregados com sucesso.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §cEncerrando recursos.");
    }

    public static Main getInstance() {
        return instance;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    private void createServersFile() {
        serversFile = new File(getDataFolder(), "servers.yml");
        if (!serversFile.exists()) {
            saveResource("servers.yml", false);
        }
    }

    private void loadServersConfig() {
        File serversFile = new File(getDataFolder(), "servers.yml");
        if (serversFile.exists()) {
            serversConfig = YamlConfiguration.loadConfiguration(serversFile);
            getLogger().info("servers.yml carregado com sucesso.");
        } else {
            getLogger().warning("O arquivo servers.yml não foi encontrado. Criando um novo.");
            saveResource("servers.yml", false);
            serversConfig = YamlConfiguration.loadConfiguration(serversFile);
        }
    }

    private void initializeLocationFile() {
        File locationFile = new File(getDataFolder(), "location.yml");
        if (!locationFile.exists()) {
            saveResource("location.yml", false);
        }
        LobbyLocation.createLocationFile();
        LobbyLocation.loadLocationConfig();
    }

    public FileConfiguration getServersConfig() {
        return serversConfig;
    }
}