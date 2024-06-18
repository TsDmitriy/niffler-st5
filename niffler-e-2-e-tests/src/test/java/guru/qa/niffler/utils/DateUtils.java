package guru.qa.niffler.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateUtils {
    public static String formatCurrentDate(Date date, String ofPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ofPattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }
}