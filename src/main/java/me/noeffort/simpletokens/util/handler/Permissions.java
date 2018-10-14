package me.noeffort.simpletokens.util.handler;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permissions {
	
	public Permissions() {}
	
	private static String def = "tokens.default";
	private static String admin = "tokens.admin";
	private static String all = "tokens.*";
	
	public boolean has(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public boolean has(Player player, String permission) {
		return player.hasPermission(permission);
	}
	
	public boolean has(OfflinePlayer player, String permission) {
		return player.getPlayer().hasPermission(permission);
	}
	
	public boolean has(CommandSender sender, PermissionLevel level) {
		switch(level) {
		case DEFAULT:
			return sender.hasPermission(def);
		case ADMIN:
			return sender.hasPermission(admin);
		case ALL:
			return sender.hasPermission(all);
		default:
			return sender.hasPermission(def);
		}
	}
	
	public boolean has(Player player, PermissionLevel level) {
		switch(level) {
		case DEFAULT:
			return player.hasPermission(def);
		case ADMIN:
			return player.hasPermission(admin);
		case ALL:
			return player.hasPermission(all);
		default:
			return player.hasPermission(def);
		}
	}
	
	public boolean has(OfflinePlayer player, PermissionLevel level) {
		switch(level) {
		case DEFAULT:
			return player.getPlayer().hasPermission(def);
		case ADMIN:
			return player.getPlayer().hasPermission(admin);
		case ALL:
			return player.getPlayer().hasPermission(all);
		default:
			return player.getPlayer().hasPermission(def);
		}
	}
	
	public static PermissionLevel getLevel(CommandSender sender) {
		if(sender.hasPermission(def) && !sender.hasPermission(admin) && !sender.hasPermission(all)) {
			return PermissionLevel.DEFAULT;
		}
		else if(sender.hasPermission(admin) && !sender.hasPermission(def) && !sender.hasPermission(all)) {
			return PermissionLevel.ADMIN;
		}
		else if(sender.hasPermission(all) && !sender.hasPermission(admin) && !sender.hasPermission(def)) {
			return PermissionLevel.ALL;
		} else {
			return PermissionLevel.DEFAULT;
		}
	}
	
	public static PermissionLevel getLevel(Player player) {
		if(player.hasPermission(def) && !player.hasPermission(admin) && !player.hasPermission(all)) {
			return PermissionLevel.DEFAULT;
		}
		else if(player.hasPermission(admin) && !player.hasPermission(def) && !player.hasPermission(all)) {
			return PermissionLevel.ADMIN;
		}
		else if(player.hasPermission(all) && !player.hasPermission(admin) && !player.hasPermission(def)) {
			return PermissionLevel.ALL;
		} else {
			return PermissionLevel.DEFAULT;
		}
	}
	
	public static PermissionLevel getLevel(OfflinePlayer player) {
		if(player.getPlayer().hasPermission(def) && !player.getPlayer().hasPermission(admin) && !player.getPlayer().hasPermission(all)) {
			return PermissionLevel.DEFAULT;
		}
		else if(player.getPlayer().hasPermission(admin) && !player.getPlayer().hasPermission(def) && !player.getPlayer().hasPermission(all)) {
			return PermissionLevel.ADMIN;
		}
		else if(player.getPlayer().hasPermission(all) && !player.getPlayer().hasPermission(admin) && !player.getPlayer().hasPermission(def)) {
			return PermissionLevel.ALL;
		} else {
			return PermissionLevel.DEFAULT;
		}
	}
	
	public enum PermissionLevel {
		ALL, ADMIN, DEFAULT
	}
	
}
