package net.bartzz.pexmanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.bartzz.pexmanager.data.PermissionPlayer;
import net.bartzz.pexmanager.util.UtilChat;

public class ChatListener implements Listener {
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player player = e.getPlayer();
		PermissionPlayer pPlayer = PermissionPlayer.getPermissionPlayer(player.getUniqueId());
		
		if(pPlayer == null) {
			return;
		}
		
		if(pPlayer.getGroup() == null) {
			return;
		}
		
		if(pPlayer.getGroup().getGroupPrefix() == null) {
			return;
		}
		
		String message = e.getMessage();
		
		e.setFormat(UtilChat.fixColors("" + pPlayer.getGroup().getGroupPrefix() + " " + player.getName() + ": " + message));
		
		e.setMessage("" + message);
	}

}
