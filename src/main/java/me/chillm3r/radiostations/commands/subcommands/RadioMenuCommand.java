package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

import static me.chillm3r.radiostations.ChatMessages.sendNoMusicFoundMessage;
import static me.chillm3r.radiostations.ChatMessages.sendNoRadioFoundMessage;

public class RadioMenuCommand extends SubCommand {

    private static Inventory menu;
    private static ItemStack radioSettingsItem = new ItemStack(Material.BOOK);
    private static ItemStack radioPowerItem = new ItemStack(Material.RED_WOOL);
    private static ItemStack radioStatsItem = new ItemStack(Material.PAPER);
    private static ItemMeta radioSettingsMeta = radioSettingsItem.getItemMeta();
    private static ItemMeta radioPowerMeta = radioPowerItem.getItemMeta();
    private static ItemMeta radioStatsMeta = radioStatsItem.getItemMeta();

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Open radio menu";
    }

    @Override
    public String getSyntax() {
        return "/radio menu";
    }

    @Override
    public void perform(Player player, String[] args) {
        Main.loadData();

        if (Main.config.get(player.getDisplayName()) != null) {
            generateMenu(player);
        } else {
            sendNoRadioFoundMessage(player);
        }
    }

    public static void toggleRadioPower(HumanEntity player) {
        Main.loadData();
        String radioPower = WordUtils.capitalizeFully(Main.config.getString(player.getName() + ".radio-power"));

        if (radioPower.equalsIgnoreCase("off")) {
            Inventory radioInv = Main.getRadioInventory((Player) player);

            if (radioInv != null) {
                Boolean hasMusic = false;

                for (ItemStack item : radioInv.getContents()) {
                    if (item != null) {
                        if (item.getType().name().contains("MUSIC_DISC")) {
                            Main.config.set(player.getName() + ".radio-power", "on");
                            hasMusic = true;
                        }
                    }
                }

                if (hasMusic == false) {
                    sendNoMusicFoundMessage((Player) player);
                }
            }
        } else {
            Main.config.set(player.getName() + ".radio-power", "off");
        }

        try {
            Main.config.save(Main.dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        generateMenu(Bukkit.getPlayer(player.getUniqueId()));
    }



    // Menu generation
    private static void generateMenu(Player player) {
        menu = Bukkit.createInventory(null, 9, Main.config.getString(player.getDisplayName() + ".radio-settings.name"));
        menu.setItem(2, radioSettingsItem);
        menu.setItem(4, radioPowerItem);
        menu.setItem(6, radioStatsItem);

        radioSettingsMeta.setDisplayName("Settings");
        radioSettingsItem.setItemMeta(radioSettingsMeta);
        setRadioPower(player);
        radioStatsMeta.setDisplayName("Statistics");
        radioStatsItem.setItemMeta(radioStatsMeta);

        InventoryClickListener.radioMenuActive = true;
        player.openInventory(menu);
    }

    private static void setRadioPower(Player player) {
        String radioPower = WordUtils.capitalizeFully(Main.config.getString(player.getDisplayName() + ".radio-power"));
        radioPowerMeta.setDisplayName("Power");
        radioPowerItem.setItemMeta(radioPowerMeta);

        if (radioPower.equalsIgnoreCase("on")) {
            radioPowerItem.setType(Material.LIME_WOOL);
        } else {
            radioPowerItem.setType(Material.RED_WOOL);
        }
    }
}
