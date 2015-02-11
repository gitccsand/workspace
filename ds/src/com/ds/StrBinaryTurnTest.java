package com.ds;

import java.io.UnsupportedEncodingException;

public class StrBinaryTurnTest {

	public static void main(String[] args) throws Exception {
//		testboolstrtoboolarr();
		teststrbyte();
//		testchar();
	}
	
	public static void testchar(){
		char a='Äã';
		System.out.println("a is "+a);
		System.out.println(Integer.toBinaryString(a));
	}
	public static void teststrchar() throws Exception{
		String str = "hello baby Âð ÄãºÃ";
		System.out.println("str length: "+str.length());
		System.out.println("char array length: "+str.toCharArray().length);
		for(int i=0;i<str.getBytes().length;i++){
			System.out.print((char)str.getBytes()[i]+"||");
			System.out.print(str.getBytes()[i]+"||");
			System.out.print(Integer.toHexString((str.getBytes()[i]))+"||");
			System.out.println(Integer.toBinaryString((str.getBytes()[i])&0xff)+"||");
//			System.out.println(Byte.toString(str.getBytes()[i]));
//			System.out.print(Byte.toString(str.getBytes()[2*i]));
//			System.out.println(Byte.toString(str.getBytes()[2*i+1]));
		}
//		System.out.println(printHexString(str.getBytes()).getBytes());
		System.out.println(new String(str.getBytes()));
		System.out.println(printByteCharString(str.getBytes()).length());
	}
	public static void teststrbyte() throws Exception{
		String str = "hello baby Âð ÄãºÃ";
		System.out.println("str length: "+str.length());
		System.out.println("char array length: "+str.toCharArray().length);
		System.out.println("byte array length: "+str.getBytes().length);
		for(int i=0;i<str.getBytes().length;i++){
			System.out.print((char)str.getBytes()[i]+"||");
			System.out.print(str.getBytes()[i]+"||");
			System.out.print(Integer.toHexString((str.getBytes()[i]))+"||");
			System.out.println(Integer.toBinaryString((str.getBytes()[i])&0xff)+"||");
//			System.out.println(Byte.toString(str.getBytes()[i]));
//			System.out.print(Byte.toString(str.getBytes()[2*i]));
//			System.out.println(Byte.toString(str.getBytes()[2*i+1]));
		}
//		System.out.println(printHexString(str.getBytes()).getBytes());
		System.out.println(new String(str.getBytes()));
		System.out.println(printByteCharString(str.getBytes()).length());
	}
	
	public static void testboolstrtoboolarr(){
		StrBinaryTurn binaryTurn = new StrBinaryTurn();
		String str = "";
		System.out.println("str length: "+str.length());
		boolean[] bset = binaryTurn.StrToBool(str);
		System.out.println("bool array length:  "+bset.length);
		for (int i =0 ;i<bset.length;i++)
		System.out.println(bset[i]);
	}
	
	public static String printHexString( byte[] b) { 
		String result="";
		for (int i = 0; i < b.length; i++) { 
		String hex = Integer.toHexString(b[i] & 0xFF); 
		if (hex.length() == 1) { 
		hex = '0' + hex; 
		} 
		result=result+hex.toUpperCase(); 
		} 
		return result;

		} 
	public static String printByteCharString( byte[] b) { 
		String result="";
		for (int i = 0; i < b.length; i++) { 
		char c = (char)b[i];
		result=result+c; 
		} 
		return result;

		} 
	public static String printByteIntString( byte[] b) { 
		String result="";
		for (int i = 0; i < b.length; i++) { 
			int j = (int)b[i];
			result=result+String.valueOf(j); 
		} 
		return result;
		
	} 

}
