package pl.com.kotlewski.kalendarzbiegacza.domain.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

/**
 * Created by Łukasz on 2018-05-05.
 */

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if(value == null)
            return null;
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(value);
            return calendar;
        }

    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
