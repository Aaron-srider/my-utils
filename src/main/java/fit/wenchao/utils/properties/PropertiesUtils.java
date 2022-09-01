package fit.wenchao.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

    /**
     * 从classpath载入资源到Properties对象中
     * @param propertiesName 基于classpath的相对路径，比如"test/test.properties"的绝对路径为:"classpath:test/test.properties"
     * @return 包含文件内容的properties对象
     */
    public static Properties getProperties(String propertiesName)  {
        try(InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream(propertiesName);){
            if( resource==null) {
                return null;
            }
            Properties properties = new Properties();
            properties.load(new InputStreamReader(resource,"UTF-8"));
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}