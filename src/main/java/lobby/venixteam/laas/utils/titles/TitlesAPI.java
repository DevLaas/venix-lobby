package lobby.venixteam.laas.utils.titles;

import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TitlesAPI {


    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {

        title = ChatColor.translateAlternateColorCodes('&', title);
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

        Class<?> chatSerializer = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
        Class<?> chatComponent = getNMSClass("IChatBaseComponent");
        Class<?> packetTitle = getNMSClass("PacketPlayOutTitle");

        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Constructor<?> constructorTitle = packetTitle.getDeclaredConstructor(packetTitle.getDeclaredClasses()[0], chatComponent, int.class, int.class, int.class);
            Object chatTitle = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + title + "\"}");
            Object packet = constructorTitle.newInstance(enumTitle, chatTitle, fadein, stay, fadeout);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Constructor<?> constructorSubtitle = packetTitle.getDeclaredConstructor(packetTitle.getDeclaredClasses()[0], chatComponent, int.class, int.class, int.class);
            Object chatSubtitle = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + subtitle + "\"}");
            Object packet = constructorSubtitle.newInstance(enumSubtitle, chatSubtitle, fadein, stay, fadeout);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendActionBar(Player player, String message) {

        message = PlaceholderAPI.setPlaceholders(player, message);

        message = ChatColor.translateAlternateColorCodes('&', message);
        Class<?> chatSerializer = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
        Class<?> chatComponent = getNMSClass("IChatBaseComponent");
        Class<?> packetActionbar = getNMSClass("PacketPlayOutChat");

        try {
            Constructor<?> ConstructorActionbar = packetActionbar.getDeclaredConstructor(chatComponent, byte.class);
            Object actionbar = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + message + "\"}");
            Object packet = ConstructorActionbar.newInstance(actionbar, (byte) 2);
            sendPacket(player, packet);
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public static void sendTablist(Player player, String header, String footer) {
        if (header == null) header = "";
        if (footer == null) footer = "";

        header = PlaceholderAPI.setPlaceholders(player, header);
        footer = PlaceholderAPI.setPlaceholders(player, footer);

        try {
            Class<?> chatSerializer = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
            Object tabHeader = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\":\"" + header + "\"}");
            Object tabFooter = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\":\"" + footer + "\"}");

            Class<?> packetClass = getNMSClass("PacketPlayOutPlayerListHeaderFooter");
            Object packet = packetClass.getConstructor().newInstance();

            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, tabHeader);

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, tabFooter);

            sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
