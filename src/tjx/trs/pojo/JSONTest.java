package tjx.trs.pojo;

import com.alibaba.fastjson.JSONObject;

public class JSONTest {
	public static void main(String[] args) {
		Document doc = new Document() ;
		doc.setAuthor("ansj") ;
		doc.setTitle("tjx shi pig") ;
		doc.setBody(" you dao li ") ;
		
		//���԰�һ������ת��Ϊ�ַ���������ַ������Դ浽�ļ��� �������Ƕ������л�
		String jsonString = JSONObject.toJSONString(doc) ;
		System.out.println(jsonString);
		
		//�����л�
		Document parse = JSONObject.parseObject(jsonString, Document.class) ;
		System.out.println( parse.getTitle());
		
	}
}

class Document{
	private String title ;
	private String body ;
	private String author ;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}