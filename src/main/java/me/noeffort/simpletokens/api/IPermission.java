package me.noeffort.simpletokens.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.noeffort.simpletokens.util.handler.Permissions;
import me.noeffort.simpletokens.util.handler.Permissions.PermissionLevel;

public class IPermission {

	public IPermission() {}
	
	private CommandSender sender = null;
	private Player player = null;
	private OfflinePlayer offlinePlayer = null;
	private PermissionLevel level = null;
	
	public PermissionLevel getLevel(CommandSender sender) {
		this.sender = sender;
		level = Permissions.getLevel(sender);
		return level;
	}
	
	public PermissionLevel getLevel(Player player) {
		this.player = player;
		level = Permissions.getLevel(player);
		return level;
	}
	
	public PermissionLevel getLevel(OfflinePlayer player) {
		offlinePlayer = player;
		level = Permissions.getLevel(player);
		return level;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}
	
}
