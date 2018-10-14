package me.noeffort.simpletokens.util.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.noeffort.simpletokens.Main;

public class MessagesConfig {

	Main plugin = Main.get();
	
	ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
	
	private File file = null;
	private FileConfiguration config = null;
	
	public FileConfiguration getConfig() {
		if(config == null) {
			reloadConfig();
		}
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean createFile() {
		if(file == null) {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
				sender.sendMessage(ChatColor.GREEN + "Plugin folder created!");
			}
			
			file = new File(plugin.getDataFolder(), "messages.yml");
			
			if(!file.exists()) {
				try {
					file.createNewFile();
					sender.sendMessage(ChatColor.GREEN + "Messages file created!");
				} catch (IOException e) {
					e.printStackTrace();
					sender.sendMessage(ChatColor.RED + "Error creating Messages file!");
				}
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		loadDefaultConfiguration();
		return true;
	}
	
	private void loadDefaultConfiguration() {
		config.addDefault("messages.prefix", "&e[&7Simpl-eTokens&e]&f ");
		config.addDefault("messages.reload.general", "&7Reloading files...");
		config.addDefault("messages.reload.success", "&aConfig files reloaded!");
		config.addDefault("messages.reload.failure", "&cError reloading config files!");
		config.addDefault("messages.give.self", "&eYou've been given %TOKENS tokens!");
		config.addDefault("messages.give.others", "&eYou gave %TARGET %TOKENS tokens!");
		config.addDefault("messages.give.receive", "&e%PLAYER gave you %TOKENS tokens!");
		config.addDefault("messages.balance.self", "&eCurrent balance: &6%BAL &etokens.");
		config.addDefault("messages.balance.others", "&e%TARGET's balance: &6%USERBAL &etokens.");
		config.addDefault("messages.balance.top", "&eTop 10 balances:");
		config.addDefault("messages.help", "&eHelp Dialog:");
		config.addDefault("messages.missing.target", "&cNo target specified.");
		config.addDefault("messages.offline.target", "&cThe target you specified may not be online.");
		config.addDefault("messages.missing.permission", "&cYou do not have the proper permissions.");
		config.addDefault("messages.missing.command", "&7The command you entered is not valid!");
		config.addDefault("messages.missing.amount", "&7Please specify an amount to give!");
		config.addDefault("messages.missing.int", "&cPlease use and specify an integer!");
		config.addDefault("messages.missing.usage", "&cInvalid usage, please try /token help");
		config.addDefault("messages.reset.self", "&bYour tokens have been reset!");
		config.addDefault("messages.reset.others", "&b%TARGET's tokens have been reset!");
		config.addDefault("messages.values.over", "&cYou have entered too many arguments.");
		config.addDefault("messages.tokens.negative", "&7You cannot give negative tokens!");
		config.addDefault("messages.tokens.afford", "&7You cannot afford this transaction! You have: &e%BAL &7tokens!");
		config.addDefault("messages.tokens.redeem", "&bYou successfully redeemed %TOKENS tokens!");
		config.addDefault("messages.error", "&cAn error has occured, please contact plugin developer(s)!");
		config.addDefault("messages.debug.enabled", "&cDebug Mode is not enabled! Enable it in options.yml!");
		
		config.options().copyDefaults(true);
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void saveConfig() {
		if(config == null || file == null) {
			return;
		}
		
		try {
			config.save(file);
		} catch(IOException ignored) {}
	}
	
	public void saveDefaultConfig() {
		if(file == null) {
			file = new File(plugin.getDataFolder(), "messages.yml");
			plugin.saveResource("messages.yml", false);
		}
		
		if(file.exists()) {
			plugin.saveResource("messages.yml", false);
		}
	}
	
}
