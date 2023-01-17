package me.initsync.mongodb.test.api.model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.initsync.mongodb.test.MongoDBTest;
import org.bson.Document;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserModel {
	private final MongoCollection<Document> data = MongoDBTest.getPlugin()
		 .getDatabaseManager()
		 .getDataCollection();
	private final UUID uuid;
	private final String name;
	
	private int kills;
	private int level;
	
	public UserModel(UUID uuid, String name) {
		this.uuid = Objects.requireNonNull(uuid, "The uuid is null.");
		this.name = Objects.requireNonNull(name, "The name is null.");
	}
	
	/**
	 * Simple method to reset the user statistics.
	 */
	public void resetStatistics() {
		kills = 0;
		level = 0;
	}
	
	/**
	 * Loads the user data.
	 */
	public void loadData() {
		CompletableFuture.runAsync(() -> {
			Document document = data.find(Filters.eq("uuid", uuid.toString())).first();
			if (document == null) return;
			
			setKills(document.getInteger("kills"));
			setLevel(document.getInteger("level"));
		});
	}
	
	/**
	 * Save the user data.
	 */
	@SuppressWarnings("deprecation")
	public void saveData() {
		CompletableFuture.runAsync(() -> {
			Document document = new Document();
			document.put("name", name);
			document.put("uuid", uuid.toString());
			document.put("kills", kills);
			document.put("level", level);
			
			data.replaceOne(
				 Filters.eq("uuid", uuid.toString()),
				 document,
				 new UpdateOptions().upsert(true)
			);
		});
	}
	
	/**
	 * Returns the uuid of this user.
	 *
	 * @return The uuid.
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	/**
	 * Returns the nickname of this user.
	 *
	 * @return The nickname.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the kills amount of this user.
	 *
	 * @return The kills amouint.
	 */
	public int getKills() {
		return kills;
	}
	
	/**
	 * Returns the level of this user.
	 *
	 * @return The level.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Increments the kills amount for this user.
	 *
	 * @param amount Amount to increment.
	 */
	public void incrementKills(int amount) {
		kills += amount;
	}
	
	/**
	 * Decrements the kills amount.
	 *
	 * @param amount Amount to decrement for this user.
	 */
	public void decrementKills(int amount) {
		kills -= amount;
	}
	
	/**
	 * Sets an specific kills amount to the user.
	 *
	 * @param kills The kills amoun to set.
	 */
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	/**
	 * Increments the level of user.
	 *
	 * @param amount Amount to increment.
	 */
	public void incrementLevel(int amount) {
		level += amount;
	}
	
	/**
	 * Decrements the level.
	 *
	 * @param amount Amount to decrement for this user.
	 */
	public void decrementLevel(int amount) {
		level -= amount;
	}
	
	/**
	 * Sets an specific level for the user.
	 *
	 * @param level New level to set.
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
