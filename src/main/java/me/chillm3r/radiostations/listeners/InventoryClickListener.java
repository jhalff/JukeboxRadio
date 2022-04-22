package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.subcommands.RadioMenuCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioSettingsCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    public static Boolean radioMenuActive = false;
    public static Boolean radioMenuSettingsActive = false;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity player = e.getWhoClicked();
        Inventory radioInv = Main.getRadioInventory((Player) player);

        // Radio Menu
        if (radioMenuActive) {
            if (e.getClick().toString() == "LEFT" || e.getClick().toString() == "RIGHT") {
                if (e.getCurrentItem() != null) {
                    String selectedItem = e.getCurrentItem().getType().name();

                    // Settings
                    if (selectedItem == "BOOK") {
                        ((Player) player).performCommand("radio settings");
                    }

                    // Power
                    if (selectedItem == "RED_WOOL" || selectedItem == "LIME_WOOL") {
                        RadioMenuCommand.toggleRadioPower(player);
                    }

                    // Stats
                    if (selectedItem == "PAPER") {
                        ((Player) player).performCommand("radio stats");
                    }
                }
            }
        }

        // Radio Menu Settings
        if (radioMenuSettingsActive) {
            if (e.getClick().toString() == "LEFT" || e.getClick().toString() == "RIGHT") {
                if (e.getCurrentItem() != null) {
                    String selectedItem = e.getCurrentItem().getType().name();

                    // Edit Station Name
                    if (selectedItem == "NAME_TAG") {
                        RadioSettingsCommand.changeRadioName((Player) player);
                    }
                }
            }
        }
    }
}
