package kate.workoutplanner;

public class ExerciseItem {

    String name;
    int reps;

    public ExerciseItem(){
        // Does this constructor need anything?
    }

    public void name(String name){
        this.name = name;
    }

    public void reps(int reps){
        this.reps = reps;
    }

    public int getReps(){
        return this.reps;
    }

    public String getName(){
        return this.name;
    }

    public String getExerciseItemText(){
        return (this.name + "\nreps: " + String.valueOf(this.reps));
    }

}
