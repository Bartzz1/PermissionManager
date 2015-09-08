package net.bartzz.pexmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.bartzz.pexmanager.listener.ChatListener;
import net.bartzz.pexmanager.listener.JoinListener;
import net.bartzz.pexmanager.sql.MCDatabase;

public class Main extends JavaPlugin {
	
	private static Main inst;
	private static MCDatabase mcdatabase;
	
	public Main() {
		
		inst = this;
	}

	public void onEnable() {
		
		this.registerListeners();
	}
	
	public static Main getInstance() {
		
		return inst;
	}
	
	public static MCDatabase getMCDatabase() {
		
		return mcdatabase;
	}
	
	private void registerListeners() {
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new ChatListener(), this);
	}
}
