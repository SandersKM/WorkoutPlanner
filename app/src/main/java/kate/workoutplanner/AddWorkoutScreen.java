package kate.workoutplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutScreen extends AppCompatActivity {
    // Back button is not really needed
    private Button goBack;
    private Button addToWorkout;
    private Button addWorkout;
    private ListView workoutPlan;
    private Spinner muscleGroups;
    private Spinner exercises;
    private SeekBar reps;
    private TextView repsNumDisplay;
    private int numRepsSelected;
    private String exerciseSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_screen);
        addToWorkout = (Button) findViewById(R.id.addExerciseToWorkout);
        addWorkout = (Button) findViewById(R.id.saveWorkout);
        workoutPlan = (ListView) findViewById(R.id.workoutSelection);
        this.muscleGroups = (Spinner) findViewById(R.id.muscleGroupSelector);
        dropdownSelections();
        showMuscleGroups();
        reps = findViewById(R.id.repSelector);
        repsNumDisplay = (TextView)findViewById(R.id.repsNumberDisplay);
        repSelection();
        // The following code was modified from
        // https://android--code.blogspot.com/2015/08/android-listview-add-items.html
        final List < String > AddWorkoutElements = new ArrayList < String >();
        final ArrayAdapter < String > workoutSelectionAdapter = new ArrayAdapter < String >
                (AddWorkoutScreen.this, android.R.layout.simple_list_item_multiple_choice,
                        AddWorkoutElements);
        workoutPlan.setAdapter(workoutSelectionAdapter);
        addToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWorkoutElements.add(exerciseSelected.toString() + "\nreps: " + String.valueOf(numRepsSelected));
                workoutSelectionAdapter.notifyDataSetChanged();
            }
        });
        // Binds the Adapter to the ListView
        workoutPlan.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
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

        DatabaseAccess databaseAccess;
        databaseAccess = DatabaseAccess.getInstance(this, null);

        databaseAccess.open();
        List<String> exercises = databaseAccess.getExercises(muscleGroup);
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.exercises.setAdapter(adapter);
    }

    private void addExercise(){

    }

    // How do I seperate these functions?
    private void dropdownSelections(){
        // modified the following code from
        // https://android--code.blogspot.com/2015/08/android-spinner-get-selected-item-text.html
        this.muscleGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                exercises = (Spinner) findViewById(R.id.exerciseSelector);
                showExercises(selectedItemText);
                exercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        exerciseSelected = (String) parent.getItemAtPosition(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void repSelection() {
        // I modified this code from
        // https://tutorialwing.com/android-discrete-seekbar-tutorial-with-example/
        if (reps != null) {
            reps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Write code to perform some action when progress is changed.
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // want the intermediate values...
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Write code to perform some action when touch is stopped.
                    numRepsSelected = seekBar.getProgress();
                    repsNumDisplay.setText(String.valueOf(numRepsSelected));
                }
            });
        }
    }
}
// Workout data from https://www.edu.gov.mb.ca/k12/cur/physhlth/frame_found_gr11/rm/resist_train_planner.xls