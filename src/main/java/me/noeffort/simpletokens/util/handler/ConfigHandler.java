package me.noeffort.simpletokens.util.handler;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.noeffort.enhancedenchanter.util.MessageUtil;
import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.config.MessagesConfig;
import me.noeffort.simpletokens.util.config.OptionsConfig;
import me.noeffort.simpletokens.util.config.PlayerConfig;

public class ConfigHandler {

	Main plugin = Main.get();
	
	PlayerConfig player;
	MessagesConfig messages;
	OptionsConfig options;
	
	public ConfigHandler() {
		player = plugin.getPlayerConfig();
		messages = plugin.getMessageConfig();
		options = plugin.getOptionsConfig();
		registerPaths();
		registerValues();
	}
	
	//VALUES
	public String prefix;
	public String reload;
	public String reloadSuccess;
	public String reloadFailure;
	public String give;
	public String giveOthers;
	public String giveReceive;
	public String balance;
	public String balanceOthers;
	public String balanceTop;
	public String help;
	public String missingTarget;
	public String offlineTarget;
	public String missingPermission;
	public String missingCommand;
	public String missingAmount;
	public String invalidInt;
	public String invalidUsage;
	public String reset;
	public String resetOthers;
	public String tooManyArgs;
	public String negativeTokens;
	public String cantAffordTokens;
	public String redeemTokens;
	public String error;
	public String debug;
	
	private String playerUUID;
	private String targetUUID;
	
	public String playerName;
	public String targetName;
	public String playerTokens;
	public String targetTokens;
	public String playerTime;
	public String targetTime;
	
	public String delayTime;
	public String delayEnabled;
	public String delayAmount;
	public String debugEnabled;
	
	//PATHS
	public String prefixPath;
	public String reloadPath;
	public String reloadSuccessPath;
	public String reloadFailurePath;
	public String givePath;
	public String giveOthersPath;
	public String giveReceivePath;
	public String balancePath;
	public String balanceOthersPath;
	public String balanceTopPath;
	public String helpPath;
	public String missingTargetPath;
	public String offlineTargetPath;
	public String missingPermissionPath;
	public String missingCommandPath;
	public String missingAmountPath;
	public String invalidIntPath;
	public String invalidUsagePath;
	public String resetPath;
	public String resetOthersPath;
	public String tooManyArgsPath;
	public String negativeTokensPath;
	public String cantAffordTokensPath;
	public String redeemTokensPath;
	public String errorPath;
	public String debugPath;
	
	public String playerPath;
	public String targetPath;
	public String playerNamePath;
	public String targetNamePath;
	public String playerTokensPath;
	public String targetTokensPath;
	
	public String playerTimePath;
	public String targetTimePath;
	
	public String delayTimePath;
	public String delayEnabledPath;
	public String delayAmountPath;
	public String debugEnabledPath;
	
	public void registerValues() {
		prefix = messages.getConfig().getString(prefixPath);
		reload = messages.getConfig().getString(reloadPath);
		reloadSuccess = messages.getConfig().getString(reloadSuccessPath);
		reloadFailure = messages.getConfig().getString(reloadFailurePath);
		give = messages.getConfig().getString(givePath);
		giveOthers = messages.getConfig().getString(giveOthersPath);
		giveReceive = messages.getConfig().getString(giveReceivePath);
		balance = messages.getConfig().getString(balancePath);
		balanceOthers = messages.getConfig().getString(balanceOthersPath);
		balanceTop = messages.getConfig().getString(balanceTopPath);
		help = messages.getConfig().getString(helpPath);
		missingTarget = messages.getConfig().getString(missingTargetPath);
		offlineTarget = messages.getConfig().getString(offlineTargetPath);
		missingPermission = messages.getConfig().getString(missingPermissionPath);
		missingCommand = messages.getConfig().getString(missingCommandPath);
		missingAmount = messages.getConfig().getString(missingAmountPath);
		invalidInt = messages.getConfig().getString(invalidIntPath);
		invalidUsage = messages.getConfig().getString(invalidUsagePath);
		reset = messages.getConfig().getString(resetPath);
		resetOthers = messages.getConfig().getString(resetOthersPath);
		tooManyArgs = messages.getConfig().getString(tooManyArgsPath);
		negativeTokens = messages.getConfig().getString(negativeTokensPath);
		cantAffordTokens = messages.getConfig().getString(cantAffordTokensPath);
		redeemTokens = messages.getConfig().getString(redeemTokensPath);
		error = messages.getConfig().getString(errorPath);
		debug = messages.getConfig().getString(debugPath);
		
		delayTime = options.getConfig().getString(delayTimePath);
		delayEnabled = options.getConfig().getString(delayEnabledPath);
		delayAmount = options.getConfig().getString(delayAmountPath);
		debugEnabled = options.getConfig().getString(debugEnabledPath);
	}
	
