package common.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import common.report.ReportUtils;
import org.testng.asserts.IAssert;

public class SoftAssert extends org.testng.asserts.SoftAssert {
    private ReportUtils report = ReportUtils.getInstance();
    private Page page;

    public SoftAssert(Page page){
        this.page = page;
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        super.onAssertFailure(assertCommand, ex);
        report.getTest().fail(String.format("%s</br><b>Expect:</b> %s</br><b>Actual:</b> %s", assertCommand.getMessage(), assertCommand.getExpected(), assertCommand.getActual()),
                MediaEntityBuilder.createScreenCaptureFromPath(new Screenshot(page).getScreenshotPath()).build());
    }

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        super.onAssertSuccess(assertCommand);
        report.getTest().pass(String.format("%s</br><b>Expect:</b> %s</br><b>Actual:</b> %s", assertCommand.getMessage(), assertCommand.getExpected(), assertCommand.getActual()));
    }
}
