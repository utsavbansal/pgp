package com.SAIAFarm.SAIAFarm.Utils;



import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum AppProperties {
    INSTANCE;

    private final Properties properties;
    private ResourceBundle bundle;

    AppProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            bundle = ResourceBundle.getBundle("application");

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getMysqlDriver(){
        return properties.getProperty("mysql.driver-class-name");
    }
    public String getMysqlUrl() {
        System.out.println("Property values "+properties);
        System.out.println("Url: "+bundle.getString("mysql.url"));
        System.out.println("Url: "+properties.getProperty("mysql.url"));
        return properties.getProperty("mysql.url");
//        System.out.println("Url: "+properties.getProperty("spring.datasource.url"));
//        return properties.getProperty("spring.datasource.url");
    }
    public String getMysqlUsername() {
        System.out.println("Property values "+properties);

        System.out.println("All : "+properties.propertyNames());
        return properties.getProperty("mysql.username");
//        System.out.println("All : "+properties.propertyNames());
//        return properties.getProperty("spring.datasource.username");
    }
    public String getMysqlPassword() {
        return properties.getProperty("mysql.password");
//        return properties.getProperty("spring.datasource.password");
    }
}


