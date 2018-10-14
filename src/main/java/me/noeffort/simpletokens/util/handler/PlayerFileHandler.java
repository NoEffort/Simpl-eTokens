package me.noeffort.simpletokens.util.handler;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.config.OptionsConfig;
import me.noeffort.simpletokens.util.config.PlayerConfig;

public class PlayerFileHandler implements Listener {	
	
	Main plugin = Main.get();
	
	public PlayerFileHandler() { }
	
	PlayerConfig config;
	OptionsConfig options;
	ConfigHandler handler;
	ItemHandler item;
	
	static HashMap<UUID, Integer> join = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = event.getPlayer();
			if(player == null) {
				return;
			} else {
				config = plugin.getPlayerConfig();
				options = plugin.getOptionsConfig();
				handler = plugin.getConfigHandler();
				
				handler.assignPlayer(player);
				
				if(!config.getConfig().isSet(handler.replaceString(handler.playerNamePath))) {
					config.getConfig().set(handler.replaceString(handler.playerNamePath), player.getName());
				}
				
				if(!config.getConfig().isSet(handler.replaceString(handler.playerTokensPath))) {
					config.getConfig().set(handler.replaceString(handler.playerTokensPath), 0);
				}
				
				if(!config.getConfig().isSet(handler.replaceString(handler.playerTimePath))) {
					config.getConfig().set(handler.replaceString(handler.playerTimePath), 0);
				}
				
				join.put(player.getUniqueId(), Integer.parseInt(handler.replaceString(handler.playerTime)));
				
				if(join.containsKey(player.getUniqueId())) { 
					Player debug = Bukkit.getServer().getPlayer(player.getUniqueId());
					System.out.println("PLAYER ADDED: " + debug.getName() + "; " + Integer.parseInt(handler.replaceString(handler.playerTime)));
				}
				
				if(options.ifDelayEnabled()) { 
					checkTokens();
				}
				
				config.saveConfig();
				config.reloadConfig();
			}
		}
	}	
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = event.getPlayer();
			if(player == null) {
				return;
			} else {
				config = plugin.getPlayerConfig();
				handler = plugin.getConfigHandler();
				
				handler.assignPlayer(player);
				
				config.getConfig().set(handler.replaceString(handler.playerTimePath), join.get(player.getUniqueId()));
				if(join.containsKey(player.getUniqueId())) {
					join.remove(player.getUniqueId());
				}
				config.saveConfig();
				config.reloadConfig();
			}
		}
	} 
	
	private int taskID = 0;
	
	public void checkTokens() {
		
		item = plugin.getItemHandler();
		config = plugin.getPlayerConfig();
		options = plugin.getOptionsConfig();
		handler = plugin.getConfigHandler();
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					
					handler.assignPlayer(player);
					
					if(!join.containsKey(player.getUniqueId())) {
						join.put(player.getUniqueId(), Integer.parseInt(handler.replaceString(handler.playerTime)));
					} else {
						int time = join.get(player.getUniqueId());
						if((time % options.getConfig().getInt("options.delay.time")) == 0) {
							handler.setTokens(options.getConfig().getInt("options.give.amount"));
							player.getInventory().addItem(item.giveTokens(player, options.getConfig().getInt("options.give.amount")));
							player.sendMessage(handler.appendPrefix(handler.give));
							config.saveConfig();
							config.reloadConfig();
						}
						time++;
						join.put(player.getUniqueId(), time);
						config.getConfig().set(handler.replaceString(handler.playerTimePath), time);
					}
				}
			}
		}, 0L, 20L);
	}
	
	public void reloadTokenCounter() {
		options = plugin.getOptionsConfig();
		options.reloadConfig();
		if(options.ifDelayEnabled()) {
			checkTokens();
		} else {
			stopTask(taskID);
		}
	}
	
	public HashMap<UUID, Integer> getJoinMap() {
		return join;
	}
	
	public void stopTask(int id) {
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public int getTaskID() {
		return taskID;
	}
}
