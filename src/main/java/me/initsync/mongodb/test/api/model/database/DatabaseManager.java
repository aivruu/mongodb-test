package me.initsync.mongodb.test.api.model.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public interface DatabaseManager {
	/**
	 * Establish the database connection.
	 *
	 * @return A boolean value. True if the connection was established successful, else return false.
	 */
	boolean establishConnection();
	
	/**
	 * Close the database connection.
	 *
	 * @return A boolean value. True if the connection was closed successful, else return false.
	 */
	boolean closeConnection();
	
	/**
	 * Sets the database booting properties.
	 */
	void setDatabaseProperties();
	
	/**
	 * Returns the mongo client.
	 *
	 * @return The client.
	 */
	MongoClient getClient();
	
	/**
	 * Returns the database object.
	 *
	 * @return A MongoDatabase object.
	 */
	MongoDatabase getDatabase();
	
	/**
	 * Returns the database collection (table) "Data".
	 *
	 * @return The "Data" collection.
	 */
	MongoCollection<Document> getDataCollection();
}
