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
		 * 加密 
		 *  
		 * @param content 需要加密的内容 
		 * @param encryptkey  加密密码 
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
		                Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
		                byte[] byteContent = content.getBytes("utf-8");  
		                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
		                byte[] MI = cipher.doFinal(byteContent);  
		                return MI; // 密文  		       
		}  

		/**解密 
		 * @param content  待解密内容 
		 * @param password 解密密钥 
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
		                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
		                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
		                byte[] result = cipher.doFinal(content);  
		                return result; // 加密   
		}  
		
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{

		String content = "test";  
		String password = "12345678";  
		//加密   
		System.out.println("加密前：" + content);  
		byte[] encryptResult = encrypt(content, password);  
		System.out.println("加密后：" + new String(encryptResult));
		//解密   
		byte[] decryptResult = decrypt(encryptResult,password);  
		System.out.println("解密后：" + new String(decryptResult));  
	}

}
