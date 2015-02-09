package com.tcp.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("TCP protocol simple test");
		//请求
		Request request = new Request();
		request.setCommand("simple tcp test");
		request.setCommandLength(request.getCommand().length());
		request.setEncode(Encode.UTF8.getValue());
		
		System.out.println("commandlength : "+request.getCommandLength());
		System.out.println("command : "+request.getCommand());
		
		Socket client = new Socket("127.0.0.1",5678);
		
		OutputStream output = client.getOutputStream();
		
		//发送请求
		ProtocolUtil.writeRequest(output, request);
		
		//读取响应数据
		InputStream input = client.getInputStream();
		Response response = ProtocolUtil.readResponse(input);
		client.shutdownOutput();
		
		System.out.println("responselength : "+response.getResponseLength());
		System.out.println("response : "+response.getResponse());
	}

}
