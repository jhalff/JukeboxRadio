package me.chillm3r.radiostations.listeners;

import me.chillm3r.radiostations.Main;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.chillm3r.radiostations.ChatMessages.sendRadioNameChangedMessage;

public class PlayerChatListener implements Listener {

    public static Boolean radioNameChangeActive = false;

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String msg = e.getMessage();

        // Radio Name Change
        if (radioNameChangeActive) {
            Main.loadData();
            String radioName = WordUtils.capitalizeFully(msg);

            try {
                Main.config.set(player.getName() + ".radio-settings.name", radioName);
                Main.config.save(Main.dataFile);

                radioNameChangeActive = false;
                sendRadioNameChangedMessage(player, radioName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
