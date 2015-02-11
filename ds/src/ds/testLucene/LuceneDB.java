package ds.testLucene;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

//Lucene为数据库建索引实例
public class LuceneDB {
	
   Connection conn = null;
   
   private final String URL = "jdbc:oracle:thin:@192.168.169.105:1521:ORCL";
   
   //操作 Oracle 数据库
   public LuceneDB(){
       try {
           Class.forName("oracle.jdbc.driver.OracleDriver");
          //Oracle连接用户名:hr 密码:oracle
           conn = DriverManager.getConnection(URL,"hr","oracle");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   public Connection getConnection(){
       return this.conn;
   }//获取数据库连接

   public void close(){
       try {
           this.conn.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }//关闭数据库连接

   public static void main(String args[]) throws Exception {  
       LuceneDB lucene = new LuceneDB();
       Connection conn = lucene.getConnection();
       String sql = "select e.EMPLOYEE_ID,e.FIRST_NAME,e.LAST_NAME,e.PHONE_NUMBER,e.EMAIL, d.DEPARTMENT_NAME from employees e, departments d"+
    		   		" where e.DEPARTMENT_ID=d.DEPARTMENT_ID";
       Directory indexDIR = new SimpleFSDirectory(new File("F:\\ds\\7\\index"));
       IndexWriter indexWriter = new IndexWriter(indexDIR, new IndexWriterConfig(Version.LATEST, new StandardAnalyzer()));
       PreparedStatement ps = conn.prepareStatement(sql);
       ResultSet rs = ps.executeQuery();
       while(rs.next()){
           Document doc = new Document();
          // 建索引
           doc.add(new Field("EMPLOYEE_ID",rs.getString("EMPLOYEE_ID"),Field.Store.YES,Field.Index.ANALYZED));
           doc.add(new Field("FIRST_NAME",rs.getString("FIRST_NAME"),Field.Store.YES,Field.Index.ANALYZED));
           doc.add(new Field("LAST_NAME",rs.getString("LAST_NAME"),Field.Store.YES,Field.Index.ANALYZED));
           doc.add(new Field("DEPARTMENT_NAME",rs.getString("DEPARTMENT_NAME"),Field.Store.YES,Field.Index.ANALYZED));
           indexWriter.deleteDocuments(new Term("EMPLOYEE_ID",rs.getString("EMPLOYEE_ID")));
           indexWriter.addDocument(doc); 
           indexWriter.updateDocument(new Term("EMPLOYEE_ID",rs.getString("EMPLOYEE_ID")), doc);
       }
       rs.close();        //关闭记录集
       conn.close();      //关闭数据库连接
//       indexWriter.optimize(); //索引优化
       indexWriter.close();    //关闭读写器

       IndexSearcher searcher = new IndexSearcher(IndexReader.open(indexDIR));

      //选择姓名中包含"Michael"字的记录
       QueryBuilder qbuilder = new QueryBuilder(new StandardAnalyzer());
       Query query = qbuilder.createBooleanQuery("FIRST_NAME", "Michael") ;
      
       TopDocs topDocs = searcher.search(query, 100);
       ScoreDoc[] hits = topDocs.scoreDocs;
       for(int i=0;i< hits.length;i++){
           int DocId = hits[i].doc;
           Document doc = searcher.doc(DocId);
           System.out.println(doc.get("EMPLOYEE_ID")+"  "+doc.get("FIRST_NAME")+"  "+doc.get("LAST_NAME")+"  "+doc.get("DEPARTMENT_NAME"));           
       }
   }
}