package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.PlayerInteractListener;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

public class RadioCreateCommand extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a radio station";
    }

    @Override
    public String getSyntax() {
        return "/radio create";
    }

    @Override
    public void perform(Player player, String[] args) {
        Main.loadData();

        if (Main.config.get(player.getDisplayName()) == null) {
            PlayerInteractListener.selectRadioActive = true;

            Main.chatMessageHeader(player);
            player.sendMessage(ChatColor.GRAY + "Please" + ChatColor.WHITE + " RIGHT-CLICK" + ChatColor.GRAY + " on a" + ChatColor.BOLD + " jukebox");
            player.sendMessage("");
        } else {
            Main.chatMessageHeader(player);
            player.sendMessage(ChatColor.RED + "Whoops.." + ChatColor.GRAY + " You can only have 1 radio station.");
            player.sendMessage("");
        }

        TimerTask stopRadioSelection = new TimerTask() {
            public void run() {
                if (PlayerInteractListener.selectRadioActive) {
                    player.sendMessage(ChatColor.RED + "No jukebox selected in time" + ChatColor.GRAY + " Try again by executing:");
                    player.sendMessage(ChatColor.WHITE + "/radio create");
                    player.sendMessage("");
                    PlayerInteractListener.selectRadioActive = false;
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 5000L;

        timer.schedule(stopRadioSelection, delay);
    }

    public static void generateRadio(Player player, Block selectedBlock) {
        player.sendMessage(ChatColor.GRAY + "Generating radio station...");

        Location jukeboxLocation = selectedBlock.getLocation();
        Location chestLocation = new Location(player.getWorld(), jukeboxLocation.getX(), jukeboxLocation.getY() + 1, jukeboxLocation.getZ());
        chestLocation.getBlock().setType(Material.CHEST);

        player.playSound(jukeboxLocation, Sound.BLOCK_AMETHYST_BLOCK_BREAK, 3.0F, 0.5F);

        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.GRAY + " Radio station is up and running!");
        player.sendMessage("");

        saveRadioData(player, jukeboxLocation);
    }

    private static void saveRadioData(Player player, Location jukeboxLocation) {
        Main.loadData();

        try {
            Main.config.set(player.getDisplayName() + ".radio-location.x", String.valueOf(jukeboxLocation.getX()));
            Main.config.set(player.getDisplayName() + ".radio-location.y", String.valueOf(jukeboxLocation.getY()));
            Main.config.set(player.getDisplayName() + ".radio-location.z", String.valueOf(jukeboxLocation.getZ()));
            Main.config.set(player.getDisplayName() + ".radio-power", "off");

            Main.config.save(Main.dataFile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
