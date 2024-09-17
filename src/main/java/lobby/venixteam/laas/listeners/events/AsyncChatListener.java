package lobby.venixteam.laas.listeners.events;

import lobby.venixteam.laas.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

    private LuckPerms luckPerms;
    private String group;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent evt) {
        Player player = evt.getPlayer();
        String message = evt.getMessage();

        luckPerms = Main.getInstance().getLuckPerms();

        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            group = user.getCachedData().getMetaData().getPrefix();

            if (group == null || group.isEmpty()) {
                group = "§7" + user.getPrimaryGroup().toUpperCase();
            } else {
               group = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
            }

            String chatFormat = Main.getInstance().getConfig().getString("configuration.chat.chat-format")
                    .replace("%group%", group)
                    .replace("%player%", player.getDisplayName())
                    .replace("%message%", "%2$s")
                    .replace("&", "§");
            evt.setFormat(chatFormat);
        }

        if (Main.getInstance().getConfig().getBoolean("configuration.chat.chat-player-notify")) {
            String soundName = Main.getInstance().getConfig().getString("configuration.chat.chat-notify-sound", "ITEM_PICKUP");
            float volume = (float) Main.getInstance().getConfig().getDouble("configuration.chat.notify-sound-altura", 0.5);
            float pitch = (float) Main.getInstance().getConfig().getDouble("configuration.chat.notify-sound-pitch", 1.0);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (message.contains(onlinePlayer.getName())) {
                    try {
                        Sound notifySound = Sound.valueOf(soundName.toUpperCase());
                        onlinePlayer.playSound(onlinePlayer.getLocation(), notifySound, volume, pitch);
                    } catch (IllegalArgumentException e) {
                        System.out.println("§cSom configurado '" + soundName.toUpperCase() + "' não é válido.");
                    }
                }
            }
        }
    }
}
