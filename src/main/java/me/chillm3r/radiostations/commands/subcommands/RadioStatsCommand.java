package me.chillm3r.radiostations.commands.subcommands;

import me.chillm3r.radiostations.Main;
import me.chillm3r.radiostations.commands.SubCommand;
import org.bukkit.entity.Player;

import static me.chillm3r.radiostations.ChatMessages.sendNoRadioFoundMessage;

public class RadioStatsCommand extends SubCommand {

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Get a list of your radio's statistics";
    }

    @Override
    public String getSyntax() {
        return "/radio stats";
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

    private void generateMenu(Player player) {
        player.sendMessage("Open stats");
    }
}
