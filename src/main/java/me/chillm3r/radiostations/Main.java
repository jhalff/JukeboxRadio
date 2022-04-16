package me.chillm3r.radiostations;

import me.chillm3r.radiostations.commands.CommandManager;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import me.chillm3r.radiostations.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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

    public static void chatMessageHeader(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_BLUE + "{ === " + ChatColor.GOLD + "Radio Stations" + ChatColor.DARK_BLUE + " === }");
        player.sendMessage("");
    }
}
