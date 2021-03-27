package pageobject;

import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class BasePage {
    protected Page page;

    public BasePage(Page page){
        this.page = page;
    }

    protected List<String> getAllTextFromCSS(String selector){
        List<String> texts = new ArrayList<>();

        if (selector.contains("nth-child")){
            int index = 1;
            while (page.isVisible(String.format(selector, index))){
                texts.add(page.innerText(String.format(selector, index)));
                index++;
            }
        }else{
            texts.add(page.innerText(selector));
        }
        return texts;
    }
}
