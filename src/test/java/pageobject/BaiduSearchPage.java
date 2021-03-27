package pageobject;

import com.microsoft.playwright.Page;

import java.util.List;

public class BaiduSearchPage extends BasePage{
    private String css_hotsearch = "ul li.hotsearch-item:nth-child(%d) span[class*='content-title']";
    private String css_hotsearch_refreshBtn = "a[id*='hotsearch-refresh']";
    private String css_input = "#kw";
    private String css_searchInputMenuList = "form#form li[data-key]:nth-child(%d)";
    private String css_baiduYiXia = "input#su";
    private String css_resultsCount = ".nums_text";


    public BaiduSearchPage(Page page){
        super(page);
    }

    public List<String> getHotSearchTitles(){
        return getAllTextFromCSS(css_hotsearch);
    }

    public void refreshHostSearch(){
        page.click(css_hotsearch_refreshBtn);
        page.waitForLoadState();
    }

    public List<String> getSearchInputMenuList(String searchContent){
        page.fill(css_input, searchContent);
        return getAllTextFromCSS(css_searchInputMenuList);
    }

    public void baiduSearch(String searchContent){
        page.fill(css_input, searchContent);
        page.click(css_baiduYiXia);
        page.waitForLoadState();
    }

    public String getReturnCountString(){
        return page.innerHTML(css_resultsCount);
    }
}
