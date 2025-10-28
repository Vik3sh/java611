package Experiment8.src.com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static volatile Properties dbProps;

    private static void loadIfNeeded(Path propertiesPath) {
        if (dbProps != null) {
            return;
        }
        synchronized (DBUtil.class) {
            if (dbProps != null) {
                return;
            }
            Properties props = new Properties();
            if (propertiesPath != null && Files.exists(propertiesPath)) {
                try (FileInputStream fis = new FileInputStream(propertiesPath.toFile())) {
                    props.load(fis);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load DB properties from " + propertiesPath, e);
                }
            } else {
                // Fallback: try classpath
                try {
                    props.load(DBUtil.class.getClassLoader().getResourceAsStream("WEB-INF/db.properties"));
                } catch (Exception e) {
                    throw new RuntimeException("DB properties not found. Place WEB-INF/db.properties.", e);
                }
            }
            dbProps = props;
            try {
                String driver = dbProps.getProperty("driver", "com.mysql.cj.jdbc.Driver");
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("JDBC Driver not found", e);
            }
        }
    }

    public static Connection getConnection(Path webInfPath) throws SQLException {
        Path propsPath = webInfPath != null ? webInfPath.resolve("db.properties") : null;
        loadIfNeeded(propsPath);
        String url = dbProps.getProperty("url");
        String user = dbProps.getProperty("username");
        String pass = dbProps.getProperty("password");
        return DriverManager.getConnection(url, user, pass);
    }
}


