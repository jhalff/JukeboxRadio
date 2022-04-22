package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.PlayerInteractListener;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

import static me.chillm3r.radiostations.ChatMessages.*;

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
            sendSelectRadioMessage(player);
        } else {
            sendMultipleRadioMessage(player);
        }

        TimerTask stopRadioSelection = new TimerTask() {
            public void run() {
                if (PlayerInteractListener.selectRadioActive) {
                    sendNoRadioSelectedMessage(player);
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
        sendRadioCreatedMessage(player);
        saveRadioData(player, jukeboxLocation);
    }

    private static void saveRadioData(Player player, Location jukeboxLocation) {
        Main.loadData();

        try {
            Main.config.set(player.getDisplayName() + ".radio-location.x", String.valueOf(jukeboxLocation.getX()));
            Main.config.set(player.getDisplayName() + ".radio-location.y", String.valueOf(jukeboxLocation.getY()));
            Main.config.set(player.getDisplayName() + ".radio-location.z", String.valueOf(jukeboxLocation.getZ()));
            Main.config.set(player.getDisplayName() + ".radio-power", "off");
            Main.config.set(player.getDisplayName() + ".radio-settings.name", WordUtils.capitalizeFully(player.getDisplayName()) + "'s Radio");

            Main.config.save(Main.dataFile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
