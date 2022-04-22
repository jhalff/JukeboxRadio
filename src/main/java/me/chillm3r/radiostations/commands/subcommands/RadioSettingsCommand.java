package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import me.chillm3r.radiostations.listeners.PlayerChatListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Timer;
import java.util.TimerTask;

import static me.chillm3r.radiostations.ChatMessages.*;

public class RadioSettingsCommand extends SubCommand {

    private static Inventory menu;
    private static ItemStack settingNameItem = new ItemStack(Material.NAME_TAG);
    private static ItemMeta settingNameMeta = settingNameItem.getItemMeta();

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return "Adjust your radio settings";
    }

    @Override
    public String getSyntax() {
        return "/radio settings";
    }

    @Override
    public void perform(Player player, String[] args) {
        Main.loadData();

        if (Main.config.get(player.getUniqueId().toString()) != null) {
            generateMenu(player);
        } else {
            sendNoRadioFoundMessage(player);
        }
    }

    private void generateMenu(Player player) {
        Main.loadData();

        menu = Bukkit.createInventory(null, 9, Main.config.getString(player.getUniqueId() + ".radio-settings.name") + " — Settings");
        menu.setItem(2, settingNameItem);
        settingNameMeta.setDisplayName("Change station name");
        settingNameItem.setItemMeta(settingNameMeta);

        InventoryClickListener.radioMenuActive = true;
        player.openInventory(menu);
    }

    public static void changeRadioName(Player player) {
        sendChangeRadioNameMessage(player);
        PlayerChatListener.radioNameChangeActive = true;
        player.closeInventory();

        TimerTask stopRadioNameChange = new TimerTask() {
            public void run() {
            if (PlayerChatListener.radioNameChangeActive) {
                sendNoRadioNameProvidedMessage(player);
                PlayerChatListener.radioNameChangeActive = false;
            }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 10000L;

        timer.schedule(stopRadioNameChange, delay);
    }
}
