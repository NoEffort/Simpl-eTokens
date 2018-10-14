package me.noeffort.simpletokens.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {

	public MessageUtil() {}
	
	public String translate(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String stripColor(String message) {
		return ChatColor.stripColor(message);
	}
	
	public void displayDefaultCommands(Player player) {
		player.sendMessage(translate("&6/token help: &eDisplays this dialong.\n"
				+ "&6/token give: &eGive a player a specified amount of tokens.\n"
				+ "&6/token bal: &eView you or another users tokens.\n"
				+ "&6/token redeem: &eRedeem tokens to a physical form."));
	}
	
	public void displayAdminCommands(Player player) {
		displayDefaultCommands(player);
		player.sendMessage(translate("&6/token reset: &eReset your or another user's tokens!\n"
				+ "&6/token debug: &eShows debug info on the plugin."));
	}
	
	public void displayAllCommands(Player player) {
		displayAdminCommands(player);
		player.sendMessage(translate("&6/token reload: &eReloads the config files.\n"
				+ "&4**Only use the bottom two commands if you know what you're doing!!!**\n"
				+ "&6/token start: &eStarts the token counter forcibly.\n"
				+ "&6/token stop: &eStops the token counter forcibly."));
	}
	
}
