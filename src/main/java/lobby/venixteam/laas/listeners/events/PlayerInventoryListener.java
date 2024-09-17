package lobby.venixteam.laas.listeners.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onPlayerInventoryInteract(InventoryClickEvent evt) {
        if (evt.getWhoClicked() instanceof Player) {

            Player player = (Player) evt.getWhoClicked();
            Inventory clickedInventory = evt.getClickedInventory();

            if (clickedInventory instanceof PlayerInventory) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    evt.setCancelled(false);
                } else {
                    evt.setCancelled(true);
                }
            }
        }
    }
}
