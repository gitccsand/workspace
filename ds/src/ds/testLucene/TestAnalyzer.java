package ds.testLucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class TestAnalyzer {

	private static String string = "�л����񹲺͹���1949�꽨�����Ӵ˿�ʼ�����й���ΰ��ƪ�¡�";
	private static String dataDIR = "F:\\ds\\7\\data";
	private static String indexDIR = "F:\\ds\\7\\index";
	private static Version matchVersion = Version.LATEST; // Substitute desired Lucene version for XY
	
    public static void Standard_Analyzer(String str) throws Exception{
	    Analyzer analyzer = new StandardAnalyzer(matchVersion); // or any other analyzer
	    TokenStream ts = analyzer.tokenStream("myfield", string);
	    OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
	    
	    try {
	      ts.reset(); // Resets this stream to the beginning. (Required)
	      while (ts.incrementToken()) {
	        // Use AttributeSource.reflectAsString(boolean)
	        // for token stream debugging.
	        System.out.println("token: " + ts.reflectAsString(true));
	
	        System.out.println("token start offset: " + offsetAtt.startOffset());
	        System.out.println("  token end offset: " + offsetAtt.endOffset());
	      }
	      ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
	    } finally {
	      ts.close(); // Release resources associated with this stream.
	    }
    }
    public static void SmartChinese_Analyzer(String str) throws Exception{
    	Analyzer analyzer = new SmartChineseAnalyzer(matchVersion); // or any other analyzer
    	TokenStream ts = analyzer.tokenStream("myfield", string);
    	OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
    	
    	try {
    		ts.reset(); // Resets this stream to the beginning. (Required)
    		while (ts.incrementToken()) {
    			// Use AttributeSource.reflectAsString(boolean)
    			// for token stream debugging.
    			System.out.println("token: " + ts.reflectAsString(true));
    			
    			System.out.println("token start offset: " + offsetAtt.startOffset());
    			System.out.println("  token end offset: " + offsetAtt.endOffset());
    		}
    		ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
    	} finally {
    		ts.close(); // Release resources associated with this stream.
    	}
    }
    
    public static void Standard_Analyzer_index(String dataDIR,String indexDIR) throws IOException{
    	//indexDir is the directory that hosts Lucene's index files 
        File   indexDir = new File(indexDIR); 
        //dataDir is the directory that hosts the text files that to be indexed 
        File   dataDir  = new File(dataDIR); 
        Analyzer luceneAnalyzer = new StandardAnalyzer(); //Standard Analyzer
        File[] dataFiles  = dataDir.listFiles(); 
        IndexWriter indexWriter = new IndexWriter(new SimpleFSDirectory(indexDir) , new IndexWriterConfig(Version.LATEST, luceneAnalyzer)); 
        long startTime = new Date().getTime(); 
        for(int i = 0; i < dataFiles.length; i++){ 
             if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
                  System.out.println("Indexing file " + dataFiles[i].getCanonicalPath()); 
                  Document document = new Document(); 
                  Reader txtReader = new FileReader(dataFiles[i]); 
                  document.add(new Field("path",dataFiles[i].getCanonicalPath(),Store.YES,Index.NOT_ANALYZED)); 
//                  document.add(Field.Text("contents",txtReader));
                  char[] charbuf = {};
                  txtReader.read(charbuf);               
                  document.add(new Field("contents",new String(charbuf),Store.NO,Index.ANALYZED)); 
                  indexWriter.addDocument(document); 
             } 
        } 
//        indexWriter.optimize(); 
        indexWriter.close(); 
        long endTime = new Date().getTime(); 
           
        System.out.println("It takes " + (endTime - startTime) 
            + " milliseconds to create STANDARD ANALYZED index for the files in directory "
            + dataDir.getPath()); 
       
    }
    public static void SmartCN_Analyzer_index(String dataDIR,String indexDIR) throws IOException{
    	//indexDir is the directory that hosts Lucene's index files 
    	File   indexDir = new File(indexDIR); 
    	//dataDir is the directory that hosts the text files that to be indexed 
    	File   dataDir  = new File(dataDIR); 
    	Analyzer luceneAnalyzer = new SmartChineseAnalyzer(); //SmartChineseAnalyzer Analyzer
    	File[] dataFiles  = dataDir.listFiles(); 
    	IndexWriter indexWriter = new IndexWriter(new SimpleFSDirectory(indexDir) , new IndexWriterConfig(Version.LATEST, luceneAnalyzer)); 
    	long startTime = new Date().getTime(); 
    	for(int i = 0; i < dataFiles.length; i++){ 
    		if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
    			System.out.println("Indexing file " + dataFiles[i].getCanonicalPath()); 
    			Document document = new Document(); 
    			Reader txtReader = new FileReader(dataFiles[i]); 
    			document.add(new Field("path",dataFiles[i].getCanonicalPath(),Store.YES,Index.NOT_ANALYZED)); 
//                  document.add(Field.Text("contents",txtReader));
    			char[] charbuf = {};
    			txtReader.read(charbuf);               
    			document.add(new Field("contents",new String(charbuf),Store.NO,Index.ANALYZED)); 
    			indexWriter.addDocument(document); 
    		} 
    	} 
