package poo.desafio2.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.FileInputStream;
import java.util.Properties;

public class LoggerManager {
    private static boolean initialized = false;
    
    public static void initialize() {
        if (!initialized) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("log4j.properties"));
                PropertyConfigurator.configure(props);
                initialized = true;
            } catch (Exception e) {
                System.err.println("Error configurando Log4J: " + e.getMessage());
            }
        }
    }
    
    public static Logger getLogger(Class<?> clazz) {
        initialize();
        return Logger.getLogger(clazz);
    }
}