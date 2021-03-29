package common.utils;

import java.util.Calendar;

public class TimeUtil {
    public static long getTimeMillis(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
