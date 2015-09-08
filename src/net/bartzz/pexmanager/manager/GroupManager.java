package net.bartzz.pexmanager.manager;

import java.util.ArrayList;
import java.util.Collection;

import net.bartzz.pexmanager.data.Group;
import net.bartzz.pexmanager.data.PermissionPlayer;

public class GroupManager {
	
	private static Collection<Group> groups;
	
	public static Collection<Group> getGroups() {
		
		return groups;
	}
	
	public static void createGroup(String name, PermissionPlayer player) {
		Group group = new Group(name, "", new ArrayList<PermissionPlayer>(), new ArrayList<String>());
		group.addPlayer(player);
		GroupManager.getGroups().add(group);
	}
	
	public static void deleteGroup(String name) {
		Group group = Group.getGroup(name);
		if(group == null) {
			return;
		}
		
		group.delete();
		GroupManager.getGroups().remove(group);
	}
}
