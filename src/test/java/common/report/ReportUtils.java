package common.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.IResultMap;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportUtils {
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "index.html";
    private static String logPath = OUTPUT_FOLDER + FILE_NAME;
    public static String sourcePath = OUTPUT_FOLDER + "resources";
    public static String videoPath = "resources/";

    public static ExtentReports initReports(){
        File reportDir = new File(logPath);

        if (!reportDir.exists() && !reportDir.isDirectory()){
            reportDir.mkdir();
        }

        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(logPath);
        extent.attachReporter(spark);
        return extent;
    }

    public static void flushReport(ExtentReports reports){
        reports.flush();
    }

    public static ExtentTest processTestResult(ExtentReports extentReports, ITestResult result){
        ExtentTest test = extentReports.createTest(result.getName());

        for (String output : Reporter.getOutput(result)){
            test.log(Status.INFO, output);
        }

        if (result.getThrowable() != null){
            test.log(Status.FAIL, result.getThrowable().getMessage());
            test.fail(String.format("<video width=\"320\" height=\"240\" controls=\"controls\"> <source src=\"%s\" type=\"video/webm\"> </video>", ReportUtils.videoPath));
        }

        test.getModel().setStartTime(getTime(result.getStartMillis()));
        test.getModel().setEndTime(getTime(result.getEndMillis()));

        return test;
    }

    public static ExtentTest processTest(ExtentReports extentReports, IResultMap resultMap, Status status){

        Set<ITestResult> resultsSet = resultMap.getAllResults();

        List<ITestResult> results = resultsSet.stream().sorted((r1, r2) -> (int) (r1.getStartMillis() - r2.getStartMillis()))
                .collect(Collectors.toList());

        ExtentTest report = null;

        for (ITestResult result : results){
            report = extentReports.createTest(result.getName());

            for (String group : result.getMethod().getGroups()){
                report.assignCategory(group);
            }

            for (String output : Reporter.getOutput(result)){
                report.generateLog(Status.INFO, output);
            }

            if (result.getThrowable() != null){
                report.log(status, result.getThrowable());
            }

            report.getModel().setStartTime(getTime(result.getStartMillis()));
            report.getModel().setEndTime(getTime(result.getEndMillis()));
        }

        return report;
    }

    private static Date getTime(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}
