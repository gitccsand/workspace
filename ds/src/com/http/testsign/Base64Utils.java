package com.http.testsign;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {
    /**  
    * BASE64Ω‚√‹  
    *   
    * @param key  
    * @return  
    * @throws Exception  
    */    
    public static byte[] decode(String key) throws Exception {    
        return (new BASE64Decoder()).decodeBuffer(key);    
    }    
    /**  
    * BASE64º”√‹  
    *   
    * @param key  
    * @return  
    * @throws Exception  
    */    
    public static String encode(byte[] key) throws Exception {    
        return (new BASE64Encoder()).encodeBuffer(key);    
    }    
  
}
