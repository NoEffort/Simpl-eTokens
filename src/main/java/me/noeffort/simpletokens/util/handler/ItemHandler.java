package me.noeffort.simpletokens.util.handler;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.noeffort.enhancedenchanter.util.MessageUtil;
import me.noeffort.simpletokens.Main;
import me.noeffort.simpletokens.util.config.PlayerConfig;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class ItemHandler implements Listener {

	Main plugin = Main.get();
	
	PlayerConfig config;
	ConfigHandler handler;
	
	public ItemHandler() {}
	
	private static ItemStack stack;
	
	public ItemStack giveTokens(Player player, int amount) {
		
		ItemStack item = new ItemStack(Material.MAGMA_CREAM, 1);
		
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(MessageUtil.translate("&eRedeemed tokens: " + amount));
		lore.add(MessageUtil.translate("&8Right-Click to redeem tokens!"));
		meta.setDisplayName(MessageUtil.translate("&6Tokens"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		compound.set("Token", new NBTTagString("item.isToken"));
		compound.set("Amount", new NBTTagInt(amount));
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		
		stack = item;
		
		return stack;
	}
	
	public ItemStack getItem() {
		return stack;
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				EquipmentSlot hand = event.getHand();
				if(hand.equals(EquipmentSlot.HAND)) {
					ItemStack mainhand = player.getInventory().getItemInMainHand();					
					if(mainhand == null || mainhand.getType() == Material.AIR) {
						return;
					} else {
						net.minecraft.server.v1_12_R1.ItemStack nbtMainhand = CraftItemStack.asNMSCopy(mainhand);
						if(nbtMainhand.hasTag() && nbtMainhand.getTag().hasKey("Token")) {
							config = plugin.getPlayerConfig();
							handler = plugin.getConfigHandler();
							
							handler.assignPlayer(player);
							
							handler.setTokens(nbtMainhand.getTag().getInt("Amount"));
							int tokensBalPlayer = Integer.parseInt(handler.replaceString(handler.playerTokens));
							if(config.getConfig().isSet(handler.replaceString(handler.playerTokensPath))) {
								int temp = 0;
								temp = tokensBalPlayer + nbtMainhand.getTag().getInt("Amount");
								config.getConfig().set(handler.replaceString(handler.playerTokensPath), temp);
								temp = 0;
								player.getInventory().removeItem(mainhand);
								player.sendMessage(handler.appendPrefix(handler.redeemTokens));
								config.saveConfig();
								config.reloadConfig();
								event.setCancelled(true);
								return;
							}
						} else {
							return;
						}
					}
				} else {
					return;
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}
	
}
