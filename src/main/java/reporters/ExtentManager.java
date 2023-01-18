package reporters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        try {
            String userDir = System.getProperty("user.dir");
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("result/AutomationReport.html");
            htmlReporter.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            htmlReporter.loadXMLConfig("src/main/resources/extent-config.xml");
            htmlReporter.config().setDocumentTitle("AirStack");
            htmlReporter.config().setReportName("AirStack GraphQL Automation Tests");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extent;
    }
}