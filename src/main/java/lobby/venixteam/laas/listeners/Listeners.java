package lobby.venixteam.laas.listeners;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.listeners.events.*;
import org.bukkit.Bukkit;

public class Listeners {
    
    public static Listeners instanceListener;

    public static Listeners getInstanceListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new DefaultListeners(), Main.getInstance());
        return instanceListener;
    }
}
