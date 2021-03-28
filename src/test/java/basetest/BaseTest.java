package basetest;

import com.microsoft.playwright.*;
import common.report.ReportUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.nio.file.Path;

public class BaseTest {
    protected Browser browser;
    protected SoftAssert softAssert = new SoftAssert();

    private BrowserType.LaunchOptions options;
    private Playwright wright;
    private Page page;

    @BeforeMethod(alwaysRun = true)
    public void initBrowser(){
        launchBrowser();
    }

    private void launchBrowser(){
        options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000);

        wright = Playwright.create();
        browser = wright.chromium().launch(options);
    }

    protected Page navigateToURL(String url){
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setRecordVideoDir(Path.of(ReportUtils.sourcePath));
        BrowserContext context = browser.newContext(options);
        page = context.newPage();
        page.navigate(url);
        return page;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        ReportUtils.videoPath = ReportUtils.videoPath.concat(page.video().path().getFileName().toString());
        browser.close();
        wright.close();
    }
}
