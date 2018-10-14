package me.noeffort.simpletokens.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.config.MessagesConfig;
import me.noeffort.simpletokens.util.config.OptionsConfig;
import me.noeffort.simpletokens.util.config.PlayerConfig;
import me.noeffort.simpletokens.util.handler.ConfigHandler;
import me.noeffort.simpletokens.util.handler.ItemHandler;
import me.noeffort.simpletokens.util.handler.PlayerFileHandler;

public class IToken {

	Main plugin = Main.get();
	ItemHandler item = null;
	ConfigHandler handler = null;
	PlayerConfig config = null;
	PlayerFileHandler fileHandler = null;
	OptionsConfig options = null;
	
	public IToken() {}
	
	public ItemStack addToken(Player player, int amount) {
		item = plugin.getItemHandler();
		return item.giveTokens(player, amount);
	}
	
	public int getTokens(Player player) {
		handler = plugin.getConfigHandler();
		handler.assignPlayer(player);
		return Integer.parseInt(handler.replaceString(handler.playerTokens));
	}
	
	public void giveTokens(Player player, int amount) {
		handler = plugin.getConfigHandler();
		config = plugin.getPlayerConfig();
		handler.assignPlayer(player);
		if(amount > 0) {
			throw new IllegalArgumentException("Tokens given cannot be a negative number!");
		}
		int current = config.getConfig().getInt(handler.replaceString(handler.playerTokens));
		config.getConfig().set(handler.replaceString(handler.playerTokens), current + amount);
	}
	
	public void removeTokens(Player player, int amount) {
		handler = plugin.getConfigHandler();
		config = plugin.getPlayerConfig();
		handler.assignPlayer(player);
		if(amount > 0) {
			throw new IllegalArgumentException("Tokens given cannot be a negative number!");
		}
		int current = config.getConfig().getInt(handler.replaceString(handler.playerTokens));
		config.getConfig().set(handler.replaceString(handler.playerTokens), current - amount);
	}
	
	public int getSecondsPlayed(Player player) {
		handler = plugin.getConfigHandler();
		handler.assignPlayer(player);
		return Integer.parseInt(handler.replaceString(handler.playerTime));
	}
	
	public PlayerConfig getPlayerConfig() {
		return plugin.getPlayerConfig();
	}
	
	public MessagesConfig getMessageConfig() {
		return plugin.getMessageConfig();
	}
	
	public OptionsConfig getOptionsConfig() {
		return plugin.getOptionsConfig();
	}
	
	public void startDelayCounter() {
		fileHandler = plugin.getFileHandler();
		fileHandler.checkTokens();
	}
	
	public void stopDelayCounter() {
		fileHandler = plugin.getFileHandler();
		fileHandler.stopTask(fileHandler.getTaskID());
	}
	
	public boolean ifCounter() {
		options = plugin.getOptionsConfig();
		return options.ifDelayEnabled();
	}
	
}
