package lobby.venixteam.laas;

import lobby.venixteam.laas.utils.lobby.LobbyConfiguration;
import lobby.venixteam.laas.cmd.Commands;
import lobby.venixteam.laas.listeners.Listeners;
import lobby.venixteam.laas.scoreboard.ScoreboardManager;
import lobby.venixteam.laas.utils.lobby.ServersConfiguration;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private LuckPerms luckPerms;
    private ServersConfiguration serversConfig;
    private LobbyConfiguration lobbyConfig;

    @Override
    public void onLoad() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §f...");
    }

    @Override
    public void onEnable() {

        luckPerms = LuckPermsProvider.get();
        lobbyConfig = new LobbyConfiguration();
        lobbyConfig.lobbyConfiguration();
        serversConfig = new ServersConfiguration();
        serversConfig.serversConfiguration();

        ScoreboardManager.setupScoreboardHook();
        Listeners.getInstanceListener();
        Commands.getInstanceCommands();

        if (luckPerms == null) {
            Bukkit.getConsoleSender().sendMessage("§6[venix-lobby] §cA dependência LuckPerms, não foi encontrada!");
        } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
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

    public LobbyConfiguration getLobbyConfig() {
        return lobbyConfig;
    }

    public ServersConfiguration getServersConfig() {
        return serversConfig;
    }
}