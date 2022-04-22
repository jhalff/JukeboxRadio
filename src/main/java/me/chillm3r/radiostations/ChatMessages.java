package me.chillm3r.radiostations;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatMessages {

    public static void chatMessageHeader(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_BLUE + "{ === " + ChatColor.GOLD + "Radio Stations" + ChatColor.DARK_BLUE + " === }");
        player.sendMessage("");
    }


    // Radio Create
    public static void sendSelectRadioMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.GRAY + "Please" + ChatColor.WHITE + " RIGHT-CLICK" + ChatColor.GRAY + " on a" + ChatColor.BOLD + " jukebox");
        player.sendMessage("");
    }
    public static void sendNoRadioSelectedMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "No jukebox selected in time" + ChatColor.GRAY + " Try again by executing:");
        player.sendMessage(ChatColor.WHITE + "/radio create");
        player.sendMessage("");
    }
    public static void sendMultipleRadioMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.RED + "Whoops.." + ChatColor.GRAY + " You can only have 1 radio station.");
        player.sendMessage("");
    }
    public static void sendRadioCreatedMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.GRAY + " Radio station is up and running!");
        player.sendMessage("");
    }


    // Radio Menu
    public static void sendNoRadioFoundMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.RED + "No radio found!" + ChatColor.GRAY + " Create your radio by executing:");
        player.sendMessage(ChatColor.WHITE + "/radio create");
        player.sendMessage("");
    }
    public static void sendNoMusicFoundMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.RED + "No music!" + ChatColor.GRAY + " Please put" + ChatColor.WHITE + " music discs" + ChatColor.GRAY +  " in the chest");
        player.sendMessage("");
    }


    // Radio Settings
    public static void sendChangeRadioNameMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.GRAY + "Please provide a new radio name in chat");
        player.sendMessage("");
    }
    public static void sendNoRadioNameProvidedMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "No name provided!" + ChatColor.GRAY + " Cancelling radio name change...");
        player.sendMessage("");
    }
    public static void sendRadioNameChangedMessage(Player player, String radioName) {
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Success!" + ChatColor.GRAY + " New radio name is: " + ChatColor.WHITE + radioName);
        player.sendMessage("");
    }


    // Radio List
    public static void sendTunedInMessage(Player player, String radioName) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.GRAY + "Now listening to: " + ChatColor.WHITE + radioName);
        player.sendMessage("");
    }
}
