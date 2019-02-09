package kate.workoutplanner;

public class ExerciseItem {

    String name;
    int reps;
    String date;

    public ExerciseItem(){
        // Does this constructor need anything?
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

}
