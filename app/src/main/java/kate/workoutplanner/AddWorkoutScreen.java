package kate.workoutplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class AddWorkoutScreen extends AppCompatActivity {
    // Back button is not really needed
    private Button goBack;

    private Spinner muscleGroups;
    private ListView exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_screen);
        this.muscleGroups = (Spinner) findViewById(R.id.muscleGroupSelector);
        this.exercises = (ListView) findViewById(R.id.exerciseSelector);
        showMuscleGroups();
        showExercises();
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showMuscleGroups() {
        DatabaseAccess databaseAccess;
        databaseAccess = DatabaseAccess.getInstance(this, null);

        databaseAccess.open();
        List<String> muscleGroups = databaseAccess.getMuscleGroups();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, muscleGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.muscleGroups.setAdapter(adapter);
    }

    private void showExercises() {
        DatabaseAccess databaseAccess;
        databaseAccess = DatabaseAccess.getInstance(this, null);

        databaseAccess.open();
        List<String> exercises = databaseAccess.getExercises("Back");
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exercises);
        this.exercises.setAdapter(adapter);
    }
}
// Workout data from https://www.edu.gov.mb.ca/k12/cur/physhlth/frame_found_gr11/rm/resist_train_planner.xls