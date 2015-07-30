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
		//�������ݿ������
		MongoClient mongoClient = new MongoClient("localhost", 6666);
		//�г����������������ݿ�
		for(String name : mongoClient.listDatabaseNames()) {
			System.out.println(name);
		}
		//���ӵ����ݿ�
		MongoDatabase database = mongoClient.getDatabase("test");
		
		//ȡ�����ݿ����ĳ������
		MongoCollection<Document> collection = database.getCollection("person");
		
		for(Document doc : collection.find()) {
			System.out.println(doc.toJson());
		}
		//����һ��Document����
		Document doc = new Document("age", 11)
				.append("name", new Document("first", "Anna").append("last", "King"));
		//��Document���뼯����
		//collection.insertOne(doc);
		//��ѯ����
		System.out.println(collection.count());
		for(Document document : collection.find()) {
			System.out.println(document.toJson());
		}
		//��ѯ���м�������ĵ�
		MongoCursor <Document> cursor = collection.find().iterator();
		try{
			while(cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			}
		} finally {
			cursor.close();
		}
		System.out.println();
		//��ѯ�����������ĵ�
		FindIterable<Document> result = collection.find(eq("name.first", "Anna"));
		for(Document document : result) {
			System.out.println(document.toJson());
		}
		System.out.println();
		//ɾ���ĵ�
		collection.deleteOne(eq("name.first", "Anna")); //ֻɾ���ҵ��ķ��������ĵ�һ����¼������ɾ��������¼��deleteMany()
		for(Document document : collection.find()) {
			System.out.println(document.toJson());
		}
	}
}
