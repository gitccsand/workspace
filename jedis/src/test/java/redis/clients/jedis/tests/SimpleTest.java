package redis.clients.jedis.tests;

import redis.clients.jedis.Jedis;

public class SimpleTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.169.6");  
		String keys = "name";  
		  
		// 删数据  
		jedis.del(keys);  
		// 存数据  
		jedis.set(keys, "snowolf");  
		// 取数据  
		String value = jedis.get(keys);  
		  
		System.out.println(value); 
		
		//改数据
		jedis.set(keys, "balckwolf");
		
		System.out.println(jedis.get(keys)); 
		


	}

}
