package com.tcp.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException{
		
		ServerSocket server = new ServerSocket(5678);		
		System.out.println("server started on tcp port 5678");		
		
		while(true){
			Socket client = server.accept();
			
			//��ȡ��Ӧ����
			InputStream input = client.getInputStream();
			Request request = ProtocolUtil.readRequest(input); 
			
			OutputStream output = client.getOutputStream();
			
			//��װ��Ӧ
			Response response = new Response();
			response.setEncode(Encode.UTF8.getValue());
			if(request.getCommand().equals("simple tcp test")){
				response.setResponse("test is ok");
			}else{
				response.setResponse("bye bye!");
			}
			response.setResponseLength(response.getResponse().length());
			
			ProtocolUtil.writeResponse(output, response);
			
			client.shutdownOutput();
			
			
			
		}
	}

}
