import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDeliberationRepository implements DeliberationRepository {

	@Override
	public boolean exists(Deliberation deliberation) throws Exception {
		BasicDBObject query = new BasicDBObject("id", deliberation.id());
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("pretorian_register");
		DBCollection deliberations = db.getCollection("deliberations");

		DBCursor cursor = deliberations.find(query);

		try {
			while (cursor.hasNext()) {
				return true;
			}
		} finally {
			cursor.close();
		}

		return false;
	}

	@Override
	public void save(Deliberation deliberation) throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("pretorian_register");
		DBCollection deliberations = db.getCollection("deliberations");

		BasicDBObject mongoDeliberation = new BasicDBObject("id", deliberation.id()).append("title",
		        deliberation.title()).append("link", deliberation.link()).
		        append("sector", deliberation.sector()).append("year", deliberation.year());

		deliberations.insert(mongoDeliberation);
	}

}
