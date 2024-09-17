package lobby.venixteam.laas.listeners.events;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.hotbar.PlayerHotbar;
import lobby.venixteam.laas.scoreboard.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private LuckPerms luckPerms;
    private String group;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        evt.setJoinMessage(null);

        PlayerHotbar.setLobbyHotbar(player);
        ScoreboardManager.refrashScoreboard(player);

        luckPerms = Main.getInstance().getLuckPerms();
        String joinMessage = Main.getInstance().getConfig().getString("configuration.join.join-message");

        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (Main.getInstance().getConfig().getBoolean("configuration.join.join-message-active") && player.hasPermission(Main.getInstance().getConfig().getString("configuration.join.join-permission"))) {
                group = user.getCachedData().getMetaData().getPrefix();
                if (group == null || group.isEmpty()) {
                    joinMessage = joinMessage.replace("%player%", user.getPrimaryGroup().toUpperCase() + " " + player.getName()).replace("&", "§");
                } else {
                    joinMessage = joinMessage.replace("%player%", group + player.getName()).replace("&", "§");
                }

                evt.setJoinMessage(joinMessage);
            }
        }
    }
}
