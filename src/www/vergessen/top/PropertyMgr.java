package www.vergessen.top;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getInt(String key){
        return Integer.parseInt(PropertyMgr.getString(key));
    }

    public static String getString(String key){
        return (String)PropertyMgr.get(key);
    }

    public static Object get(String key){
        if(props == null) return null;
        return props.get(key);
    }
}