	public void registerPaths() {
		prefixPath = "messages.prefix";
		reloadPath = "messages.reload.general";
		reloadSuccessPath = "messages.reload.success";
		reloadFailurePath = "messages.reload.failure";
		givePath = "messages.give.self";
		giveOthersPath = "messages.give.others";
		giveReceivePath = "messages.give.receive";
		balancePath = "messages.balance.self";
		balanceOthersPath = "messages.balance.others";
		balanceTopPath = "messages.balance.top";
		helpPath = "messages.help";
		missingTargetPath = "messages.missing.target";
		offlineTargetPath = "messages.offline.target";
		missingPermissionPath = "messages.missing.permission";
		missingCommandPath = "messages.missing.command";
		missingAmountPath = "messages.missing.amount";
		invalidIntPath = "messages.missing.int";
		invalidUsagePath = "messages.missing.usage";
		resetPath = "messages.reset.self";
		resetOthersPath = "messages.reset.others";
		tooManyArgsPath = "messages.values.over";
		negativeTokensPath = "messages.tokens.negative";
		cantAffordTokensPath = "messages.tokens.afford";
		redeemTokensPath = "messages.tokens.redeem";
		errorPath = "messages.error";
		debugPath = "messages.debug.enabled";
		
		playerPath = "players.%UUID";
		targetPath = "players.%ID";
		playerNamePath = "players.%UUID.playerName";
		targetNamePath = "players.%ID.playerName";
		playerTokensPath = "players.%UUID.tokens";
		targetTokensPath = "players.%ID.tokens";
		playerTimePath = "players.%UUID.timePlayed";
		targetTimePath = "players.%ID.timePlayed";
		
		delayTimePath = "options.delay.time";
		delayEnabledPath = "options.delay.enabled";
		delayAmountPath = "options.give.amount";
		debugEnabledPath = "options.debug.enabled";
		
	}
	
	public HashSet<String> getMessages() {
		
		HashSet<String> set = new HashSet<String>();
		
		messages.reloadConfig();
		for(String key : messages.getConfig().getConfigurationSection("messages").getKeys(false)) {
			set.add(key);
		}
		
		return set;
	}
	
	public void assignPlayer(Player player) {
		playerUUID = player.getUniqueId().toString();
		playerName = this.player.getConfig().getString("players." + playerUUID + ".playerName");
		playerTokens = this.player.getConfig().getString("players." + playerUUID + ".tokens");
		playerTime = this.player.getConfig().getString("players." + playerUUID + ".timePlayed");
	}
	
	public void assignTarget(Player player) {
		targetUUID = player.getUniqueId().toString();
		targetName = this.player.getConfig().getString("players." + targetUUID + ".playerName");
		targetTokens = this.player.getConfig().getString("players." + targetUUID + ".tokens");
		targetTime = this.player.getConfig().getString("players." + targetUUID + ".timePlayed");
	}
	
	public HashSet<String> getPaths() {
		
		HashSet<String> paths = new HashSet<String>();
		paths.add(prefixPath);
		paths.add(reloadPath);
		paths.add(reloadSuccessPath);
		paths.add(reloadFailurePath);
		paths.add(givePath);
		paths.add(giveOthersPath);
		paths.add(giveReceivePath);
		paths.add(balancePath);
		paths.add(balanceOthersPath);
		paths.add(balanceTopPath);
		paths.add(helpPath);
		paths.add(missingTargetPath);
		paths.add(offlineTargetPath);
		paths.add(missingPermissionPath);
		paths.add(missingCommandPath);
		paths.add(missingAmountPath);
		paths.add(invalidIntPath);
		paths.add(invalidUsagePath);
		paths.add(resetPath);
		paths.add(resetOthersPath);
		paths.add(tooManyArgsPath);
		paths.add(negativeTokensPath);
		paths.add(cantAffordTokensPath);
		paths.add(redeemTokensPath);
		paths.add(errorPath);
		paths.add(debugPath);
		
		paths.add(replaceString(playerNamePath));
		paths.add(replaceString(playerTokensPath));
		paths.add(replaceString(targetNamePath));
		paths.add(replaceString(targetTokensPath));
		paths.add(replaceString(playerTimePath));
		paths.add(replaceString(targetTimePath));
		
		paths.add(delayTimePath);
		paths.add(delayEnabledPath);
		paths.add(delayAmountPath);
		paths.add(debugEnabledPath);
		
		return paths;
		
	}
	
