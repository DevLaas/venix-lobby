package lobby.venixteam.laas.listeners.events;

import lobby.venixteam.laas.cmd.lobby.BuilderModeCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DefaultListeners implements Listener {

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent evt) {
        evt.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent evt) {
        evt.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent evt) {
        evt.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent evt) {
        evt.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent evt) {
        evt.setCancelled(evt.toWeatherState());
    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent evt) {
        Player player = evt.getPlayer();

        if (!BuilderModeCommand.BUILDERMODE.contains(player.getName())) {
            evt.setCancelled(true);
        } else {
            evt.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent evt) {
        Player player = evt.getPlayer();

        if (!BuilderModeCommand.BUILDERMODE.contains(player.getName())) {
            evt.setCancelled(true);
        } else {
            evt.setCancelled(false);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent evt) {
        Player player = evt.getPlayer();

        if (!BuilderModeCommand.BUILDERMODE.contains(player.getName())) {
            evt.setCancelled(true);
        } else {
            evt.setCancelled(false);
        }
    }

    @EventHandler
    public void onPickupItems(PlayerPickupItemEvent evt) {
        Player player = evt.getPlayer();

        if (!BuilderModeCommand.BUILDERMODE.contains(player.getName())) {
            evt.setCancelled(true);
        } else {
            evt.setCancelled(false);
        }
    }
}
