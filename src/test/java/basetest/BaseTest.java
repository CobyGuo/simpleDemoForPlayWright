package basetest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import common.report.ReportUtils;
import common.utils.SoftAssert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;

public class BaseTest {
    protected Browser browser;
    protected SoftAssert softAssert;
    protected ReportUtils report = ReportUtils.getInstance();

    private BrowserType.LaunchOptions options;
    private Playwright wright;
    private Page page;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context){
        report.initReports();
    }

    @BeforeMethod(alwaysRun = true)
    public void initBrowser(Method m){
        launchBrowser();
        report.startCase(m.getName());
    }

    private void launchBrowser(){
        options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000);
        wright = Playwright.create();
        browser = wright.chromium().launch(options);
    }

    protected Page navigateToURL(String url){
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setRecordVideoDir(Path.of(ReportUtils.OUTPUT_FOLDER + ReportUtils.videoPath));
        BrowserContext context = browser.newContext(options);
        page = context.newPage();
        softAssert = new SoftAssert(page);
        page.navigate(url);
        return page;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){
        ReportUtils.videoPath = ReportUtils.videoPath.concat(page.video().path().getFileName().toString());

        if (Arrays.asList(ITestResult.FAILURE, ITestResult.SUCCESS_PERCENTAGE_FAILURE).contains(result.getStatus())){
            report.getTest().info(String.format("<video width=\"320\" height=\"240\" controls=\"controls\"> <source src=\"%s\" type=\"video/webm\"> </video>", ReportUtils.videoPath));
            report.getTest().fail(result.getThrowable());
        }

        browser.close();
        wright.close();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
        report.endSuite();
    }
}
