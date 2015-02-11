package protobuff;

	import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  

import com.google.protobuf.InvalidProtocolBufferException;

import protobuff.LhwMessage.LogonReqMessage;
import protobuff.Model.User;
import protobuff.Model.User.Builder;


	  
	/** 
	 * 
	 * @author Vicky.H 
	 */  
	public class Test {  
	  
	    public static void main(String args[]) throws FileNotFoundException, IOException {  
	       
	    	System.out.println("==================This is simple message.================");
	    	      //��ǰ����������ֱ�ӹ������Ϣ�����ֻ��ͨ�������ڲ�Builder�๹�첢��������ֶεĳ�ʼ����
	    	          LogonReqMessage.Builder logonReqBuilder = LogonReqMessage.newBuilder();
	    	          logonReqBuilder.setAcctID(20);
	    	          logonReqBuilder.setPasswd("Hello World");
	    	          //builder�����ʼ����Ϻ���ͨ��build����������֮��Ӧ����Ϣ�����
	    	          LogonReqMessage logonReq = logonReqBuilder.build();
	    	          int length = logonReq.getSerializedSize();
	    	         System.out.println("The result length is " + length);
	    	         //ֱ�����л����ڴ��У�֮��ɶԸ��ڴ���ж��μӹ�����д�������ļ����͵�Զ�ˣ�����ܡ�
	    	         byte[] buf = logonReq.toByteArray();
	    	 
	    	         try {
	    	             LogonReqMessage logonReq2 = LogonReqMessage.parseFrom(buf);
	    	             System.out.println("acctID = " + logonReq2.getAcctID() + "\tpassword = " + logonReq2.getPasswd());
	    	         } catch (InvalidProtocolBufferException e) {
	    	             e.printStackTrace();
	    	         }
	        
	        
//	    	File file = new File("User.pb"); 
	        
//	        InputStream is = new FileInputStream(file);  
//	        Model.User user = Model.User.parseFrom(is);  
//	        System.out.println(user.getId());  
//	        System.out.println(user.getUsername());  
//	        System.out.println(user.getPassword());  
//	        System.out.println(user.getEmail());  
//	        System.out.println("-------------------");  
//	        for (Model.Person person : user.getPersonList()) {  
//	            System.out.println(person.getId());  
//	            System.out.println(person.getName());  
//	            for (Model.PhoneNumber phone : person.getPhoneList()) {  
//	                System.out.println(phone.getNumber());  
//	            }  
//	            System.out.println("-------------------");  
//	        }  
//	        is.close();  
	    }  
	

}
