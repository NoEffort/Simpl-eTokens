package me.noeffort.simpletokens.command;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.MessageUtil;
import me.noeffort.simpletokens.util.config.MessagesConfig;
import me.noeffort.simpletokens.util.config.OptionsConfig;
import me.noeffort.simpletokens.util.config.PlayerConfig;
import me.noeffort.simpletokens.util.handler.ConfigHandler;
import me.noeffort.simpletokens.util.handler.ItemHandler;
import me.noeffort.simpletokens.util.handler.Permissions;
import me.noeffort.simpletokens.util.handler.Permissions.PermissionLevel;
import me.noeffort.simpletokens.util.handler.PlayerFileHandler;

public class TokenCommand implements CommandExecutor {

	Main plugin = Main.get();
	MessageUtil msg = new MessageUtil();
	Permissions perms = new Permissions();
	
	PlayerConfig config;
	MessagesConfig messages;
	OptionsConfig options;
	ConfigHandler handler;
	PlayerFileHandler fileHandler;
	ItemHandler item;
	
	public TokenCommand() {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;

			config = plugin.getPlayerConfig();
			messages = plugin.getMessageConfig();
			options = plugin.getOptionsConfig();
			handler = plugin.getConfigHandler();
			fileHandler = plugin.getFileHandler();
			
			if(command.getName().equalsIgnoreCase("token")) {	
				handler.assignPlayer(player);
				if(perms.has(player, PermissionLevel.DEFAULT)) {
					if(args.length >= 1) {
						switch(args[0].toString()) {
						case "bal":
							if(args.length == 2) {
								for(String key : config.getConfig().getConfigurationSection("players").getKeys(false)) {
									UUID id = UUID.fromString(key);
									if(isPlayerOnline(args[1], id)) {
										Player target = getOnlinePlayer(args[1], id);
										handler.assignTarget(target);
										player.sendMessage(handler.appendPrefix(handler.balanceOthers));
										return true;
									} else {
										player.sendMessage(handler.appendPrefix(handler.offlineTarget));
									}
									return true;
								}
							} else {
								player.sendMessage(prefix(handler.balance));
								return true;
							}
						case "baltop":
							player.sendMessage(prefix(handler.balanceTop));
							handler.displayBaltop(sender);
							return true;
						case "give":
							if(args.length >= 2) {
								if(!perms.has(player, PermissionLevel.ADMIN)) {
									player.sendMessage(prefix(handler.missingPermission));
									return true;
								} else {
									if(isInt(args[1])) {
										giveTokens(player, null, Integer.parseInt(args[1]));
										return true;
									} else {
										if(args.length == 3) {
											for(String key : config.getConfig().getConfigurationSection("players").getKeys(false)) {
												UUID id = UUID.fromString(key);
												if(isPlayerOnline(args[1], id)) {
													Player target = getOnlinePlayer(args[1], id);
													if(isInt(args[2]) && !isInt(args[1])) {
														giveTokens(player, target, Integer.parseInt(args[2]));
														return true;
													} else {
														player.sendMessage(prefix(handler.invalidInt));
														return true;
													}
												} else {
													player.sendMessage(prefix(handler.offlineTarget));
												}
												return true;
											}
										} else {
											player.sendMessage(prefix(handler.missingAmount));
											return true;
										}
									}
								}
							} else {
								player.sendMessage(prefix(handler.missingTarget));
								return true;
							}
						case "help":
							if(perms.has(player, PermissionLevel.ALL)) {
								player.sendMessage(handler.appendPrefix(handler.help));
								msg.displayAllCommands(player);
								return true;
							}
							else if(perms.has(player, PermissionLevel.ADMIN)) {
								player.sendMessage(handler.appendPrefix(handler.help));
								msg.displayAdminCommands(player);
								return true;
							} else {
								player.sendMessage(handler.appendPrefix(handler.help));
								msg.displayDefaultCommands(player);
								return true;
							}
						case "?":
							Bukkit.getServer().dispatchCommand(player, "token help");
							return true;
						case "redeem":
							if(args.length == 2) {
								if(!args[0].isEmpty() && isInt(args[1])) {
									redeemTokens(player, Integer.parseInt(args[1]));
									return true;
								} else {
									player.sendMessage(handler.appendPrefix(handler.invalidInt));
									return true;
								}
							} else {
								player.sendMessage(prefix(handler.missingAmount));
								return true;
							}
						case "debug":
							if(options.ifDebugEnabled()) {
								if(!perms.has(player, PermissionLevel.ADMIN)) {
									player.sendMessage(handler.appendPrefix(handler.missingPermission));
									return true;
								} else {
									player.sendMessage(handler.appendPrefix("&7DEBUG MODE:"));
									player.sendMessage(msg.translate("&8Nothing to display..."));
									player.sendMessage(msg.translate("&aPermission Level: " + Permissions.getLevel(player)));
									return true;
								}
							} else {
								player.sendMessage(handler.appendPrefix(handler.debug));
								return true;
							}
						case "stop":
							if(options.ifDebugEnabled()) {
								if(!perms.has(player, PermissionLevel.ALL)) {
									player.sendMessage(handler.appendPrefix(handler.missingPermission));
									return true;
								} else {
									fileHandler.stopTask(fileHandler.getTaskID());
									options.getConfig().set(handler.delayEnabledPath, false);
									options.reloadConfig();
									player.sendMessage(handler.appendPrefix("&cToken counter has forcibly been stopped!"));
									return true;
								}
							} else {
								player.sendMessage(handler.appendPrefix(handler.debug));
								return true;
							}
						case "start":
							if(options.ifDebugEnabled()) {
								if(!perms.has(player, PermissionLevel.ALL)) {
									player.sendMessage(handler.appendPrefix(handler.missingPermission));
									return true;
								} else {
									fileHandler.checkTokens();
									options.getConfig().set(handler.delayEnabledPath, true);
									options.reloadConfig();
									player.sendMessage(handler.appendPrefix("&aToken counter has been resumed!"));
									return true;
								}
							} else {
								player.sendMessage(handler.appendPrefix(handler.debug));
								return true;
							}
						case "reload":
							if(!perms.has(player, PermissionLevel.ALL)) {
								player.sendMessage(handler.appendPrefix(handler.missingPermission));
								return true;
							} else {
								player.sendMessage(handler.appendPrefix(handler.reload));
								try {
									config.reloadConfig();
									messages.reloadConfig();
									options.reloadConfig();
									fileHandler.reloadTokenCounter();
									player.sendMessage(handler.appendPrefix(handler.reloadSuccess));
									return true;
								} catch (Exception e) {
									player.sendMessage(handler.appendPrefix(handler.reloadFailure));
									return true;
								}
							}
						case "reset":
							if(args.length == 2) {
								for(String key : config.getConfig().getConfigurationSection("players").getKeys(false)) {
									UUID id = UUID.fromString(key);
									if(isPlayerOnline(args[1], id)) {
										Player target = getOnlinePlayer(args[1], id);
										resetTokens(player, target);
										return true;
									} else {
										player.sendMessage(handler.appendPrefix(handler.offlineTarget));
									}
									return true;
								}
							} else {
								resetTokens(player, null);
								return true;
							}
						default:
							player.sendMessage(handler.appendPrefix(handler.missingCommand));
							return true;
						}
					}
				} else {
					player.sendMessage(prefix(handler.missingPermission));
					return true;
				}
			} else {
				return true;
			}
		}
		return true;
	}
	
    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isPlayerOnline(String name, UUID id) {
    	Collection<? extends Player> list = Bukkit.getOnlinePlayers();
    	for(Player p : list) {
    		if(p.getUniqueId().equals(id) && p.getName().equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private Player getOnlinePlayer(String name, UUID id) {
    	if(Bukkit.getServer().getPlayer(id) != null && Bukkit.getServer().getPlayer(id).getName().equals(name)) {
    		return Bukkit.getServer().getPlayer(id);
    	}
    	return null;
    }
    
    private String prefix(String message) {
    	handler = plugin.getConfigHandler();
    	return handler.appendPrefix(message);
    }
    
    private void giveTokens(Player player, Player target, int amount) {
    	
    	handler = plugin.getConfigHandler();
    	config = plugin.getPlayerConfig();
    	
    	handler.assignPlayer(player);
    	handler.setTokens(amount);
    	
    	int playerBal = Integer.parseInt(handler.replaceString(handler.playerTokens));
    	
		if(target == null) {
	    	if(perms.has(player, PermissionLevel.ADMIN)) {
	    		if(amount < 0) {
	    			player.sendMessage(prefix(handler.negativeTokens));
	    			return;
	    		}
	    		config.getConfig().set(handler.replaceString(handler.playerTokensPath), playerBal + amount);
	    		player.sendMessage(prefix(handler.give));
	    		config.saveConfig();
	    		config.reloadConfig();
	    		return;
	    	} else {
	    		player.sendMessage(prefix(handler.missingPermission));
	    		return;
	    	}
		} else {
			handler.assignTarget(target);
			int targetBal = Integer.parseInt(handler.replaceString(handler.targetTokens));
			
    		if(amount < 0) {
    			player.sendMessage(prefix(handler.negativeTokens));
    			return;
    		}
    		if(amount > playerBal) {
    			player.sendMessage(prefix(handler.cantAffordTokens));
    			return;
    		}
    		if(handler.playerName.equals(handler.targetName)) {
    			player.sendMessage(prefix("&7Please use \"&e/token give <amount>&7\"to give tokens to yourself!"));
    			return;
    		}
    		config.getConfig().set(handler.replaceString(handler.playerTokensPath), playerBal - amount);
    		player.sendMessage(prefix(handler.giveOthers));
    		config.getConfig().set(handler.replaceString(handler.targetTokensPath), targetBal + amount);
    		target.sendMessage(prefix(handler.giveReceive));
    		config.saveConfig();
    		config.reloadConfig();
    		return;
		}
    }
    
    private void redeemTokens(Player player, int amount) {
    	
    	handler = plugin.getConfigHandler();
    	config = plugin.getPlayerConfig();
    	item = plugin.getItemHandler();
    	
    	handler.assignPlayer(player);
    	handler.setTokens(amount);
    	
    	int playerBal = Integer.parseInt(handler.replaceString(handler.playerTokens));
    	
    	if(amount < 0) {
    		player.sendMessage(prefix(handler.negativeTokens));
    		return;
    	}
    	if(amount > playerBal) {
    		player.sendMessage(prefix(handler.cantAffordTokens));
    		return;
    	}
    	config.getConfig().set(handler.replaceString(handler.playerTokensPath), playerBal - amount);
    	player.getInventory().addItem(item.giveTokens(player, amount));
    	player.sendMessage(prefix(handler.redeemTokens));
    	config.saveConfig();
    	config.reloadConfig();
    	return;
    }
    
    private void resetTokens(Player player, Player target) {
    	
    	handler = plugin.getConfigHandler();
    	config = plugin.getPlayerConfig();
    	
    	handler.assignPlayer(player);
		
		if(target == null) {
			config.getConfig().set(handler.replaceString(handler.playerTokensPath), 0);
			config.saveConfig();
			config.reloadConfig();
			player.sendMessage(prefix(handler.reset));
			return;
		} else {
			handler.assignTarget(target);
			
			if(handler.playerName.equals(handler.targetName)) {
				player.sendMessage(handler.appendPrefix("&7Use \"&e/token reset&7\" to reset your own tokens."));
				return;
			}
			if(perms.has(player, PermissionLevel.ADMIN)) {
				config.getConfig().set(handler.replaceString(handler.targetTokensPath), 0);
				config.saveConfig();
				config.reloadConfig();
				player.sendMessage(prefix(handler.reset));
				target.sendMessage(prefix(handler.resetOthers));
				return;
			} else {
				player.sendMessage(prefix(handler.missingPermission));
				return;
			}
		}
    }
}
