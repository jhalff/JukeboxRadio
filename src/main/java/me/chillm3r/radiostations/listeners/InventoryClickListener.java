package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.commands.subcommands.RadioMenuCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    public static Boolean radioMenuActive = false;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity player = e.getWhoClicked();

        // Toggle radio power
        if (radioMenuActive) {
            if (e.getClick().toString() == "LEFT" || e.getClick().toString() == "RIGHT") {
                if (e.getCurrentItem() != null) {
                    String selectedItem = e.getCurrentItem().getType().name();

                    if (selectedItem == "RED_WOOL" || selectedItem == "LIME_WOOL") {
                        RadioMenuCommand.toggleRadioPower(player);
                    }
                }
            }
        }


    }
}
