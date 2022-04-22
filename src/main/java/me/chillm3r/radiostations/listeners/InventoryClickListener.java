package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.commands.subcommands.RadioListCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioMenuCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioSettingsCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    public static Boolean radioMenuActive = false;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity player = e.getWhoClicked();

        // Radio Menu
        if (radioMenuActive) {
            if (e.getClick().toString() == "LEFT" || e.getClick().toString() == "RIGHT") {
                if (e.getCurrentItem() != null) {
                    ItemStack selectedItem = e.getCurrentItem();
                    String selectedItemName = e.getCurrentItem().getType().name();

                    if (selectedItemName == "BOOK") {
                        ((Player) player).performCommand("radio settings");
                    }
                    if (selectedItemName == "RED_WOOL" || selectedItemName == "LIME_WOOL") {
                        RadioMenuCommand.toggleRadioPower(player);
                    }
                    if (selectedItemName == "NOTE_BLOCK") {
                        ((Player) player).performCommand("radio list");
                    }
                    if (selectedItemName == "ANVIL") {
                        ((Player) player).performCommand("radio create");
                        player.closeInventory();
                    }
                    if (selectedItemName == "NAME_TAG") {
                        RadioSettingsCommand.changeRadioName((Player) player);
                    }
                    if (selectedItemName == "PLAYER_HEAD") {
                        RadioListCommand.selectRadio(selectedItem, (Player) player);
                    }
                }
            }
        }
    }
}
