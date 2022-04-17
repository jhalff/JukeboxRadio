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

import java.io.IOException;

public class RadioMenuCommand extends SubCommand {

    private static Inventory menu;
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
        Main.loadData();

        if (Main.config.get(player.getDisplayName()) != null) {
            generateMenu(player);
        } else {
            sendNoRadioFoundMessage(player);
        }
    }

    private static void generateMenu(Player player) {
        setRadioPower(player);

        menu = Bukkit.createInventory(null, 9, WordUtils.capitalizeFully(player.getDisplayName()) + "'s Radio");
        menu.setItem(4, radioPowerItem);

        InventoryClickListener.radioMenuActive = true;

        player.openInventory(menu);
    }

    public static void toggleRadioPower(HumanEntity player) {
        Main.loadData();
        String radioPower = WordUtils.capitalizeFully(Main.config.getString(player.getName() + ".radio-power"));

        if (radioPower.equalsIgnoreCase("on")) {
            Main.config.set(player.getName() + ".radio-power", "off");
        } else {
            Main.config.set(player.getName() + ".radio-power", "on");
        }

        try {
            Main.config.save(Main.dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        generateMenu(Bukkit.getPlayer(player.getUniqueId()));
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

    private void sendNoRadioFoundMessage(Player player) {
        Main.chatMessageHeader(player);
        player.sendMessage(ChatColor.RED + "No radio found!" + ChatColor.GRAY + " Create your radio by executing:");
        player.sendMessage(ChatColor.WHITE + "/radio create");
        player.sendMessage("");
    }
}
