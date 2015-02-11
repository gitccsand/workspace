package com.zk.zktest;

import java.util.List;

import com.github.zkclient.ZkClient;

public class ZkNodeTest {

	public static void main(String[] args) {
		String zkServerList = "127.0.0.1:2181";
		ZkClient zkClient = new ZkClient(zkServerList, 4000);
		
		String path = "/ZkNodeTest";
		
		zkClient.createPersistent(path);
		
//		zkClient.
		
		List<String> serviceList = zkClient.getChildren(path);
		
		System.out.println(path + "/" + serviceList);
		
		zkClient.delete(path);
		
		

	}

}