//        indexWriter.optimize(); 
    	indexWriter.close(); 
    	long endTime = new Date().getTime(); 
    	
    	System.out.println("It takes " + (endTime - startTime) 
    			+ " milliseconds to create SMART CHINESE index for the files in directory "
    			+ dataDir.getPath()); 
    	
    }
    

    public static void main(String[] args) throws Exception{
           String str = string;
         
           System.out.println("��׼�ִʣ����ǲ��Ե��ַ����ǣ�"+str);
           Standard_Analyzer(str);//һԪ�ִʣ�����ִʽ��
           Standard_Analyzer_index(dataDIR, indexDIR);//��������
           
           System.out.println("smartcn�ִʣ����ǲ��Ե��ַ����ǣ�"+str);
           SmartChinese_Analyzer(str);//��Ԫ�ִʣ�����ִʽ��
           SmartCN_Analyzer_index(dataDIR, indexDIR);//��������
     }
}

//public static void Standard_Analyzer(String str) throws Exception{
//Analyzer analyzer = new StandardAnalyzer();        
//Reader r = new StringReader(str);        
//StopFilter sf = (StopFilter) analyzer.tokenStream("", r);
//System.out.println("=====StandardAnalyzer====");
//System.out.println("����������Ĭ��û�д�ֻ���֣�һԪ�ִʣ�");
//Token t;        
//while ((t = sf.next()) != null) {        
//    System.out.println(t.termText());        
//}      
//}
//public static void CJK_Analyzer(String str) throws Exception{
//Analyzer analyzer = new CJKAnalyzer();        
//Reader r = new StringReader(str);        
//StopFilter sf = (StopFilter) analyzer.tokenStream("", r);
//System.out.println("=====CJKAnalyzer====");
//System.out.println("��������:����˫�ַָ��Ԫ�ִʣ�");
//Token t;        
//while ((t = sf.next()) != null) {        
//    System.out.println(t.termText());        
//}      
//}
//public static void Chiniese_Analyzer(String str) throws Exception{
//Analyzer analyzer = new ChineseAnalyzer();        
//Reader r = new StringReader(str);        
//TokenFilter tf = (TokenFilter) analyzer.tokenStream("", r);
//System.out.println("=====chinese analyzer====");
//System.out.println("��������:������ͬStandardAnalyzer��һԪ�ִʣ�");
//Token t;        
//while ((t = tf.next()) != null) {        
//    System.out.println(t.termText());        
//}      
//}
//public static void ik_CAnalyzer(String str) throws Exception{
////Analyzer analyzer = new MIK_CAnalyzer();
//Analyzer analyzer = new IK_CAnalyzer();
//Reader r = new StringReader(str);
//TokenStream ts = (TokenStream)analyzer.tokenStream("", r);
//System.out.println("=====IK_CAnalyzer====");
//System.out.println("��������:�ֵ�ִ�,����˫������");
//Token t;    
//while ((t = ts.next()) != null) {    
//   System.out.println(t.termText());    
//}    
//}
