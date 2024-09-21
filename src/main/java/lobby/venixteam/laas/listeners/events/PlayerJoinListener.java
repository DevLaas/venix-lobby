package lobby.venixteam.laas.listeners.events;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.hotbar.PlayerHotbar;
import lobby.venixteam.laas.scoreboard.ScoreboardManager;
import lobby.venixteam.laas.utils.lobby.LobbySetter;
import lobby.venixteam.laas.utils.titles.TitlesAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private LobbySetter lobbyConfig = new LobbySetter();
    private LuckPerms luckPerms;
    private String group;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        evt.setJoinMessage(null);

        player.getInventory().clear();
        PlayerHotbar.setLobbyHotbar(player);
        ScoreboardManager.refrashScoreboard(player);
        String header = Main.getInstance().getConfig().getString("configuration.tablist.tab-header").replace("&", "§").replace("%player%", player.getName());
        String footer = Main.getInstance().getConfig().getString("configuration.tablist.tab-footer").replace("&", "§");

        if (Main.getInstance().getConfig().getBoolean("configuration.tablist.tab-join-active")) TitlesAPI.sendTablist(player, header, footer);
        if (Main.getInstance().getConfig().getBoolean("configuration.join.join-title-active")) TitlesAPI.sendTitle(player, Main.getInstance().getConfig().getString("configuration.join.join-title").replace("&", "§"), Main.getInstance().getConfig().getString("configuration.join.join-subtitle").replace("&", "§"), 0, 1, 0);

        Location spawnLocation = lobbyConfig.getLobbyLocation();
        if (spawnLocation != null) {
            player.teleport(spawnLocation);
            player.sendMessage("§aVocê foi teleportado para o spawn do mundo!!");
        } else if (player.hasPermission("venixlobby.setlobby")) {
            player.sendMessage("\n §eA localização do lobby não foi definida, portanto, você foi teleportado para o spawn do mundo!. Utilize /setlobby para definir uma localização.\n \n");
        }

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
