package ds.testLucene;

import java.io.File; 
import java.io.FileReader; 
import java.io.Reader; 
import java.util.Date; 

import org.apache.lucene.analysis.Analyzer; 
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.standard.StandardAnalyzer; 
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field; 
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter; 
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
/** 
* This class demonstrate the process of creating index with Lucene 
* for text files 
*/ 
public class TxtFileIndexer { 
     public static void main(String[] args) throws Exception{ 
     //indexDir is the directory that hosts Lucene's index files 
     File   indexDir = new File("F:\\lhw\\2014\\ds\\7\\index"); 
     //dataDir is the directory that hosts the text files that to be indexed 
     File   dataDir  = new File("F:\\lhw\\2014\\ds\\7\\data"); 
     Analyzer luceneAnalyzer = new StandardAnalyzer(); 
     File[] dataFiles  = dataDir.listFiles(); 
     IndexWriter indexWriter = new IndexWriter(new SimpleFSDirectory(indexDir) , new IndexWriterConfig(Version.LATEST, luceneAnalyzer)); 
     long startTime = new Date().getTime(); 
     for(int i = 0; i < dataFiles.length; i++){ 
          if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
               System.out.println("Indexing file " + dataFiles[i].getCanonicalPath()); 
               Document document = new Document(); 
               Reader txtReader = new FileReader(dataFiles[i]); 
               document.add(new Field("path",dataFiles[i].getCanonicalPath(),Store.NO,Index.NOT_ANALYZED)); 
//               document.add(Field.Text("contents",txtReader));
               char[] charbuf = {};
               txtReader.read(charbuf);               
               document.add(new Field("contents",new String(charbuf),Store.NO,Index.ANALYZED)); 
               indexWriter.addDocument(document); 
          } 
     } 
//     indexWriter.optimize(); 
     indexWriter.close(); 
     long endTime = new Date().getTime(); 
        
     System.out.println("It takes " + (endTime - startTime) 
         + " milliseconds to create index for the files in directory "
         + dataDir.getPath()); 
    
     } 
     
     
}