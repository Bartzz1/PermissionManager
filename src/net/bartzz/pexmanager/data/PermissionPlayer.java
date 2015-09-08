package net.bartzz.pexmanager.data;

import java.util.Collection;
import java.util.UUID;

import net.bartzz.pexmanager.Main;
import net.bartzz.pexmanager.manager.PermissionPlayerManager;

public class PermissionPlayer {
	
	private UUID 	uuid;
	private String 	nick;
	private Group 	group;
	private Collection<String> permissions;
	
	public PermissionPlayer(UUID uuid, String nick, Group group, Collection<String> permissions) {
		
		this.uuid = uuid;
		this.nick = nick;
		this.group = group;
		this.permissions = permissions;
	}
	
	public static PermissionPlayer getPermissionPlayer(UUID uuid) {
		for(PermissionPlayer player : PermissionPlayerManager.getPermissionPlayers()) {
			if(player.getUuid().equals(uuid)) {
				return player;
			}
		}
		return null;
	}
	
	public void insert() {
		Main.getMCDatabase().update(true, "insert into permission_players (uuid, nick, group) values ('" + this.uuid + "', '" + this.nick + "', '" + this.group.toString() + "')");
	}
	
	public void update(boolean now) {
		Main.getMCDatabase().update(now, "update permission_players set nick='" + this.nick + "', group='" + this.group.getName() + "' where uuid='" + this.uuid + "'");
	}
	
	public void delete() {
		Main.getMCDatabase().update(true, "delete from permission_players where uuid='" + this.uuid + "'");
	}
	
	public void addPermission(String permission) {
		if(this.permissions.contains(permission)) {
			return;
		}
		
		this.permissions.add(permission);
		Main.getMCDatabase().update(true, "insert into permission (permission, nick) values ('" + permission + "', '" + this.nick + "')");
	}

	public void removePermission(String permission) {
		if(!this.permissions.contains(permission)) {
			return;
		}
		
		this.permissions.remove(permission);
		Main.getMCDatabase().update(true, "delete from permission where permission='" + permission + "'");
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public String getNick() {
		return nick;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public Collection<String> getPermissions() {
		return permissions;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
	}
}
