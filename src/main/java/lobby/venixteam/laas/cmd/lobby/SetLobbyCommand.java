package lobby.venixteam.laas.cmd.lobby;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.utils.LobbyLocation;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente jogadores podem executar esse comando.");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("venixlobby.setlobby")) {
            player.sendMessage(Main.getInstance().getConfig().getString("configuration.lobby.command-permission").replace("&", "§"));
            return false;
        }

        if (args.length == 0) {
            Location location = player.getLocation();
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            LobbyLocation.setLobby(location);
            player.sendMessage(Main.getInstance().getConfig().getString("configuration.lobby.setlobby-success").replace("&", "§"));
            return true;
        }

        return false;
    }
}
