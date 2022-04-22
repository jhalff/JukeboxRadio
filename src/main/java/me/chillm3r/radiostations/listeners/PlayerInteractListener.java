package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.subcommands.RadioCreateCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

    Plugin plugin = Main.getPlugin(Main.class);

    public static Boolean selectRadioActive = false;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Main.loadData();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        String action = e.getAction().name();
        Block selectedBlock = e.getClickedBlock();

        // Selecting Jukebox
        if (selectRadioActive) {
            if (action == "RIGHT_CLICK_BLOCK") {
                if (selectedBlock.getType().name() == "JUKEBOX") {
                    RadioCreateCommand.generateRadio(player, selectedBlock);
                    selectRadioActive = false;
                } else {
                    player.sendMessage(ChatColor.RED + "Please select a jukebox");
                }
            }
        }

        // Opening Radio Menu
        if (action == "RIGHT_CLICK_BLOCK" && selectedBlock.getType().name() == "JUKEBOX") {
            Main.loadData();

            Object dataObject = Main.config.get(player.getUniqueId().toString());

            if (dataObject != null) {
                String dataX = (String) Main.config.get(player.getUniqueId() + ".radio-location.x");
                String dataY = (String) Main.config.get(player.getUniqueId() + ".radio-location.y");
                String dataZ = (String) Main.config.get(player.getUniqueId() + ".radio-location.z");

                String targetX = String.valueOf(selectedBlock.getLocation().getX());
                String targetY = String.valueOf(selectedBlock.getLocation().getY());
                String targetZ = String.valueOf(selectedBlock.getLocation().getZ());

                if (dataX.equalsIgnoreCase(targetX) && dataY.equalsIgnoreCase(targetY) && dataZ.equalsIgnoreCase(targetZ)) {
                    player.performCommand("radio menu");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Main.loadData();

        try {
            if (Main.config.getString(e.getPlayer().getUniqueId().toString()) != null) {
                Main.config.set(e.getPlayer().getUniqueId() + ".radio-power", "off");
                Main.config.save(Main.dataFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
