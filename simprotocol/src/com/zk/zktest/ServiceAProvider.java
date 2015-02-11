package com.zk.zktest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

import com.github.zkclient.ZkClient;
import com.tcp.testService.RPCProvider;

public class ServiceAProvider extends RPCProvider {
	
private String serviceName = "service-A";
	
	public void init() throws Exception{
		String serverList = "127.0.0.1:2181";
		String PATH = "/configcenter";
		ZkClient zkClient = new ZkClient(serverList);
		
		boolean rootExsists = zkClient.exists(PATH);
		
		if(!rootExsists){
			zkClient.createPersistent(PATH);
		}
		
		boolean serviceExists = zkClient.exists(PATH + "/" + serviceName);
		
		if(!serviceExists){
			zkClient.createPersistent(PATH + "/" + serviceName);
		}
		
		//
		
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		
		zkClient.createEphemeral(PATH + "/" + serviceName + "/" + ip); 
		
	}
	

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		ServiceAProvider serviceAProvider = new ServiceAProvider();
		try {
			serviceAProvider.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serviceAProvider.provide(1234);
		

	}

}
