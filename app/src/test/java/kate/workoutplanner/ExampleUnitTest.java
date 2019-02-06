package kate.workoutplanner;

import org.junit.Test;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static kate.workoutplanner.AddWorkoutScreen.test;
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
        System.out.print(exerciseItem.getExerciseItemText());
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
    public void jokeTest(){
        assertEquals(DaysOfWeek.sortedWeekdays().length, 8);
    }

    //@Test
    //public void databaseRetrievalContains_Back(){
    //    System.out.print(test());
    //    assertFalse(false);
    //}
}