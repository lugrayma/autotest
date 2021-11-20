package utils;

import java.util.HashMap;
import java.util.Map;

public class StringToMapUtil {
	
	   public static Map<String,String> getStringToMap(String str){
	       //根据逗号截取字符串数组
	       String[] str1 = str.split("&");
	       //创建Map对象
	       Map<String,String> map = new HashMap<String, String>();
	       //循环加入map集合
	       for (int i = 0; i < str1.length; i++) {
	           //根据":"截取字符串数组
	           String[] str2 = str1[i].split("="); 
	           //str2[0]为KEY,str2[1]为值
	           map.put(str2[0],str2[1]);
	       }
	       return map;
	   }
}
