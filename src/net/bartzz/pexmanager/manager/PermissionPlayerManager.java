package net.bartzz.pexmanager.manager;

import java.util.Collection;

import net.bartzz.pexmanager.data.PermissionPlayer;

public class PermissionPlayerManager {
	
	private static Collection<PermissionPlayer> permissionPlayers;
	
	public static Collection<PermissionPlayer> getPermissionPlayers() {
		
		return permissionPlayers;
	}

}
