package it.polito.ai.Util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDbUtil {
	// connection params
		private static final String url = "mongodb://localhost:27017";
		private static final String db_name = "MinPathsDB";
		
		private static MongoDatabase db = setup();
		private static MongoClient mongoClient;
		
		private static MongoDatabase setup() {	
			// TODO CLOSE mongoClient! 
			mongoClient = new MongoClient(new MongoClientURI(url));
			MongoDatabase db = mongoClient.getDatabase(db_name);
			return db;
		}
		
		public static void close()
		{
			mongoClient.close();
		}
		
		public static MongoDatabase getDb() 
		{
			return db;
		}
		
		
}
