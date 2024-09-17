package lobby.venixteam.laas.scoreboard;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.scoreboard.scroller.ScoreboardScroller;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ScoreboardManager {

    public static final Map<Player, ScoreboardCreator> playerScoreboardMap = new HashMap<>();
    private static LuckPerms luckPerms;
    private static String group;

    public static void setupScoreboardHook() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    ScoreboardCreator scoreboard = getScoreboardForPlayer(player);
                    if (scoreboard != null) {
                        scoreboard.scroll();
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);

        new BukkitRunnable() {

            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    ScoreboardCreator scoreboard = getScoreboardForPlayer(player);
                    if (scoreboard != null) {
                        scoreboard.update();
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
    }

    public static void refrashScoreboard(Player player) {
        List<String> lines = new ArrayList<>(Main.getInstance().getConfig().getStringList("configuration.scoreboard.lines"));
        Collections.reverse(lines);

        ScoreboardCreator scoreboard = new ScoreboardCreator() {
            @Override
            public void update() {
                this.updateHealth();
                for (int index = 0; index < lines.size(); index++) {
                    String line = lines.get(index);
                    line = PlaceholderAPI.setPlaceholders(player, line);

                    luckPerms = Main.getInstance().getLuckPerms();
                    User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                    group = user.getCachedData().getMetaData().getPrefix();

                    if (group == null || group.isEmpty()) {
                        group = "§7" + user.getPrimaryGroup().toUpperCase();
                    } else {
                        group = user.getCachedData().getMetaData().getPrefix();
                    }

                    line = line.replace("%player%", player.getName());
                    line = line.replace("%group%", group);

                    this.add(index + 1, line);
                }
            }
        }.scroller(new ScoreboardScroller(Main.getInstance().getConfig().getStringList("configuration.scoreboard.title"))).to(player).build();
        ScoreboardManager.scoreboardAddPlayer(player, scoreboard);
        scoreboard.scroll();
    }

    public static void scoreboardAddPlayer(Player player, ScoreboardCreator scoreboardCreator) {
        playerScoreboardMap.put(player, scoreboardCreator);
    }

    public static void scoreboardRemovePlayer(Player player) {
        playerScoreboardMap.remove(player);
    }

    private static ScoreboardCreator getScoreboardForPlayer(Player player) {
        return playerScoreboardMap.get(player);
    }
}
