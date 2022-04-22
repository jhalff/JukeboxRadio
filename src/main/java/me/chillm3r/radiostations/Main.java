package me.chillm3r.radiostations;

import me.chillm3r.radiostations.commands.CommandManager;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import me.chillm3r.radiostations.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public static File dataFile;
    public static YamlConfiguration config;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("radio").setExecutor(new CommandManager());

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        System.out.println("RadioStations started successfully");
    }

    @Override
    public void onDisable() {
        System.out.println("RadioStations stopped successfully");
    }

    public static void loadData() {
        try {
            dataFile = new File(Main.getPlugin(Main.class).getDataFolder(), "data.yml");
            config = new YamlConfiguration();

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            } else {
                config.load(dataFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Inventory getRadioInventory(Player player) {
        Main.loadData();

        String dataX = (String) Main.config.get(player.getName() + ".radio-location.x");
        String dataY = (String) Main.config.get(player.getName() + ".radio-location.y");
        String dataZ = (String) Main.config.get(player.getName() + ".radio-location.z");

        Location radioStorageLocation = new Location(player.getWorld(), Double.parseDouble(dataX), Double.parseDouble(dataY) + 1, Double.parseDouble(dataZ));
        Chest radioStorage = (Chest) player.getWorld().getBlockAt(radioStorageLocation).getState();
        InventoryHolder chest = (InventoryHolder)radioStorage;
        Inventory radioInv = chest.getInventory();

        return radioInv;
    }
}
