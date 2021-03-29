package common.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class ReportUtils {
    private static ReportUtils instance;
    private static ExtentTest reportTest;
    public static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "index.html";
    private static String logPath = OUTPUT_FOLDER + FILE_NAME;
    public static String sourcePath = "resources/";
    public static String videoPath = sourcePath + "video/";
    public static String screenPath = sourcePath + "screenshot/";
    private ExtentReports extent;


    public static ReportUtils getInstance(){
        if (instance == null){
            instance = new ReportUtils();
        }
        return instance;
    }

    public ExtentTest getTest(){
        return reportTest;
    }

    public void startCase(String caseName){
        reportTest = extent.createTest(caseName);
    }

    public void endSuite(){
        extent.flush();
    }

    public void initReports(){
        File reportDir = new File(logPath);

        if (!reportDir.exists() && !reportDir.isDirectory()){
            reportDir.mkdir();
        }

        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(logPath);
        extent.attachReporter(spark);
    }

    public void reportStep(String message){
        getTest().info(String.format("<b>%s</b>", message));
    }

    public void reportStep(Status status, String message){
        getTest().log(status, message);
    }
}
