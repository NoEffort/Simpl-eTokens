package me.noeffort.simpletokens.api;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.handler.ConfigHandler;

public class IConfig {

	Main plugin = Main.get();
	
	public IConfig() {}
	
	private ConfigHandler handler;
	
	public String[] getPaths() {
		handler = plugin.getConfigHandler();
		String[] paths = new String[handler.getPaths().size()];
		for(int i = 0; i < handler.getPaths().size(); i++) {
			paths[i] = (String) handler.getPaths().toArray()[i];
		}
		return paths;
	}
	
	public HashSet<Player> getPlayers() {
		HashSet<Player> set = new HashSet<Player>();
		handler = plugin.getConfigHandler();
		
		for(UUID id : handler.getPlayers()) {
			set.add(Bukkit.getServer().getPlayer(id));
		}
		return set;
	}
	
	public Player getPlayer(UUID id) {
		handler = plugin.getConfigHandler();
		for(UUID uuid : handler.getPlayers()) {
			if(uuid.equals(id)) {
				return Bukkit.getServer().getPlayer(id);
			}
		}
		return null;
	}
	
	public HashSet<UUID> getIds() {
		HashSet<UUID> set = new HashSet<UUID>();
		handler = plugin.getConfigHandler();
		
		for(UUID id : handler.getPlayers()) {
			set.add(id);
		}
		return set;
	}
	
	public UUID getUUID(Player player) {
		handler = plugin.getConfigHandler();
		for(UUID id : getIds()) {
			if(player.getUniqueId().equals(id)) {
				return player.getUniqueId();
			}
		}
		return null;
	}
	
	public String valueOf(String path) {
		handler = plugin.getConfigHandler();
		return handler.replaceString(path);
	}
	
	public boolean isPlayerPath(String path) {
		if(path.startsWith("player.")) {
			return true;
		}
		return false;
	}
	
	public boolean isMessagePath(String path) {
		if(path.startsWith("message.")) {
			return true;
		}
		return false;
	}
	
	public boolean isOptionsPath(String path) {
		if(path.startsWith("options.")) {
			return true;
		}
		return false;
	}
	
}
