package lobby.venixteam.laas.utils.items;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

@SuppressWarnings("all")
public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, int quantitie) {
        this.itemStack = new ItemStack(material, quantitie);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(OfflinePlayer player) {
        this.itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(player.getName());
        this.itemMeta = skullMeta;
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setQuantitie(int quantitie) {
        this.itemStack.setAmount(quantitie);
        return this;
    }

    public ItemBuilder setMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable, boolean hideflag) {
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        if (hideflag == true) {
            this.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        } else {
            this.itemMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
