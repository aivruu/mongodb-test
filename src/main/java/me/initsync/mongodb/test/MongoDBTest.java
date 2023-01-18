package me.initsync.mongodb.test;

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
	
	/**
	 * Returns the MongoDBTest instance.
	 *
	 * @return The MongoDBTest instance. Can return null if the field isn't initialized.
	 */
	public static MongoDBTest getPlugin() {
		return plugin;
	}
	
	/**
	 * Returns the 'release' field.
	 *
	 * @return An String.
	 */
	public String getRelease() {
		return release;
	}
	
	/**
	 * Returns the ProfileManager instance.
	 *
	 * @return The ProfileManager instance. Can return null if the field isn't initialized.
	 */
	public ProfileManager getProfileManager() {
		return profileManager;
	}
	
	/**
	 * Returns the DatabaseManager instance.
	 *
	 * @return An DatabaseManager instance. Can return null if the field isn't initialized.
	 */
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
			getLogger().info("Saving players data");
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				profileManager.getProfile(player.getUniqueId())
					 .getUser()
					 .saveData();
			}
		}
		
		if (databaseManager != null) databaseManager = null;
	}
}
