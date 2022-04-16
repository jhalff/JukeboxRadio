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

        if (radioMenuActive) {
            if (e.getClick().toString() == "LEFT" || e.getClick().toString() == "RIGHT") {
                e.setCancelled(true);
                String selectedItem = e.getCurrentItem().getItemMeta().getDisplayName();

                if (selectedItem.equals("Power")) {
                    RadioMenuCommand.toggleRadioPower(player);
                }
            }
        }
    }
}
