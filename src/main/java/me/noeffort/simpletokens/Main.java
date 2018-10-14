package me.noeffort.simpletokens;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import me.noeffort.simpletokens.api.IConfig;
import me.noeffort.simpletokens.api.IPermission;
import me.noeffort.simpletokens.api.IToken;
import me.noeffort.simpletokens.command.TokenCommand;
import me.noeffort.simpletokens.util.config.MessagesConfig;
import me.noeffort.simpletokens.util.config.OptionsConfig;
import me.noeffort.simpletokens.util.config.PlayerConfig;
import me.noeffort.simpletokens.util.handler.ConfigHandler;
import me.noeffort.simpletokens.util.handler.ItemHandler;
import me.noeffort.simpletokens.util.handler.PlayerFileHandler;

public class Main extends JavaPlugin {

	private static Main instance;
	
	private PlayerConfig player;
	private MessagesConfig message;
	private OptionsConfig options;
	
	private ConfigHandler handler;
	private PlayerFileHandler fileHandler;
	private ItemHandler item;
	
	protected IToken token;
	protected IPermission permission;
	protected IConfig config;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		registerListeners();
		registerCommands();
		loadConfigFiles();
		loadHandlers();
		
		enableAPI();
		
		getServer().getServicesManager().register(IToken.class, token, this, ServicePriority.Normal);
		getServer().getServicesManager().register(IPermission.class, permission, this, ServicePriority.Normal);
		getServer().getServicesManager().register(IConfig.class, config, this, ServicePriority.Normal);
		
		enableMaps();
		enableCounter();
	}
	
	@Override
	public void onDisable() {
		
		disableMaps();
		disableCounter();
		
		player.saveConfig();
		message.saveConfig();
		options.saveConfig();
	}
	
	private void enableAPI() {
		token = new IToken();
		permission = new IPermission();
		config = new IConfig();
	}
	
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerFileHandler(), this);
		getServer().getPluginManager().registerEvents(new ItemHandler(), this);
	}
	
	private void registerCommands() {
		this.getCommand("token").setExecutor(new TokenCommand());
	}
	
	private void loadHandlers() {
		handler = new ConfigHandler();
		item = new ItemHandler();
		fileHandler = new PlayerFileHandler();
	}
	
	private void loadConfigFiles() {
		player = new PlayerConfig();
		player.createFile();
		
		message = new MessagesConfig();
		message.createFile();
		
		options = new OptionsConfig();
		options.createFile();
	}
	
	private void disableMaps() {
		
		try {
			for(Player user : Bukkit.getServer().getOnlinePlayers()) {
				fileHandler.getJoinMap().remove(user.getUniqueId());
			}
		} catch (Exception ignored) {}
		
	}
	
	private void enableMaps() {
		
		try {
			for(Player user : Bukkit.getServer().getOnlinePlayers()) {
				handler.assignPlayer(user);
				fileHandler.getJoinMap().put(user.getUniqueId(), Integer.parseInt(handler.replaceString(handler.playerTime)));
			}
		} catch (Exception ignored) {}
		
	}
	
	private void enableCounter() {
		
		if(options.getConfig().getBoolean("options.delay.enabled")) {
			fileHandler.checkTokens();
		}
		
	}
	
	private void disableCounter() {
		fileHandler.stopTask(fileHandler.getTaskID());
	}
	
	public static Main get() {
		return instance;
	}
	
	public PlayerConfig getPlayerConfig() {
		return player;
	}
	
	public MessagesConfig getMessageConfig() {
		return message;
	}
	
	public OptionsConfig getOptionsConfig() {
		return options;
	}
	
	public ConfigHandler getConfigHandler() {
		return handler;
	}
	
	public ItemHandler getItemHandler() {
		return item;
	}
	
	public PlayerFileHandler getFileHandler() {
		return fileHandler;
	}
	
}
