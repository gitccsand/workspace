package com.zk.zktest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.zkclient.ZkClient;
import com.github.zkclient.IZkChildListener;
import com.tcp.testService.RPCConsumer;
import com.tcp.testService.SayHelloSrv;

/**
 * @Description service consumer
 * @author lhw
 * @Date 2014-12-13
 * @Copyright 2014 lhw ,All rights reserved.
 *
 */
public class ZkConsumer extends RPCConsumer {
	private List<String> serverList = new ArrayList<String>();
	
	private String serviceName = "service-B";
	
	//��ʼ�������ַ��Ϣ
	
	public void init(){
		String serviceName = "service-B";
		String zkServerList = "127.0.0.1:2181";
		String SERVICE_PATH = "/configcenter/"+serviceName;//����ڵ�·��
		ZkClient zkClient = new ZkClient(zkServerList, 4000);
		
		boolean serviceExsits = zkClient.exists(SERVICE_PATH);
		
		if(serviceExsits){
			serverList = zkClient.getChildren(SERVICE_PATH);
		}else{
			throw new RuntimeException("service not exsist!");
		}
		
		//ע��watcher
		
		zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChildren)
					throws Exception {
				serverList = currentChildren;
				
			}
		});
		
	}
	
	//��ѯ ����
	public void polling_consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException{
		String serverIp = serverList.iterator().next();
		super.consume(serverIp, 5678, "polling consume");
	}
	//��� ����
	public void random_consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException{
		String serverIp = serverList.get(new Random(0).nextInt(serverList.size()));
		super.consume(serverIp, 5678, "random consume");		
	}
	//Դ��ַ��ϣ ����
	public void hash_consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException{
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
			
		String serverIp = serverList.get(ip.hashCode()%serverList.size());			
		super.consume(serverIp, 5678, "source ip hash consume");
	}
	

	public static void main(String[] args) throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException 
{
		ZkConsumer consumer = new ZkConsumer();
		consumer.init();
		
		consumer.polling_consume();		//��ѯ
		consumer.random_consume();	//���
		consumer.hash_consume(); //��ϣ
	
		
	}	

}

//public void consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException{
//RPCConsumer.main(null);
//}

