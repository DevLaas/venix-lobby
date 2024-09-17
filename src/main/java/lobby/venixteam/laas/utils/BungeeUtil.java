package lobby.venixteam.laas.utils;

import lobby.venixteam.laas.Main;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtil {

    public static void connectToServer(Player player, String serverName) {
        if (player == null || !player.isOnline()) {
            player.sendMessage(Main.getInstance().getConfig().getString("configuration.server.server-connection-fail").replace("&", "§"));
            return;
        }

        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF("Connect");
            out.writeUTF(serverName);

            player.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());

        } catch (IOException e) {
            player.sendMessage("§cHouve um erro ao tentar conectar ao servidor.");
            e.printStackTrace();
        }
    }
}
