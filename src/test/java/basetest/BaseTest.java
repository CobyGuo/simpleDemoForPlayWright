package basetest;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.nio.file.Path;

public class BaseTest {
    protected Browser browser;
    protected SoftAssert softAssert = new SoftAssert();

    private BrowserType.LaunchOptions options;
    private Playwright wright;

    @BeforeMethod(alwaysRun = true)
    public void initBrowser(){
        launchBrowser();
    }

    private void launchBrowser(){
        options = new BrowserType.LaunchOptions().setHeadless(false);

        wright = Playwright.create();
        browser = wright.chromium().launch(options);
    }

    protected Page navigateToURL(String url){
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setRecordVideoDir(Path.of("C:\\Users\\cobyg\\Desktop\\path"));
        BrowserContext context = browser.newContext(options);
        Page page = context.newPage();
        page.navigate(url);
        return page;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        browser.close();
        wright.close();
    }
}
