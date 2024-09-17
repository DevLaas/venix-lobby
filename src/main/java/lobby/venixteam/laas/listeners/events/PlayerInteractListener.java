package lobby.venixteam.laas.listeners.events;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.menu.ModosMenu;
import lobby.venixteam.laas.menu.ProfileMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInHand();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (itemStack.getType() == Material.SKULL_ITEM &&
                itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("configuration.hotbar.profile-item-name").replace("&", "§"))) {
            evt.setCancelled(true);
            new ProfileMenu(player);
            player.playSound(player.getLocation(), Sound.CLICK, 0.5f, 1.0f);
        }

        if (itemStack.getType() == Material.CHEST &&
                itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("configuration.hotbar.modos-item-name").replace("&", "§"))) {
            evt.setCancelled(true);
            new ModosMenu(player);
            player.playSound(player.getLocation(), Sound.CLICK, 0.5f, 1.0f);
        }
    }
}
