package kate.workoutplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkoutPlan {

    List<ExerciseItem> exerciseItems;

    public WorkoutPlan(){
        this.exerciseItems = new ArrayList<ExerciseItem>();

    }

    public void addExerciseItem(ExerciseItem item){
        this.exerciseItems.add(item);
    }

    public List<ExerciseItem> getWorkoutPlan(){
        return this.exerciseItems;
    }

    public List<String> getWorkoutPlan_asStrings(){
        List<String> stringList = new ArrayList<String>();
        for(ExerciseItem item: exerciseItems){
            stringList.add(item.getExerciseItemText());
        }
        return stringList;
    }

    public int getWorkoutPlanLength(){
        return this.exerciseItems.size();
    }

    public void addWorkoutPlan(WorkoutPlan plan){
        for(ExerciseItem item: plan.exerciseItems){
            this.exerciseItems.add(item);
        }
    }

}
