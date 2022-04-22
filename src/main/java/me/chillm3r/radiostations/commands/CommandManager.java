package me.chillm3r.radiostations.commands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.subcommands.RadioCreateCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioMenuCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioSettingsCommand;
import me.chillm3r.radiostations.commands.subcommands.RadioListCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static me.chillm3r.radiostations.ChatMessages.chatMessageHeader;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new RadioCreateCommand());
        subCommands.add(new RadioMenuCommand());
        subCommands.add(new RadioSettingsCommand());
        subCommands.add(new RadioListCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length != 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                        break;
                    } else {
                        sendInvalidCommandMessage(player, args, i);
                    }
                }
            } else {
                Main.loadData();
                player.performCommand("radio menu");
            }
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }



    private void sendHelpMessage(Player player) {
        chatMessageHeader(player);
        player.sendMessage(ChatColor.GREEN + "Available radio commands");
        for (int i = 0; i < getSubCommands().size(); i++) {
            player.sendMessage(ChatColor.GRAY + getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
        }
        player.sendMessage("");
    }

    private void sendInvalidCommandMessage(Player player, String[] args, Integer commandCount) {
        if (commandCount == getSubCommands().size() - 1) {
            String argsText = "";

            if (args.length > 0) {
                argsText = " " + Arrays.toString(args).replace("[", "").replace("]", "");
            }

            chatMessageHeader(player);
            player.sendMessage(ChatColor.RED + "/radio" + argsText + ChatColor.GRAY + " is an invalid command, please try:");
            for (int i = 0; i < getSubCommands().size(); i++) {
                player.sendMessage(ChatColor.GRAY + getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
            }
            player.sendMessage("");
        }
    }
}
