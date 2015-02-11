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
	
	//初始化服务地址信息
	
	public void init(){
		String serviceName = "service-B";
		String zkServerList = "127.0.0.1:2181";
		String SERVICE_PATH = "/configcenter/"+serviceName;//服务节点路径
		ZkClient zkClient = new ZkClient(zkServerList, 4000);
		
		boolean serviceExsits = zkClient.exists(SERVICE_PATH);
		
		if(serviceExsits){
			serverList = zkClient.getChildren(SERVICE_PATH);
		}else{
			throw new RuntimeException("service not exsist!");
		}
		
		//注册watcher
		
		zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChildren)
					throws Exception {
				serverList = currentChildren;
				
			}
		});
		
	}
	
	//轮询 消费
	public void polling_consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException{
		String serverIp = serverList.iterator().next();
		super.consume(serverIp, 5678, "polling consume");
	}
	//随机 消费
	public void random_consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException, InterruptedException{
		String serverIp = serverList.get(new Random(0).nextInt(serverList.size()));
		super.consume(serverIp, 5678, "random consume");		
	}
	//源地址哈希 消费
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
		
		consumer.polling_consume();		//轮询
		consumer.random_consume();	//随机
		consumer.hash_consume(); //哈希
	
		
	}	

}

//public void consume() throws UnknownHostException, SecurityException, NoSuchMethodException, ClassNotFoundException, IOException{
//RPCConsumer.main(null);
//}

