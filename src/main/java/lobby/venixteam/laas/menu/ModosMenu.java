package lobby.venixteam.laas.menu;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.utils.items.ItemBuilder;
import lobby.venixteam.laas.utils.lobby.ServersConfiguration;
import lobby.venixteam.laas.utils.menu.PlayerMenu;
import lobby.venixteam.laas.utils.BungeeUtil;
import lobby.venixteam.laas.utils.titles.TitlesAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class ModosMenu extends PlayerMenu {

    private ServersConfiguration config;

    public ModosMenu(Player player) {
        super(player, "Menu de Modos", 3);
        config = Main.getInstance().getServersConfig();

        for (String key : config.getServersConfig().getConfigurationSection("servers").getKeys(false)) {
            FileConfiguration serversConfig = config.getServersConfig();
            Map<String, Object> serverConfig = serversConfig.getConfigurationSection("servers." + key).getValues(false);
            Object iconObject = serverConfig.get("icon");
            String name = ChatColor.translateAlternateColorCodes('&', (String) serverConfig.get("name"));
            String desc = ChatColor.translateAlternateColorCodes('&', (String) serverConfig.get("desc"));
            int slot = (int) serverConfig.get("slot");


            Material material = getMaterialFromObject(iconObject);
            if (material == null) {
                player.sendMessage("§cÍcone inválido para o servidor " + key + ".");
                continue;
            }

            ItemStack serverItem = new ItemBuilder(material, 1)
                    .setName(name)
                    .setLore(desc.split("\n"))
                    .build();

            this.setItem(slot, serverItem);
        }

        ItemStack closeInv = new ItemBuilder(Material.ARROW, 1)
                .setName("§cFechar Menu")
                .build();

        this.setItem(26, closeInv);
        this.open();
        this.register(Main.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                ItemStack item = evt.getCurrentItem();

                if (item != null && item.getType() != Material.AIR) {
                    int slot = evt.getSlot();
                    if (slot == 26) {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.5f, 0.2f);
                    } else {
                        for (String key : config.getServersConfig().getConfigurationSection("servers").getKeys(false)) {
                            Map<String, Object> serverConfig = config.getServersConfig().getConfigurationSection("servers." + key).getValues(false);
                            int serverSlot = (int) serverConfig.get("slot");
                            if (serverSlot == slot) {
                                String serverName = (String) serverConfig.get("name");
                                player.sendMessage(Main.getInstance().getConfig().getString("configuration.server.send-server").replace("%server%", key).replace("&", "§"));
                                BungeeUtil.connectToServer(player, key);
                                TitlesAPI.sendActionBar(player, Main.getInstance().getConfig().getString("configuration.server.send-actionbar-server").replace("%server%", key).replace("&", "§"));
                                break;
                            }
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

    /**
     * Obtém o Material a partir de um objeto que pode ser uma string ou um número.
     * @param value Objeto que pode ser o nome do material ou um ID numérico.
     * @return O Material correspondente, ou null se não for encontrado.
     */
    private Material getMaterialFromObject(Object value) {
        if (value instanceof String) {
            Material material = Material.getMaterial((String) value);
            if (material != null) {
                return material;
            }
        } else if (value instanceof Integer) {
            Material material = Material.getMaterial((Integer) value);
            if (material != null) {
                return material;
            }
        }
        return null;
    }
}
