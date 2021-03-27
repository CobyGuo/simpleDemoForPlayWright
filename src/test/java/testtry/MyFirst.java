package testtry;

import basetest.BaseTest;
import com.microsoft.playwright.*;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobject.BaiduSearchPage;

import java.util.List;

public class MyFirst extends BaseTest {

    @Test
    public void openBaiduAndSearch() {

        String baiduURL = "http://www.baidu.com";
        Page page = navigateToURL(baiduURL);
        BaiduSearchPage searchPage = new BaiduSearchPage(page);
        List<String> hotSearches1 = searchPage.getHotSearchTitles();
        Reporter.log(String.format("Hot searches are %s", hotSearches1));

        searchPage.refreshHostSearch();
        List<String> hotSearches2 = searchPage.getHotSearchTitles();
        Reporter.log(String.format("Hot searches are %s", hotSearches2));

        softAssert.assertNotEquals(hotSearches1, hotSearches2, "Verify refresh successfully");

        List<String> searchMenuList = searchPage.getSearchInputMenuList("中国");
        Reporter.log(String.format("Hot searches are %s", searchMenuList));

        searchPage.baiduSearch("忠告");
        String resultsCount = searchPage.getReturnCountString();
        Reporter.log(String.format("Result count: %s", resultsCount));
        softAssert.assertEquals(resultsCount, "123456", "I know this one will fail");

        softAssert.assertAll();
    }
}
