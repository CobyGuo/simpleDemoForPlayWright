package common.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {
    private ExtentReports reports;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        reports = ReportUtils.initReports();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = ReportUtils.processTestResult(reports, result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = ReportUtils.processTestResult(reports, result);
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportUtils.flushReport(reports);
    }
}
