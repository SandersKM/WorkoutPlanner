package kate.workoutplanner;


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

    // https://stackoverflow.com/questions/5270272/how-to-determine-day-of-week-by-passing-specific-date

    public String[] sortedWeekdays(){
        int dayOfWeek = getDayofWeek();

        String[] sorted = new String[8];
        sorted[0] = "Today";
        int i = 1;
        for(int j = dayOfWeek + 1; j < this.weekdays.length; j++){
            sorted[i] = this.weekdays[j];
            i++;
        }
        for(int j = 1; j<= dayOfWeek; j++){
            sorted[i] = this.weekdays[j];
            i++;
        }
        for(int j =0; j< sorted.length; j++){
            System.out.print(sorted[j]);
        }
        return sorted;
    }

    public static int getDayofWeek(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
