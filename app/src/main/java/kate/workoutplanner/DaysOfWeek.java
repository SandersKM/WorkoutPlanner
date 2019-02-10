package kate.workoutplanner;


import android.util.Log;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DaysOfWeek {

    String[] weekdays;

    public DaysOfWeek(){
        DateFormatSymbols dateFormat = new DateFormatSymbols();
        this.weekdays = dateFormat.getWeekdays();
    }

    public String[] sortedWeekdays(){
        int dayOfWeek = getDayofWeek();
        String[] sorted = new String[8];
        sorted[0] = "Today";
        int i = 1;
        for(int j = dayOfWeek + 1; j < this.weekdays.length; j++){
            sorted[i] = this.weekdays[j];
            i++;
        }
        // weekdays has nothing in index 0 :(
        for(int j = 1; j<= dayOfWeek; j++){
            sorted[i] = this.weekdays[j];
            i++;
        }
        return sorted;
    }

    // https://stackoverflow.com/questions/5270272/how-to-determine-day-of-week-by-passing-specific-date
    public static int getDayofWeek(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_WEEK);
    }

    // https://www.geeksforgeeks.org/calendar-class-in-java-with-examples/
    public String[] getDates(){
        String[] dates = new String[8];
        Calendar c = Calendar.getInstance();
        String[] date = c.getTime().toString().split(" ");
        dates[0] = date[0] + " " + date[1] + " " + date[2] + " " + date[5];
        for(int i = 1; i < dates.length; i++){
            c.add(c.DATE, 1);
            date = c.getTime().toString().split(" ");
            dates[i] = date[0] + " " + date[1] + " " + date[2] + " " + date[5];
        }
        return dates;
    }
}
