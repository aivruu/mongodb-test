package me.initsync.mongodb.test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.initsync.mongodb.test.api.model.database.DatabaseManager;
import me.initsync.mongodb.test.api.model.profile.ProfileManager;
import me.initsync.mongodb.test.listeners.PlayerListener;
import me.initsync.mongodb.test.model.SimpleDatabaseManager;
import me.initsync.mongodb.test.model.SimpleProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MongoDBTest extends JavaPlugin {
	private static MongoDBTest plugin;
	
	private final String release = getDescription().getVersion();
	
	private ProfileManager profileManager;
	private DatabaseManager databaseManager;
	
	public String getRelease() {
		return release;
	}
	
	public static MongoDBTest getPlugin() {
		return plugin;
	}
	
	public ProfileManager getProfileManager() {
		return profileManager;
	}
	
	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	@Override
	public void onLoad() {
		plugin = this;
		
		profileManager = new SimpleProfileManager();
		databaseManager = new SimpleDatabaseManager();
	}
	
	@Override
	public void onEnable() {
		databaseManager.setDatabaseProperties();
		
		if (databaseManager.establishConnection()) getLogger().info("MongoDB connection established successful!");
		
		getServer().getPluginManager().registerEvents(new PlayerListener(profileManager), this);
	}
	
	@Override
	public void onDisable() {
		if (profileManager != null) {
			// Can that this kills the server :8
			for (Player player : Bukkit.getOnlinePlayers()) {
				profileManager.getProfile(player.getUniqueId())
					 .getUser()
					 .saveData();
			}
			
			getLogger().info("Saved players data successful!");
		}
		
		if (databaseManager != null) databaseManager = null;
	}
}
