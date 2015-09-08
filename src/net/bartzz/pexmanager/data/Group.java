package net.bartzz.pexmanager.data;

import java.util.Collection;

import net.bartzz.pexmanager.Main;
import net.bartzz.pexmanager.manager.GroupManager;

public class Group {
	
	private String 	name;
	private String	groupPrefix;
	private Collection<PermissionPlayer> permissionPlayers;
	private Collection<String> permissions;
	
	public Group(String name, String groupPrefix, Collection<PermissionPlayer> permissionPlayers, Collection<String> permissions) {
		
		this.name = name;
		this.groupPrefix = groupPrefix;
		this.permissionPlayers = permissionPlayers;
		this.permissions = permissions;
	}
	
	public static Group getGroup(String name) {
		for(Group group : GroupManager.getGroups()) {
			if(group.getName().equalsIgnoreCase(name)) {
				return group;
			}
		}
		return null;
	}
	
	public void insert() {
		Main.getMCDatabase().update(true, "insert into permission_groups (name, groupPrefix) values ('" + this.name + "', '" + this.groupPrefix + "'");
	}
	
	public void update(boolean now) {
		Main.getMCDatabase().update(now, "update permission_groups set groupPrefix='" + this.groupPrefix + "' where name='" + this.name + "'");
	}
	
	public void delete() {
		Main.getMCDatabase().update(true, "delete from permission_groups where name='" + this.name + "'");
	}
	
	public void addPlayer(PermissionPlayer player) {
		if(this.permissionPlayers.contains(player)) {
			return;
		}
		
		this.permissionPlayers.add(player);
		Main.getMCDatabase().update(true, "insert into permission_groups_players (name, nick) values ('" + this.name + "', '" + player.getNick() + "')");
	}
	
	public void removePlayer(PermissionPlayer player) {
		if(!this.permissionPlayers.contains(player)) {
			return;
		}
		
		this.permissionPlayers.remove(player);
		Main.getMCDatabase().update(true, "delete from permission_groups_players where nick='" + player.getNick() + "'");
	}
	
	public void addPermission(String permission) {
		if(this.permissions.contains(permission)) {
			return;
		}
		
		this.permissions.add(permission);
		Main.getMCDatabase().update(true, "insert into permission_groups_per (name, permission) values ('" + this.name + "', '" + permission + "')");
	}
	
	public void removePermission(String permission) {
		if(!this.permissions.contains(permission)) {
			return;
		}
		
		this.permissions.remove(permission);
		Main.getMCDatabase().update(true, "delete from permission_group_per where permission='" + permission + "'");
	}
	
	public String getName() {
		return name;
	}
	
	public String getGroupPrefix() {
		return groupPrefix;
	}
	
	public Collection<PermissionPlayer> getPermissionPlayers() {
		return permissionPlayers;
	}
	
	public Collection<String> getPermissions() {
		return permissions;
	}
	
	public void setGroupPrefix(String groupPrefix) {
		this.groupPrefix = groupPrefix;
	}
	
	public void setPermissionPlayers(Collection<PermissionPlayer> permissionPlayers) {
		this.permissionPlayers = permissionPlayers;
	}
	
	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
	}
}
