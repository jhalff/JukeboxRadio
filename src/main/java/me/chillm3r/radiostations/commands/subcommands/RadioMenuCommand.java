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

public class RadioMenuCommand extends SubCommand {

    private static Inventory menu;
    private static Inventory guestMenu;
    private static ItemStack radioCreateItem = new ItemStack(Material.ANVIL);
    private static ItemStack radioSettingsItem = new ItemStack(Material.BOOK);
    private static ItemStack radioPowerItem = new ItemStack(Material.RED_WOOL);
    private static ItemStack radioListItem = new ItemStack(Material.NOTE_BLOCK);
    private static ItemMeta radioCreateMeta = radioCreateItem.getItemMeta();
    private static ItemMeta radioSettingsMeta = radioSettingsItem.getItemMeta();
    private static ItemMeta radioPowerMeta = radioPowerItem.getItemMeta();
    private static ItemMeta radioListMeta = radioListItem.getItemMeta();

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

        if (Main.config.get(player.getUniqueId().toString()) != null) {
            generateMenu(player);
        } else {
            generateGuestMenu(player);
        }
    }

    // Menu generation
    private static void generateMenu(Player player) {
        menu = Bukkit.createInventory(null, 9, Main.config.getString(player.getUniqueId() + ".radio-settings.name"));

        radioSettingsMeta.setDisplayName("Settings");
        radioSettingsItem.setItemMeta(radioSettingsMeta);
        setRadioPower(player);
        radioListMeta.setDisplayName("Tune in to radio");
        radioListItem.setItemMeta(radioListMeta);

        menu.setItem(2, radioSettingsItem);
        menu.setItem(4, radioPowerItem);
        menu.setItem(6, radioListItem);

        InventoryClickListener.radioMenuActive = true;
        player.openInventory(menu);
    }

    private static void generateGuestMenu(Player player) {
        guestMenu = Bukkit.createInventory(null, 9, "Radio");

        radioCreateMeta.setDisplayName("Create radio");
        radioCreateItem.setItemMeta(radioCreateMeta);
        radioListMeta.setDisplayName("Tune in to radio");
        radioListItem.setItemMeta(radioListMeta);

        guestMenu.setItem(3, radioCreateItem);
        guestMenu.setItem(5, radioListItem);

        InventoryClickListener.radioMenuActive = true;
        player.openInventory(guestMenu);
    }



    public static void toggleRadioPower(HumanEntity player) {
        Main.loadData();
        String radioPower = WordUtils.capitalizeFully(Main.config.getString(player.getUniqueId() + ".radio-power"));

        if (radioPower.equalsIgnoreCase("off")) {
            Inventory radioInv = Main.getRadioInventory((Player) player);

            if (radioInv != null) {
                Boolean hasMusic = false;

                for (ItemStack item : radioInv.getContents()) {
                    if (item != null) {
                        if (item.getType().name().contains("MUSIC_DISC")) {
                            Main.config.set(player.getUniqueId() + ".radio-power", "on");
                            hasMusic = true;
                        }
                    }
                }

                if (hasMusic == false) {
                    sendNoMusicFoundMessage((Player) player);
                }
            }
        } else {
            Main.config.set(player.getUniqueId() + ".radio-power", "off");
        }

        try {
            Main.config.save(Main.dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        generateMenu(Bukkit.getPlayer(player.getUniqueId()));
    }

    private static void setRadioPower(Player player) {
        String radioPower = WordUtils.capitalizeFully(Main.config.getString(player.getUniqueId() + ".radio-power"));
        radioPowerMeta.setDisplayName("Power");
        radioPowerItem.setItemMeta(radioPowerMeta);

        if (radioPower.equalsIgnoreCase("on")) {
            radioPowerItem.setType(Material.LIME_WOOL);
        } else {
            radioPowerItem.setType(Material.RED_WOOL);
        }
    }
}
