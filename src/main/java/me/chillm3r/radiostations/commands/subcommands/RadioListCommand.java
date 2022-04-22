package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import me.chillm3r.radiostations.listeners.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.List;

import static me.chillm3r.radiostations.ChatMessages.sendTunedInMessage;

public class RadioListCommand extends SubCommand {

    private static Inventory menu;
    private static ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD);
    private static SkullMeta playerMeta = (SkullMeta) playerItem.getItemMeta();

    private static List<String> radioListeners = new ArrayList<>();

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Get a list of all available radio's";
    }

    @Override
    public String getSyntax() {
        return "/radio list";
    }

    @Override
    public void perform(Player player, String[] args) {
        Main.loadData();
        generateMenu(player);
    }

    private void generateMenu(Player player) {
        menu = Bukkit.createInventory(null, 54, "Available Radio's");
        List<Player> radioList = new ArrayList<>();
        Integer count = 0;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Main.config.get(p.getUniqueId().toString()) != null) {
                if (Main.config.get(p.getUniqueId() + ".radio-power").toString().equalsIgnoreCase("on")) {
                    radioList.add(p);
                }
            }
        }

        for (Player p : radioList) {
            playerMeta.setDisplayName(Main.config.getString(p.getUniqueId() + ".radio-settings.name"));
            playerMeta.setOwningPlayer(p);
            playerItem.setItemMeta(playerMeta);
            menu.setItem(count, playerItem);
            count++;
        }

        InventoryClickListener.radioMenuActive = true;
        player.openInventory(menu);
    }

    public static void selectRadio(ItemStack selectedRadio, Player player) {
        String radioName = selectedRadio.getItemMeta().getDisplayName();
        SkullMeta meta = (SkullMeta) selectedRadio.getItemMeta();
        PlayerProfile radioOwner = meta.getOwnerProfile();

        radioListeners = (List<String>) Main.config.getList(radioOwner.getUniqueId() + ".radio-listeners");
        if (radioListeners.toString().contains(player.getUniqueId().toString()) == false) {
            radioListeners.add(player.getUniqueId().toString());

            try {
                Main.config.set(radioOwner.getUniqueId() + ".radio-listeners", radioListeners);
                Main.config.save(Main.dataFile);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        sendTunedInMessage(player, radioName);
        player.closeInventory();

        playMusic(player, radioOwner);
    }

    private static void playMusic(Player player, PlayerProfile radioOwner) {
        Inventory radioInv = Main.getRadioInventory((Player) radioOwner);
        player.sendMessage(radioInv.toString());
    }
}
