package utils;

import java.util.Locale;
import java.util.ResourceBundle;

import model.InterfaceName;

public class BundleTakeProperFile {
    public static ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.CHINA);
    public static String getUrl(InterfaceName name){
        String address = "";
        //最终测试地址
        if (name == InterfaceName.BICYCLINGAPI){
        	address = bundle.getString("dev");
        }else if(name == InterfaceName.MOCOTEST) {
        	address = bundle.getString("test");
        }
        return address;
    }

}

