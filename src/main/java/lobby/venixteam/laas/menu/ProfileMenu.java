package lobby.venixteam.laas.menu;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.utils.items.ItemBuilder;
import lobby.venixteam.laas.utils.menu.PlayerMenu;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileMenu extends PlayerMenu {

    private final LuckPerms luckPerms;
    private String cargo;

    public ProfileMenu(Player player) {
        super(player, "Seu perfil", 3);

        this.luckPerms = Main.getInstance().getLuckPerms();

        long lastPlayed = player.getLastPlayed();
        String lastLogin = (lastPlayed == 0) ? "Nunca!" : new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(lastPlayed));

        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            cargo = user.getCachedData().getMetaData().getPrefix();

            if (cargo == null || cargo.isEmpty()) {
                cargo = "§7" + user.getPrimaryGroup().toUpperCase();
            } else {
                cargo = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
            }
          } else {
            cargo = Main.getInstance().getConfig().getString("configuration.profile.error-luckperms-load").replace("&", "§");
        }

        ItemStack profile = new ItemBuilder(player)
                .setName("§eInformações do seu perfil:")
                .setLore(
                        "§fNick: §7" + player.getName(),
                        "§fGrupo: " + cargo,
                        "",
                        "§fUltimo lógin: §7" + lastLogin
                        )
                .setQuantitie(1)
                .build();

        ItemStack closeInv = new ItemBuilder(Material.ARROW, 1)
                .setName("§cFechar perfil")
                .build();

        this.setItem(13, profile);
        this.setItem(26, closeInv);

        this.open();
        this.register(Main.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == 26) {
                            player.closeInventory();
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.5f, 0.2f);
                        } else if (evt.getSlot() == 13) {
                            player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 0.5f, 0.2f);
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
            this.cancel();
        }
    }
}
