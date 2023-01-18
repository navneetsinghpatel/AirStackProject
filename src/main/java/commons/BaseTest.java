package commons;

import models.payloads.VariableData;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.testng.annotations.BeforeSuite;
import utils.GenericUtils;

import java.io.IOException;
import java.util.HashMap;

import static commons.Constants.*;

public class BaseTest {
    public static Logger log = Logger.getLogger("applog");
    public static HashMap<String, String> queryString = new HashMap<>();
    public static HashMap<String, VariableData> dataMap = new HashMap<>();
    @BeforeSuite
    public void setup(){
        addRuntimeLogger(log);
        log.info("Doing initial Setup");
        queryString = GenericUtils.getQueryStringsInAMap(QUERIES_JSON_FILE_PATH);
        log.info("Initialized queryString hashmap with all stored queries in json file");
        dataMap = GenericUtils.getCSVDataIntoMap(TEST_DATA_CSV_FILE_PATH);
        log.info("Initialized queryString hashmap with all stored queries in json file");
    }

    private void addRuntimeLogger(Logger logger) {
        try {
            PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n");
            RollingFileAppender fileAppender = new RollingFileAppender(layout, LOG_FILE_PATH, false);
            logger.setLevel(Level.DEBUG);
            logger.addAppender(fileAppender);
        } catch (IOException e) {
            log.error("Failed to add RunTime Logger for : "+logger.getName());
            e.printStackTrace();
        }

    }
}
