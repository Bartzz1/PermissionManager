package net.bartzz.pexmanager.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilChat {
	
	public static String fixColors(String text) {
		
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static void message(CommandSender sender, String text) {
		
		if(text.isEmpty()) {
			return;
		}
		
		sender.sendMessage(fixColors(text));
	}
	
	public static void broadcast(String text) {
		
		if(text.isEmpty()) {
			return;
		}
		
		Bukkit.getOnlinePlayers().forEach(player -> message(player, text));
	}
	
	public static void broadcast(String text, String permission) {
		
		if(text.isEmpty() || permission.isEmpty()) {
			return;
		}
		
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(!online.hasPermission(permission)) {
				return;
			}
			
			message(online, text);
		}
	}
}
