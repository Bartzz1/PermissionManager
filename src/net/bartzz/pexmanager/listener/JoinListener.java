package net.bartzz.pexmanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.bartzz.pexmanager.data.PermissionPlayer;

public class JoinListener implements Listener {
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		PermissionPlayer pPlayer = PermissionPlayer.getPermissionPlayer(player.getUniqueId());
		
		if(pPlayer == null) {
			
			pPlayer = new PermissionPlayer(player.getUniqueId(), player.getName(), null, null);
		}
	}
}
