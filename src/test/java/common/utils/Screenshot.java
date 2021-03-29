package common.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import common.report.ReportUtils;

import java.io.File;
import java.nio.file.Path;

public class Screenshot {
    private Page page;
    private String screenPath;

    public Screenshot(Page page){
        this.page = page;
        screenPath = String.format("%s%d.png", ReportUtils.screenPath, TimeUtil.getTimeMillis());

        File reportDir = new File(ReportUtils.OUTPUT_FOLDER + ReportUtils.screenPath);

        if (!reportDir.exists() && !reportDir.isDirectory()){
            reportDir.mkdir();
        }

        page.screenshot(new Page.ScreenshotOptions()
                .setType(ScreenshotType.PNG).setPath(Path.of(ReportUtils.OUTPUT_FOLDER + screenPath)));
    }

    public String getScreenshotPath(){
        return screenPath;
    }
}
