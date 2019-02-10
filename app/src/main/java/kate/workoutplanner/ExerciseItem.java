package kate.workoutplanner;

import android.util.Log;

import java.util.Calendar;

public class ExerciseItem {

    String name;
    int reps;
    String date;
    String id;

    public ExerciseItem(){
        Calendar c = Calendar.getInstance();
        this.id = String.valueOf(c.getTimeInMillis());
        //Log.e("TIME", this.id);
    }

    public void name(String name){
        this.name = name;
    }

    public void reps(int reps){
        this.reps = reps;
    }

    public void date(String date) { this.date = date; }

    public int getReps(){
        return this.reps;
    }

    public String getDate(){
        return this.date;
    }

    public String getName(){
        return this.name;
    }

    public String getExerciseItemText(){
        return (this.name + "\nreps: " + String.valueOf(this.reps));
    }

    public String getId(){
        return this.id;
    }

}