	public String replaceString(String value) {
		
		String replaced = "";
		
		for(Keyword key : Keyword.values()) {
			switch(key) {
			case TOKENS:
				if(value.contains("%TOKENS")) {
					value = value.replaceAll("%TOKENS", Integer.toString(getTokens()));
					replaced = MessageUtil.translate(value);
				}
			case PLAYER:
				if(value.contains("%PLAYER")) {
					value = value.replaceAll("%PLAYER", playerName);
					replaced = MessageUtil.translate(value);
				}
			case TARGET:
				if(value.contains("%TARGET")) {
					value = value.replaceAll("%TARGET", targetName);
					replaced = MessageUtil.translate(value);
				}
			case PLAYER_BALANCE:
				if(value.contains("%BAL")) {
					value = value.replaceAll("%BAL", playerTokens);
					replaced = MessageUtil.translate(value);
				}
			case TARGET_BALANCE:
				if(value.contains("%USERBAL")) {
					value = value.replaceAll("%USERBAL", targetTokens);
					replaced = MessageUtil.translate(value);
				}
			case PLAYER_UUID:
				if(value.contains("%UUID")) {
					value = value.replaceAll("%UUID", playerUUID);
					replaced = MessageUtil.translate(value);
				}
			case TARGET_UUID:
				if(value.contains("%ID")) {
					value = value.replaceAll("%ID", targetUUID);
					replaced = MessageUtil.translate(value);
				}
			default:
				return MessageUtil.translate(value);
			}
		}
		return replaced;
	}
	
	public String appendPrefix(String string) {
		registerValues();
		return MessageUtil.translate(prefix + replaceString(string));
	}
	
	public void displayBaltop(CommandSender sender) {
		
		player.reloadConfig();
		
		HashMap<UUID, Integer> map = new HashMap<UUID, Integer>();
		ValueComparator compare = new ValueComparator(map);
		TreeMap<UUID, Integer> sorted = new TreeMap<UUID, Integer>(compare);
		
		for(UUID id : getPlayers()) {
			int value = player.getConfig().getInt("players." + id + ".tokens");
			map.put(id, value);
		}
		
		sorted.putAll(map);
		
		int i = 1;
		
		for(Entry<UUID, Integer> entry : sorted.entrySet()) {
			sender.sendMessage(MessageUtil.translate("&6" + i + "/ &e" + Bukkit.getOfflinePlayer(entry.getKey()).getName()) + " : " + entry.getValue());
			i++;
			
			if(i == 10) {
				break;
			}
		}
	}
	
	private enum Keyword {
		TOKENS, PLAYER, TARGET, PLAYER_BALANCE, TARGET_BALANCE, PLAYER_UUID, TARGET_UUID
	}
	
	private int tokens;
	
	private int getTokens() {
		return tokens;
	}
	
	public void setTokens(int i) {
		tokens = i;
	}
	
	public UUID[] getPlayers() {
		
		UUID[] ids = {};
		
		for(String key : player.getConfig().getConfigurationSection("players").getKeys(false)) {
			int size = ids.length;
			int update = size + 1;
			
			UUID id = UUID.fromString(key);
			UUID[] temp = new UUID[update];
			
			for(int i = 0; i < size; i++) {
				temp[i] = ids[i];
			}
			
			temp[update - 1] = id;
			ids = temp;
		}
		
		return ids;
	}
	
}

class ValueComparator implements Comparator<UUID> {
	
	Map<UUID, Integer> base;
	public ValueComparator(Map<UUID, Integer> base) {
		this.base = base;
	}
	
	public int compare(UUID a, UUID b) {
		if(base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}
	
}
