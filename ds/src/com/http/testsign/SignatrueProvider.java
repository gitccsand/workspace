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

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@WebServlet("/SignatrueProvider.do")
public class SignatrueProvider  extends HttpServlet{
	
	private static String clientPublicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKNH2eUag8XJDmbDu2tP+rz3e8L/i1fY9lmScr"
+"vUCIRYDXFMICnpuiwogI4egR1UXSWEJr+oFGUijqj4I/XM3HSlnjE91a7GbDZYEOPaJHi66cqWFC"
+"W9uQF2H0cgUrMaVENjOvU8KitqTMP4+0DIjuGi/GUdtVhFvSF9/V+FcNfwIDAQAB";
	
	private static String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK+8c+3sZeiH1eXqEfPLJh1I+zH4"
+"0X8ZLzerllE0m5SwJfuWNKcN02XtV0fEvkP7CLXBT00LCxbazq7Fs9FhfdrOt381srFbAA3+VIkB"
+"1sitSBQqKBpEb4+IfnLaCkZV5zY1ov2BywmNH54YTXYBrJ62D8NIcTeh8drvcE/kkpyhAgMBAAEC"
+"gYBDQGM9EdKOAi2hJspn+b5ERsGahhXmBcVEEK4dtXw0kpR2iyzrISddQk68cFY/vbm/lJK8cRV1"
+"UZb220HNRvnaN4Ggzv+TKxeEgRZFLCgL+CVJsumE9V5rniOoslPGLIIml+WETCIjBzVN0utThaXx"
+"fAFP9Gu3G6ptXvDy7v8j4QJBAOcUhcJHiLcnRfTPPXNeJFFJGqPBlSzsXCEed2ejzVtrRMNCl7NQ"
+"SBfsQm3ngmeZyoczcUAQPb2NntoIxlJdV8cCQQDCsAleLvRFA342KOARpLyHQyQRL/kj49/m7uWV"
+"ORCyih6/GAFZeoTvW8813AvDS9XgxDDSHrcFHvpAs2ERp/hXAkEAqGG48g+EHMneXPwiMA3acuzN"
+"H6Rw2jJFE9EKexfODR6GFa5RslT+o4gCA+ndiTZ0cL5YeLEc/CHVLdWVBfGEgwJBAJurzOHNY65n"
+"W50M1BXnhoQVkJpZpLoo9JigP10xcwN/45PjKvkUOYV3ANnNKG6xjcQau/rXanqiyXHAvQY0LCMC"
+"QH/VG2znLVFFZPAY0T8lzQv0w4R5TqRsSMCDzitiuXasVp3owVADFWeLAeXyIgfNHqC3AAf3D/Ve"
+"ZVJPpAmnOPE=";

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
	
	private String sign(byte[] content,RSAPrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bytes = digest.digest(content);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] signBytes = cipher.doFinal(bytes);
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
	
	private String sortPrarams(Map<String,String[]> params){
		Set<String> keySet = params.keySet();
		TreeSet<String> sortSet = new TreeSet<String>();
		sortSet.addAll(keySet);
		String keyValueStr="";
		Iterator<String> it = sortSet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String value[] = params.get(key);
			keyValueStr+=key+value[0];
		}
		return keyValueStr;
		
	}
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String, String[]> params = req.getParameterMap();
		String sortParams = sortPrarams(params);
		System.out.println("----------------------------------------");
		System.out.println("Provider: Request URL:  "+req.getRequestURI());
		System.out.println("Provider: Sorted Request params:  "+sortParams);
		String reqSign = req.getHeader("Sign");			
		System.out.println("Provider: Request header:  "+reqSign);
		System.out.println("----------------------------------------");
		boolean verfiy = false;
		try {
			verfiy = verify(sortParams.getBytes(), reqSign, getPublicKey(clientPublicKey));
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		if(verfiy){
			String contentStr = "Hello World!  Request and Response Singature are all verified!!!";
			
			String sign = "";
			try {
				sign = sign(contentStr.getBytes(), getPrivatekey(privateKey));
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.setHeader("Sign", sign);
			resp.getWriter().write(contentStr);
			
		}else{
			resp.getWriter().write("Request Signatrue verify fail");
		}
		resp.getWriter().close();
	}



}
