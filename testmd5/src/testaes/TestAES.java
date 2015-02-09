package testaes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TestAES {

		/** 
		 * ���� 
		 *  
		 * @param content ��Ҫ���ܵ����� 
		 * @param encryptkey  �������� 
		 * @return 
		 * @throws NoSuchAlgorithmException 
		 * @throws NoSuchPaddingException 
		 * @throws UnsupportedEncodingException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
		 */  
		public static byte[] encrypt(String content, String encryptkey) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {  
		                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		                kgen.init(128, new SecureRandom(encryptkey.getBytes()));  
		                SecretKey secretKey = kgen.generateKey();  
		                byte[] enCodeFormat = secretKey.getEncoded();  
		                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
		                Cipher cipher = Cipher.getInstance("AES");// ����������   
		                byte[] byteContent = content.getBytes("utf-8");  
		                cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��   
		                byte[] MI = cipher.doFinal(byteContent);  
		                return MI; // ����  		       
		}  

		/**���� 
		 * @param content  ���������� 
		 * @param password ������Կ 
		 * @return 
		 * @throws NoSuchAlgorithmException 
		 * @throws NoSuchPaddingException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
		 */  
		public static byte[] decrypt(byte[] content, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {  
		                 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		                 kgen.init(128, new SecureRandom(password.getBytes()));  
		                 SecretKey secretKey = kgen.generateKey();  
		                 byte[] enCodeFormat = secretKey.getEncoded();  
		                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
		                 Cipher cipher = Cipher.getInstance("AES");// ����������   
		                cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��   
		                byte[] result = cipher.doFinal(content);  
		                return result; // ����   
		}  
		
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{

		String content = "test";  
		String password = "12345678";  
		//����   
		System.out.println("����ǰ��" + content);  
		byte[] encryptResult = encrypt(content, password);  
		System.out.println("���ܺ�" + new String(encryptResult));
		//����   
		byte[] decryptResult = decrypt(encryptResult,password);  
		System.out.println("���ܺ�" + new String(decryptResult));  
	}

}
