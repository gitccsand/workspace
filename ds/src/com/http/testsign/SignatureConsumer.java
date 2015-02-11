package com.http.testsign;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@WebServlet("/testSign.do")
public class SignatureConsumer extends HttpServlet{
	
	private static String providerPbulicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvvHPt7GXoh9Xl6hHzyyYdSPsx+NF/GS83q5ZR"
			+"NJuUsCX7ljSnDdNl7VdHxL5D+wi1wU9NCwsW2s6uxbPRYX3azrd/NbKxWwAN/lSJAdbIrUgUKiga"
			+"RG+PiH5y2gpGVec2NaL9gcsJjR+eGE12Aayetg/DSHE3ofHa73BP5JKcoQIDAQAB";
	
	
	private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIo0fZ5RqDxckOZsO7a0/6vPd7wv"
			+"+LV9j2WZJyu9QIhFgNcUwgKem6LCiAjh6BHVRdJYQmv6gUZSKOqPgj9czcdKWeMT3VrsZsNlgQ49"
			+"okeLrpypYUJb25AXYfRyBSsxpUQ2M69TwqK2pMw/j7QMiO4aL8ZR21WEW9IX39X4Vw1/AgMBAAEC"
			+"gYBLcMe5ah2JhYXbIe2emEKPyJo1cYIzn6xs6yMErzPQrw/bWAtnXqjaOZIENzx0r5SDc4YeZwm2"
			+"FxcaoueYKtJwT2O5X49vVsLj3NAR1/I5rsEG4vR0i/LeC9jAhDMbITbro1Dm9Ffpa529nLsIHsTb"
			+"nQVO6nQCMa1tVMZ/DDeNsQJBAMIUgJoqGofAA7HHQzdIu921RGBlXxFeu+hrlZBrHPX+ZZtCGSAr"
			+"evabOLPMJpfa+guHlKMum5r1jI+horWuxpUCQQC2TGRiTvmIX2YRU8HzahfQ/ZV0e6e/9xx39mk1"
			+"L91l30DFIb6yHgUEpCNOrOP1mHuFmRhlBKeqg9VH+6Z+/iLDAkBNHsyDdhuYuco+CIpDG5nbXb5P"
			+"97gxNM3Ca43BF28oqPBDI57MU7rG56M+fu8PBKq7jiKYPVsD1nMGWBpJy6uJAkBn9nL9OarHIhi0"
			+"Yv7mEagZcLTOJEMLkCK1K1rekTiDA2oNwmvdao9UEUNtd93dTyOKkj++RIRWEy8MCTDvlYE/AkBf"
			+"Turt4iZ3Pp96azHnYitm44KUtvdhDd5tYmgvVSgg1D5CrcKWwXU+G/gAvk6Ef+B9gUrxsCO+kCgz"
			+"sLEe2yZO";
	
	private RSAPrivateKey getPrivatekey(String privateKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] privateKeyBytes = new BASE64Decoder().decodeBuffer(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");	
		
		RSAPrivateKey key=(RSAPrivateKey)keyFactory.generatePrivate(keySpec);
		return key;
	}
	
	private RSAPublicKey getPublicKey(String publicKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] publickKeyBytes = new BASE64Decoder().decodeBuffer(publicKeyStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publickKeyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		
		RSAPublicKey key = (RSAPublicKey)factory.generatePublic(keySpec);
		return key;
	}
	
	private String sign(byte[] content,RSAPrivateKey privateKey) throws Exception{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] contentBytes = digest.digest(content);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] signBytes = cipher.doFinal(contentBytes);
		String signStr= new BASE64Encoder().encodeBuffer(signBytes).replace("\r\n", "");
		return signStr;
		
//		return signBytes;
	}
	
	private boolean verify(byte[] content,String sign,RSAPublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		MessageDigest digest  = MessageDigest.getInstance("MD5");
		byte[] contentDigest = digest.digest(content);
		
		byte[] signBytes = new BASE64Decoder().decodeBuffer(sign);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptSign = cipher.doFinal(signBytes);
		
		BASE64Encoder encoder = new BASE64Encoder();
		if(encoder.encode(contentDigest).equals(encoder.encode(decryptSign)))
			return true;
		else
			return false;
	}
	
	private String sortPrarams(Map<String,String> params){
		Set<String> keySet = params.keySet();
		TreeSet<String> sortSet = new TreeSet<String>();
		sortSet.addAll(keySet);
		String keyValueStr="";
		Iterator<String> it = sortSet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = params.get(key);
			keyValueStr+=key+value;
		}
		return keyValueStr;
		
	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		String service = "signedService";
		String format = "json";
		String arg1 = "hello";
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("service",service);
		params.put("format", format);
		params.put("arg", arg1);
		//签名
		String sortPrarams = sortPrarams(params);
		String sign="";
		try {
			sign= sign(sortPrarams.getBytes(), getPrivatekey(privateKey));
			
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeySpecException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String url = "http://127.0.0.1:8080/ds/SignatrueProvider.do?"+"service="+service+"&format="+format+"&arg="+arg1;
		
		//组装请求
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Sign", sign);
//		System.out.println("Sign:  "+"fasdjfldsfj");
		System.out.println("----------------------------------------");  
		System.out.println("httpGet URI:  "+httpGet.getURI());
		System.out.println("httpGet to String = :  " + httpGet.toString());
		System.out.println("httpGet header:  "+httpGet.getHeaders("Sign")[0]);
		System.out.println("----------------------------------------");  
	
		//执行请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		
		//接收响应
		HttpEntity entity = response.getEntity();
		System.out.println("----------------------------------------");  
        System.out.println(response.getStatusLine());  
        if (entity != null) {  
            System.out.println("Response content length: " + entity.getContentLength());  
        }  
        String responseSign = response.getLastHeader("Sign").getValue();
        System.out.println("Response Header: "+responseSign);  
		
		byte[] entityBytes = EntityUtils.toByteArray(entity);
		
		boolean verify=false;
		try {
			verify = verify(entityBytes, responseSign, getPublicKey(providerPbulicKey));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (verify) {
			System.out.println("Response Entity : "+new String(entityBytes));
			System.out.println("----------------------------------------");
		}else{
			resp.getWriter().write("Response Signatrue verify failed");
		}
		httpClient.close();
	}
	
	

	public static void main(String[] args) throws ClientProtocolException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		
		SignatureConsumer signatureConsumer = new SignatureConsumer();
		try {
			signatureConsumer.doGet(null, null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

//		String data = "asdjfljdalf";
//		String sign = "";
//		try {
//			sign = signatureConsumer.sign(data.getBytes(), signatureConsumer.getPrivatekey(privateKey));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		boolean verify = false;
//		verify = signatureConsumer.verify(data.getBytes(), sign, signatureConsumer.getPublicKey(clientPublicKey));
//		
//		if (verify) 
//			{System.out.println("ok");}
//		else
//			{System.out.println("bad");}
//