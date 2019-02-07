package kate.workoutplanner;

import java.util.List;

public interface WorkoutPlanning {

    void addExerciseItem(ExerciseItem item);

    List<ExerciseItem> getWorkoutPlan();

    List<String> getWorkoutPlan_asStrings();

    int getWorkoutPlanLength();

    void addWorkoutPlan(WorkoutPlan plan);

}
