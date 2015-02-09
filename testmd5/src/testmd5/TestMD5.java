package testmd5;

import java.security.MessageDigest;

public class TestMD5 {

	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            char str[] = new char[md.length * 2];
            int k = 0;
            for (int i = 0; i < md.length; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
    	System.out.println("9823483984032 ժҪ��");
        System.out.println(TestMD5.MD5("9823483984032"));
        System.out.println("�����ɽ� ժҪ��");
        System.out.println(TestMD5.MD5("�����ɽ�"));
    	
//    	 String s = "This is my original string ,it is very good!";
//    	   String r = "at";
//    	   s = s.replace("is",r);
//    	   System.out.println(s);
    }
}