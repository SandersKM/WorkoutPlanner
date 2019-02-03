package kate.workoutplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddWorkoutScreen extends AppCompatActivity {
    // Back button is not really needed
    private Button goBack;

    private Spinner muscleGroups;
    private Spinner exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_screen);
        this.muscleGroups = (Spinner) findViewById(R.id.muscleGroupSelector);
        this.muscleGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text

                showExercises(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        showMuscleGroups();
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
        // The following code was adapted from
        // http://www.java2s.com/Code/Android/UI/FilldatatoSpinnerwithArrayAdapter.htm
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, muscleGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.muscleGroups.setAdapter(adapter);
    }

    private void showExercises(String muscleGroup) {

        this.exercises = (Spinner) findViewById(R.id.exerciseSelector);
        DatabaseAccess databaseAccess;
        databaseAccess = DatabaseAccess.getInstance(this, null);

        databaseAccess.open();
        List<String> exercises = databaseAccess.getExercises(muscleGroup);
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.exercises.setAdapter(adapter);
    }
}
// Workout data from https://www.edu.gov.mb.ca/k12/cur/physhlth/frame_found_gr11/rm/resist_train_planner.xls