package lobby.venixteam.laas.cmd.lobby;

import lobby.venixteam.laas.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuilderModeCommand implements CommandExecutor {

    public static List<String> BUILDERMODE = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente jogadores podem executar esse comando.");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("venixlobby.buildermode")) {
            player.sendMessage(Main.getInstance().getConfig().getString("configuration.lobby.command-permission").replace("&", "§"));
            return false;
        }

        if (args.length == 0) {
            if (!BUILDERMODE.contains(player.getName())) {
                player.sendMessage(Main.getInstance().getConfig().getString("configuration.lobby.lobby-buildermode-on").replace("&", "§"));
                BUILDERMODE.add(player.getName());
            } else {
                player.sendMessage(Main.getInstance().getConfig().getString("configuration.lobby.lobby-buildermode-off").replace("&", "§"));
                BUILDERMODE.remove(player.getName());
            }
        }
        return false;
    }
}
