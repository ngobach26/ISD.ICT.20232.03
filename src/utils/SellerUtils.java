package utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SellerUtils {

    public static Date converToDate(LocalDate dateToConvert) {
        Date date = Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
}
