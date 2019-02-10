package kate.workoutplanner;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static kate.workoutplanner.AddWorkoutScreen.test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
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

    public void exerciseItemReps_isCorrect(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name("Squats");
        exerciseItem.reps(13);
        assertEquals(exerciseItem.reps, 13);
    }

    public void exerciseItemName_isCorrect(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name("Squats");
        exerciseItem.reps(13);
        assertEquals(exerciseItem.name, "Squats");
    }

    @Test
    public void daysOfWeekLength(){
        DaysOfWeek dow = new DaysOfWeek();
        assertEquals(dow.sortedWeekdays().length, 8);
    }

    @Test
    public void datesArrayToday_isCorrect(){
        DaysOfWeek dow = new DaysOfWeek();
        String[] dates = dow.getDates();
        assertEquals(dates[0], new Date().toString());
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



    //@Test
    //public void databaseRetrievalContains_Back(){
    //    System.out.print(test());
    //    assertFalse(false);
    //}
}

