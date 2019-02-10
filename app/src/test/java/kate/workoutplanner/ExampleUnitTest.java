package kate.workoutplanner;

import android.provider.Telephony;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.test.InstrumentationRegistry;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void exerciseItemText_isString(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name("Squats");
        exerciseItem.reps(13);
        assertTrue(exerciseItem.getExerciseItemText() instanceof String);
    }

    @Test
    public void exerciseItemReps_isCorrect(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name("Squats");
        exerciseItem.reps(13);
        assertEquals(exerciseItem.reps, 13);
    }

    @Test
    public void exerciseItemName_isCorrect(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name("Squats");
        exerciseItem.reps(13);
        assertEquals(exerciseItem.name, "Squats");
    }

    @Test
    public void exerciseIds_areDifferent(){
        ExerciseItem itemA = new ExerciseItem();
        ExerciseItem itemB = new ExerciseItem();
        assertNotEquals(itemA, itemB);
    }

    @Test
    public void daysOfWeekLength(){
        DaysOfWeek dow = new DaysOfWeek();
        assertEquals(dow.sortedWeekdays().length, 8);
    }

    @Test
    public void workoutPlan_isCorrectSize(){
        WorkoutPlan workoutPlan = new WorkoutPlan();
        for(int i = 0; i < 10; i++){
            ExerciseItem exerciseItem = new ExerciseItem();
            exerciseItem.name("Squats");
            exerciseItem.reps(13);
            workoutPlan.addExerciseItem(exerciseItem);
        }
        assertEquals(workoutPlan.getWorkoutPlanLength(), 10);
    }

    @Test
    public void joinWorkoutPlans_isCorrectSize(){
        WorkoutPlan workoutPlan1 = new WorkoutPlan();
        WorkoutPlan workoutPlan2 = new WorkoutPlan();
        for(int i = 0; i < 10; i++){
            ExerciseItem exerciseItem = new ExerciseItem();
            exerciseItem.name("Squats");
            exerciseItem.reps(13);
            workoutPlan1.addExerciseItem(exerciseItem);
            workoutPlan2.addExerciseItem(exerciseItem);
        }
        workoutPlan1.addWorkoutPlan(workoutPlan2);
        assertEquals(workoutPlan1.getWorkoutPlanLength(), 20);
    }


}

