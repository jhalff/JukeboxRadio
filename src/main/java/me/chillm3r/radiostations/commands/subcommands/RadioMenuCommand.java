package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RadioMenuCommand extends SubCommand {

    private static Inventory menu;
    private Boolean isMenuGenerated = false;
    private static Boolean radioPower = false;
    private static ItemStack radioPowerItem = new ItemStack(Material.RED_WOOL);
    private static ItemMeta radioPowerMeta = radioPowerItem.getItemMeta();

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
        if (!isMenuGenerated) {
            generateMenu(player);
            sendNoRadioFoundMessage(player);

        } else {
            Main.loadData();

            if (Main.config.get(player.getDisplayName()) != null) {
                InventoryClickListener.radioMenuActive = true;
                player.openInventory(menu);
            } else {
                sendNoRadioFoundMessage(player);
            }
        }
    }

    private void generateMenu(Player player) {
        radioPowerMeta.setDisplayName("Power");
        radioPowerItem.setItemMeta(radioPowerMeta);

        menu = Bukkit.createInventory(null, 9, WordUtils.capitalizeFully(player.getDisplayName()) + "'s Radio");
        menu.setItem(4, radioPowerItem);

        InventoryClickListener.radioMenuActive = true;
        isMenuGenerated = true;
    }

    public static void toggleRadioPower(HumanEntity player) {
        if (radioPower) {
            radioPower = false;
            radioPowerItem.setType(Material.RED_WOOL);
            menu.setItem(4, radioPowerItem);
        } else {
            radioPower = true;
            radioPowerItem.setType(Material.LIME_WOOL);
            menu.setItem(4, radioPowerItem);
        }
    }

    private void sendNoRadioFoundMessage(Player player) {
        Main.chatMessageHeader(player);
        player.sendMessage(ChatColor.RED + "No radio found!" + ChatColor.GRAY + " Create your radio by executing:");
        player.sendMessage(ChatColor.WHITE + "/radio create");
        player.sendMessage("");
    }
}
