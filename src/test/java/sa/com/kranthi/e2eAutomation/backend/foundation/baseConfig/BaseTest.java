package sa.com.kranthi.e2eAutomation.backend.foundation.baseConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfigs.DBMethods;

public class BaseTest {

    public static Properties prop = null;
    public static Map<String, String> props = new HashMap<>(); // This line seems to be unused or meant for a different purpose based on context
    public static DBMethods db = new DBMethods();
    public static Logger logg; // Assuming Logger is a custom class or an external logging library

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before suite starts");
        // Default to 'dev' if environment not specified
        String environment = System.getProperty("environment", "dev");
        System.out.println("Loading props...");
        prop.load(new FileInputStream("src/test/resources/backendConfig.props"));

        db = new DBMethods();
        db.setup(prop.getProperty("JDBC_URL"), prop.getProperty("JDBC_USER"), prop.getProperty("JDBC_PASSWORD"));

        // If RestAssured is used, the following might be its setup
        // RestAssured.baseURI = prop.getProperty("BASEURI");
    }

    public static void propLoad() {
        try {
            prop = new Properties();
            System.out.println("Loading props...");
            // Default to 'dev' if environment not specified
            String environment = System.getProperty("key", "environment", "dev"); // This line seems slightly off or a typo in the screenshot
            prop.load(new FileInputStream("src/test/resources/backendConfig.props"));

            // Construct property keys based on environment
            String baseURIKey = environment + "_BASEURI";
            String jdbcUrlKey = environment + "_JDBC_URL";
            String jdbcUserKey = environment + "_JDBC_USER";
            String jdbcPasswordKey = environment + "_JDBC_PASSWORD";

            // Put relevant properties based on constructed keys
            props.put("BASEURI", prop.getProperty(baseURIKey));
            props.put("JDBC_URL", prop.getProperty(jdbcUrlKey));
            props.put("JDBC_USER", prop.getProperty(jdbcUserKey));
            props.put("JDBC_PASSWORD", prop.getProperty(jdbcPasswordKey));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterSuite
    public void afterSuite() throws SQLException { // Assuming SQLException from the usage
        // Close resources
        if (db.resultSet != null) db.resultSet.close();
        if (db.statement != null) db.statement.close();
        if (db.connection != null) db.connection.close();
    }
}