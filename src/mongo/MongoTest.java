package mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

public class MongoTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//连接数据库服务器
		MongoClient mongoClient = new MongoClient("localhost", 6666);
		//列出服务器中所有数据库
		for(String name : mongoClient.listDatabaseNames()) {
			System.out.println(name);
		}
		//连接到数据库
		MongoDatabase database = mongoClient.getDatabase("test");
		
		//取得数据库里的某个集合
		MongoCollection<Document> collection = database.getCollection("person");
		
		for(Document doc : collection.find()) {
			System.out.println(doc.toJson());
		}
		//创建一个Document对象
		Document doc = new Document("age", 11)
				.append("name", new Document("first", "Anna").append("last", "King"));
		//把Document插入集合中
		//collection.insertOne(doc);
		//查询集合
		System.out.println(collection.count());
		for(Document document : collection.find()) {
			System.out.println(document.toJson());
		}
		//查询所有集合里的文档
		MongoCursor <Document> cursor = collection.find().iterator();
		try{
			while(cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			}
		} finally {
			cursor.close();
		}
		System.out.println();
		//查询符合条件的文档
		FindIterable<Document> result = collection.find(eq("name.first", "Anna"));
		for(Document document : result) {
			System.out.println(document.toJson());
		}
		System.out.println();
		//删除文档
		collection.deleteOne(eq("name.first", "Anna")); //只删除找到的符合条件的第一条记录，若想删除多条记录用deleteMany()
		for(Document document : collection.find()) {
			System.out.println(document.toJson());
		}
	}
}
