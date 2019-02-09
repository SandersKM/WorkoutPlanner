package kate.workoutplanner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddWorkoutScreen extends AppCompatActivity {
    // Back button is not really needed
    private Button goBack;
    private Button addToWorkout;
    private Button addWorkout;
    private ListView workoutPlanDisplay;
    private Spinner muscleGroups;
    private Spinner exercises;
    private SeekBar reps;
    private TextView repsNumDisplay;
    private int exerciseReps;
    private String exerciseName;
    private WorkoutPlan workoutPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_screen);
        addWorkout = (Button) findViewById(R.id.saveWorkout);
        createWorkoutPlan();
        cancelWorkout();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("workoutPlan", workoutPlan);
    }

    private void cancelWorkout(){
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createWorkoutPlan(){
        workoutPlan = new WorkoutPlan();
        workoutPlanDisplay = (ListView) findViewById(R.id.workoutSelection);
        workoutPlanDisplay.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        addToWorkout = (Button) findViewById(R.id.addExerciseToWorkout);
        setExerciseItemComponents();
        updateWorkoutPlan();
    }

    // is there a way to break this down more
    public void updateWorkoutPlan(){
        // The following code was modified from
        // https://android--code.blogspot.com/2015/08/android-listview-add-items.html
        final List < String > AddWorkoutElements = workoutPlan.getWorkoutPlan_asStrings();
        final ArrayAdapter < String > workoutSelectionAdapter = new ArrayAdapter < String >
                (AddWorkoutScreen.this, android.R.layout.simple_list_item_multiple_choice,
                        AddWorkoutElements);
        workoutPlanDisplay.setAdapter(workoutSelectionAdapter);
        addToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseItem exerciseItem = setExerciseItem();
                AddWorkoutElements.add(exerciseItem.getExerciseItemText());
                workoutPlan.addExerciseItem(exerciseItem);
                System.out.println(workoutPlan.getWorkoutPlan_asStrings());
                System.out.println(AddWorkoutElements);
                workoutSelectionAdapter.notifyDataSetChanged();
            }
        });
    }

    private ExerciseItem setExerciseItem(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name(exerciseName);
        exerciseItem.reps(exerciseReps);
        return exerciseItem;
    }

    private void setExerciseItemComponents(){
        setExerciseName();
        repSelection();
    }

    private void setExerciseName(){
        showDropdownSelections();
        getDropdownSelections();
    }

    private void showDropdownSelections(){
        this.muscleGroups = (Spinner) findViewById(R.id.muscleGroupSelector);
        showMuscleGroups();
        getDropdownSelections();
    }

    // How do I return something from this?
    private void getDropdownSelections(){
        // modified the following code from
        // https://android--code.blogspot.com/2015/08/android-spinner-get-selected-item-text.html
        this.muscleGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                showExerciseDrowpdownSelection(selectedItemText);
                getExerciseDropdownSelection();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showExerciseDrowpdownSelection(String selectedItemText){
        exercises = (Spinner) findViewById(R.id.exerciseSelector);
        showExercises(selectedItemText);
    }

    private void getExerciseDropdownSelection(){
        exercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exerciseName = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showMuscleGroups() {
        ExerciseDatabaseAccess databaseAccess = getDatabaseAccess();
        List<String> muscleGroups = databaseAccess.getMuscleGroups();
        databaseAccess.close();
        this.muscleGroups.setAdapter(getArrayAdapter(muscleGroups));
    }

    private void showExercises(String muscleGroup) {
        ExerciseDatabaseAccess databaseAccess = getDatabaseAccess();
        List<String> exercises = databaseAccess.getExercises(muscleGroup);
        databaseAccess.close();
        this.exercises.setAdapter(getArrayAdapter(exercises));
    }

    private ExerciseDatabaseAccess getDatabaseAccess(){
        ExerciseDatabaseAccess databaseAccess;
        databaseAccess = ExerciseDatabaseAccess.getInstance(this, null);
        databaseAccess.open();
        return databaseAccess;
    }

    private ArrayAdapter getArrayAdapter(List<String> display){
        // The following code was adapted from
        // http://www.java2s.com/Code/Android/UI/FilldatatoSpinnerwithArrayAdapter.htm
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, display);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void repSelection() {
        showRepSelection();
        if (reps != null) {
            getRepSelection();
        }
    }

    private void showRepSelection(){
        ensureRepSelected();
        reps = findViewById(R.id.repSelector);
        repsNumDisplay = (TextView)findViewById(R.id.repsNumberDisplay);
    }

    public void getRepSelection(){
        // I modified this code from
        // https://tutorialwing.com/android-discrete-seekbar-tutorial-with-example/
        reps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ensureRepSelected();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                exerciseReps = seekBar.getProgress();
                repsNumDisplay.setText(String.valueOf(exerciseReps));
                ensureRepSelected();
            }
        });
    }

    public void ensureRepSelected(){
        if(exerciseReps > 0){
            addToWorkout.setEnabled(true);
            addToWorkout.setText("Add Exercise to Workout");
            addToWorkout.setBackgroundColor(Color.GREEN);
        }
        else{
            addToWorkout.setEnabled(false);
            addToWorkout.setText("Add Reps!");
            addToWorkout.setBackgroundColor(R.drawable.ic_launcher_background);
        }
    }

    public static List<String> test() {
        ExerciseDatabaseAccess databaseAccess;
        databaseAccess = ExerciseDatabaseAccess.getInstance(new MyApplication().getAppContext(), null);
        databaseAccess.open();
        List<String> muscleGroups = databaseAccess.getMuscleGroups();
        databaseAccess.close();
        return muscleGroups;
    }

}