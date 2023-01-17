package me.initsync.mongodb.test.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.initsync.mongodb.test.api.model.database.DatabaseManager;
import org.bson.Document;

import java.util.Collections;

public class SimpleDatabaseManager implements DatabaseManager {
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> dataCollection;
	
	public SimpleDatabaseManager() {}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean establishConnection() {
		try {
			client = new MongoClient(
				 new ServerAddress("", 27017),
				 Collections.singletonList(MongoCredential.createCredential(
					  "up2iry1szhbk9ygcex5e",
					  "b9y5zvptnbpcivp",
					  "W5zIF0DoWFIOhTgl2xjO".toCharArray())
				 )
			);
			
			database = client.getDatabase("b9y5zvptnbpcivp");
			dataCollection = database.getCollection("Data");
		} catch (IllegalArgumentException exception) {
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
