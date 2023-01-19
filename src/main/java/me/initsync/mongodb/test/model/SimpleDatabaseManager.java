package me.initsync.mongodb.test.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.initsync.mongodb.test.api.model.database.DatabaseManager;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.Objects;

public class SimpleDatabaseManager implements DatabaseManager {
	private final FileConfiguration config;
	
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> dataCollection;
	
	public SimpleDatabaseManager(FileConfiguration config) {
		this.config = Objects.requireNonNull(config, "The configuration file is null.");
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean establishConnection() {
		try {
			client = new MongoClient(
				 new ServerAddress(config.getString("host"), config.getInt("port")),
				 Collections.singletonList(MongoCredential.createCredential(
					  config.getString("username"),
					  config.getString("database"),
					  config.getString("password").toCharArray())
				 )
			);
			
			database = client.getDatabase(config.getString("database"));
			dataCollection = database.getCollection(config.getString("collection-name"));
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean closeConnection() {
		try {
			client.close();
			database = null;
			dataCollection = null;
		} catch (SecurityException exception) {
			exception.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void setDatabaseProperties() {
		System.setProperty("DEBUG.GO", "true");
		System.setProperty("DB.TRACE", "true");
	}
	
	@Override
	public MongoClient getClient() {
		return client;
	}
	
	@Override
	public MongoDatabase getDatabase() {
		return database;
	}
	
	@Override
	public MongoCollection<Document> getDataCollection() {
		return dataCollection;
	}
}
