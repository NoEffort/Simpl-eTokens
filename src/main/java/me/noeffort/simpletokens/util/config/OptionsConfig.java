package me.noeffort.simpletokens.util.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.noeffort.simpletokens.Main;

public class OptionsConfig {

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
			
			file = new File(plugin.getDataFolder(), "options.yml");
			
			if(!file.exists()) {
				try {
					file.createNewFile();
					sender.sendMessage(ChatColor.GREEN + "Options file created!");
				} catch (IOException e) {
					e.printStackTrace();
					sender.sendMessage(ChatColor.RED + "Error creating Options file!");
				}
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		loadDefaultConfiguration();
		return true;
	}
	
	private void loadDefaultConfiguration() {
		config.addDefault("options.delay.time", 3600);
		config.addDefault("options.delay.enabled", true);
		config.addDefault("options.give.amount", 50);
		config.addDefault("options.debug.enabled", false);
		
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
			file = new File(plugin.getDataFolder(), "options.yml");
			plugin.saveResource("options.yml", false);
		}
		
		if(file.exists()) {
			plugin.saveResource("options.yml", false);
		}
	}
	
	public boolean ifDelayEnabled() {
		return config.getBoolean("options.delay.enabled");
	}
	
	public boolean ifDebugEnabled() {
		return config.getBoolean("options.debug.enabled");
	}
	
}
