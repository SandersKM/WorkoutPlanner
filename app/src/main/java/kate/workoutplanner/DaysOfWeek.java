package kate.workoutplanner;


import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DaysOfWeek {

    public DaysOfWeek(){

    }

    public static String[] sortedWeekdays(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        DateFormatSymbols df = new DateFormatSymbols();
        String[] wd = df.getWeekdays();
        String[] sorted = new String[8];
        sorted[0] = "Today";
        int i = 1;
        for(int j = dayOfWeek + 1; j < wd.length; j++){
            sorted[i] = wd[j];
            i++;
        }
        for(int j = 1; j<= dayOfWeek; j++){
            sorted[i] = wd[j];
            i++;
        }
        for(int j =0; j< sorted.length; j++){
            System.out.print(sorted[j]);
        }
        return sorted;
    }
}
