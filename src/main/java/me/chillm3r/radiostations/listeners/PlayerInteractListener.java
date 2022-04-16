package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.subcommands.RadioCreateCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

    Plugin plugin = Main.getPlugin(Main.class);

    public static Boolean selectRadioActive = false;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        String action = e.getAction().name();
        Block selectedBlock = e.getClickedBlock();

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

        if (action == "RIGHT_CLICK_BLOCK" && selectedBlock.getType().name() == "JUKEBOX") {
            Main.loadData();

            if (Main.config.get(player.getDisplayName()) != null) {
                player.performCommand("radio menu");
            }
        }
    }
}
