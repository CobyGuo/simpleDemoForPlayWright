package common.utils;

import common.report.ReportUtils;

public class ReportDescription {

    private static String desc = "";

    public static void put(String key, String value){
        if (!desc.equals("")){
            desc = desc.concat("<br/>");
        }
        desc = desc.concat(String.format("<b>%s:</b> %s", key, value));
    }

    public static void put(String description){
        if (!desc.equals("")){
            desc = desc.concat("<br/>");
        }
        desc = desc.concat(description);
    }

    public static void build(){
        ReportUtils.getInstance().getTest().getModel().setDescription(desc);
    }
}
