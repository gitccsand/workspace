package com.testmemcache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class TestMemcache {

	public static void main(String[] args) {
		String[] servers = { "192.168.235.128:11211" };

	       SockIOPool pool = SockIOPool.getInstance();

	       pool.setServers(servers);

	       pool.setFailover(true);

	       pool.setInitConn(10);

	       pool.setMinConn(5);

	       pool.setMaxConn(250);

	       pool.setMaintSleep(30);

	       pool.setNagle(false);

	       pool.setSocketTO(3000);

	       pool.setAliveCheck(true);

	       pool.initialize();
	       
	       /**

	        * 建立MemcachedClient实例

	        * */

	       MemCachedClient memCachedClient = new MemCachedClient();
	       
	       boolean b_add = memCachedClient.add("key1", "value1");
	       if(b_add) System.out.println("memcache add key1 success ="+ b_add);
	       
	       String cachedValue = (String) memCachedClient.get("key1");
	       System.out.println("memcached key1 value is = " + cachedValue);
	       
	       boolean b_replace = memCachedClient.replace("key1", "replaced value");
	       if(b_replace) System.out.println("memcached key1 replaced success = " + b_replace);	       
	       System.out.println("memcached key1 = " + (String)memCachedClient.get("key1"));
	       
	       boolean b_delete = memCachedClient.delete("key1");	       
	       if(b_delete) System.out.println("memcached key1 deleted sucess = "+b_delete);
	       System.out.println("deleted key1 value = " + memCachedClient.get("key1"));
	       
	       
	       

	}

}

//	       for (int i = 0; i < 10; i++) {
//
//	           /**
//
//	            * 将对象加入到memcached缓存
//
//	            * */
//
//	           boolean success = memCachedClient.set("" + i, "Hello!");
//
//	           /**
//
//	            * 从memcached缓存中按key值取对象
//
//	            * */
//
//	           String result = (String) memCachedClient.get("" + i);
//
//	           System.out.println(String.format("set( %d ): %s", i, success));
//
//	           System.out.println(String.format("get( %d ): %s", i, result));
//
//	       }