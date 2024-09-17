package lobby.venixteam.laas.hotbar;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHotbar {

    public static void setLobbyHotbar(Player player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getName());

        ItemStack profile = new ItemBuilder(player)
                .setName(Main.getInstance().getConfig().getString("configuration.hotbar.profile-item-name").replace("&", "§"))
                .setQuantitie(1)
                .build();

        ItemStack modos = new ItemBuilder(Material.CHEST, 1)
                .setName(Main.getInstance().getConfig().getString("configuration.hotbar.modos-item-name").replace("&", "§"))
                .build();


        player.getInventory().setItem(Main.getInstance().getConfig().getInt("configuration.hotbar.profile-slot"), profile);
        player.getInventory().setItem(Main.getInstance().getConfig().getInt("configuration.hotbar.modos-slot"), modos);
    }
}
