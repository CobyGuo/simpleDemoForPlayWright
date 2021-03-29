package testtry;

import basetest.BaseTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobject.BaiduSearchPage;

import java.util.List;

public class MyFirst extends BaseTest {

    @Test(testName = "openBaiduAndSearch")
    public void openBaiduAndSearch() {

        report.reportStep("Step1 open baidu yi xia");
        String baiduURL = "http://www.baidu.com";
        Page page = navigateToURL(baiduURL);
        BaiduSearchPage searchPage = new BaiduSearchPage(page);
        List<String> hotSearches1 = searchPage.getHotSearchTitles();
        report.reportStep(Status.INFO, String.format("Hot searches are %s", hotSearches1));

        report.reportStep("Step2 click refresh hot search button");
        searchPage.refreshHostSearch();
        List<String> hotSearches2 = searchPage.getHotSearchTitles();
        report.reportStep(Status.INFO, String.format("Hot searches after refresh are %s", hotSearches2));

        softAssert.assertNotEquals(hotSearches1, hotSearches2, "Verify refresh successfully");

        report.reportStep("Step3 input search and get all the menu list items");
        List<String> searchMenuList = searchPage.getSearchInputMenuList("中国");
        report.reportStep(Status.INFO, String.format("Searches dropdown menu lists: %s", searchMenuList));

        report.reportStep("Step4 input search and click search");
        searchPage.baiduSearch("忠告");
        String resultsCount = searchPage.getReturnCountString();
        Reporter.log(String.format("Result count: %s", resultsCount));
        softAssert.assertEquals(resultsCount, "123456", "I know this one will fail");

        softAssert.assertAll();
    }

    @Test(testName = "openBaiduAndSearch1")
    public void openBaiduAndSearch1() {

        report.reportStep("Step1 open baidu yi xia");
        String baiduURL = "http://www.baidu.com";
        Page page = navigateToURL(baiduURL);
        BaiduSearchPage searchPage = new BaiduSearchPage(page);
        List<String> hotSearches1 = searchPage.getHotSearchTitles();
        report.reportStep(Status.INFO, String.format("Hot searches are %s", hotSearches1));

        report.reportStep("Step2 click refresh hot search button");
        searchPage.refreshHostSearch();
        List<String> hotSearches2 = searchPage.getHotSearchTitles();
        report.reportStep(Status.INFO, String.format("Hot searches after refresh are %s", hotSearches2));

        softAssert.assertNotEquals(hotSearches1, hotSearches2, "Verify refresh successfully");

        report.reportStep("Step3 input search and get all the menu list items");
        List<String> searchMenuList = searchPage.getSearchInputMenuList("中国");
        report.reportStep(Status.INFO, String.format("Hot searches are %s", searchMenuList));

        softAssert.assertAll();
    }
}
