package com.tcp.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtocolUtil {
	public static Request readRequest(InputStream input) throws IOException{
		//读取编码
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];
		//读取命令长度
		byte[] commandLengthBytes = new byte[4];
		input.read(commandLengthBytes);
		int commandLength = ByteUtil.bytesToInt(commandLengthBytes);
		//读取命令
		byte[] commandBytes = new byte[commandLength];
		input.read(commandBytes);
		String command = "";
		if(Encode.GBK.getValue() == encode){
			command = new String(commandBytes,"GBK");
		}else{
			command = new String(commandBytes,"UTF8");
		}
		Request request  = new Request();
		request.setCommand(command);
		request.setCommandLength(commandLength);
		request.setEncode(encode);
		
		return request;		
	}
	
	public static Response readResponse(InputStream input) throws IOException{
		//读取编码
		byte[] encodeBytes = new byte[1];
		input.read(encodeBytes);
		byte encode = encodeBytes[0];
		
		//读取命令长度
		byte[] responseLengthBytes = new byte[4];
		input.read(responseLengthBytes);
		int responseLength = ByteUtil.bytesToInt(responseLengthBytes);
		
		//读取命令
		byte[] responseBytes = new byte[responseLength];
		input.read(responseBytes);
		String responseStr = "";
		if(Encode.GBK.getValue()==encode){
			responseStr = new String(responseBytes,"GBK");
		}else{
			responseStr = new String(responseBytes,"UTF8");			
		}
		
		Response response = new Response();
		response.setEncode(encode);
		response.setResponse(responseStr);
		response.setResponseLength(responseLength);
		
		return response;
	
	}
	public static void writeResponse(OutputStream output,Response response) throws IOException{
		output.write(response.getEncode());
		output.write(ByteUtil.intToByte(response.getResponseLength()));
		if(Encode.GBK.getValue()==response.getEncode()){
			output.write(response.getResponse().getBytes("GBK"));			
		}else{
			output.write(response.getResponse().getBytes("UTF8"));
		}
	}
	
	public static void writeRequest(OutputStream output,Request request) throws IOException{
		//将request请求发送给服务端
		output.write(request.getEncode());
		output.write(ByteUtil.intToByte(request.getCommandLength()));
		if(Encode.GBK.getValue() == request.getEncode()){
			output.write(request.getCommand().getBytes("GBK"));
		}else{
			output.write(request.getCommand().getBytes("UTF8"));
		}
	}
		

}
