package lobby.venixteam.laas.utils;

import org.bukkit.*;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

/**
 * Classe com utilitários relacionados ao {@link org.bukkit}.
 **/
public class BukkitUtils {

    /**
     * Transforma uma {@link Location} em uma {@code String} utilizando o seguinte formato:<br/>
     * {@code "mundo;x;y;z;yaw;pitch"}
     *
     * @param location A {@link Location} para transformar em {@code String}.
     * @return A {@link Location} transformada em uma {@code String}.
     */
    public static String serializeLocation(Location location) {
        if (location == null) {
            return "";
        }
        return location.getWorld().getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ() + ";" +
                location.getYaw() + ";" +
                location.getPitch();
    }

    /**
     * Transforma uma {@code String} em uma {@link Location} utilizando o seguinte formato:<br/>
     * {@code "mundo;x;y;z;yaw;pitch"}
     *
     * @param serialized A {@code String} para transformar em {@link Location}.
     * @return A {@code String} transformada em uma {@link Location}.
     */
    public static Location deserializeLocation(String serialized) {
        if (serialized == null || serialized.isEmpty()) {
            return null;
        }

        String[] divPoints = serialized.split(";");
        if (divPoints.length != 6) {
            throw new IllegalArgumentException("Invalid location format");
        }

        World world = Bukkit.getWorld(divPoints[0].trim());
        if (world == null) {
            throw new IllegalArgumentException("World not found: " + divPoints[0].trim());
        }

        try {
            double x = parseDouble(divPoints[1].trim());
            double y = parseDouble(divPoints[2].trim());
            double z = parseDouble(divPoints[3].trim());
            float yaw = parseFloat(divPoints[4].trim());
            float pitch = parseFloat(divPoints[5].trim());

            Location location = new Location(world, x, y, z);
            location.setYaw(yaw);
            location.setPitch(pitch);
            return location;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing location values", e);
        }
    }
}
