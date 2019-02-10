package kate.workoutplanner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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
    private String date;
    List<String> savedExerciseItems;
    List<String> savedExerciseItemIDs;
    WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_screen);
        date = getIntent().getStringExtra("date");
        workoutPlanDisplay = (ListView) findViewById(R.id.workoutSelection);
        //workoutPlanDisplay.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        viewWorkoutPlan();
        createWorkoutPlan();
        cancelWorkout();
        deleteCheckedItems();
    }

    private void viewWorkoutPlan(){
        workoutPlanDisplay.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getDatabaseUpdate();
        ArrayAdapter workoutSelectionAdapter = getAdapter(savedExerciseItems);
        workoutSelectionAdapter.notifyDataSetChanged();
    }

    private void deleteCheckedItems(){
        Button deleteChecked = findViewById(R.id.deleteButton);
        deleteChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess = getWorkoutInfoDatabaseAccess();
                workoutInfoDatabaseAccess.deleteExercisesFromWorkout(getChecked());
                viewWorkoutPlan();
            }
        });
    }

    private String[] getChecked(){
        SparseBooleanArray checked = workoutPlanDisplay.getCheckedItemPositions();
        String[] selectedItems = new String[checked.size()];
        //ArrayList<String> selectedExercies = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
                selectedItems[i] = savedExerciseItemIDs.get(i);
            //selectedExercies.add(savedExerciseItems.get(i));
        }
        Log.e("SAVED", String.valueOf(savedExerciseItemIDs.toString()));
        //Log.e("SAVED", String.valueOf(savedExerciseItems.get(0)));
        return selectedItems;
    }

    private void getDatabaseUpdate(){
        WorkoutInfoDatabaseAccess databaseAccess = getWorkoutInfoDatabaseAccess();
        savedExerciseItems = databaseAccess.getWorkoutPlanForDate(date);
        savedExerciseItemIDs = databaseAccess.getIdsForDate(date);
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
        addToWorkout = (Button) findViewById(R.id.addExerciseToWorkout);
        setExerciseItemComponents();
        updateWorkoutPlan();
    }

    public ArrayAdapter<String> getAdapter(List<String> list){
        final ArrayAdapter < String > workoutSelectionAdapter = new ArrayAdapter < String >
                (AddWorkoutScreen.this, android.R.layout.simple_list_item_multiple_choice,
                        list);
        workoutPlanDisplay.setAdapter(workoutSelectionAdapter);
        return workoutSelectionAdapter;
    }

    // is there a way to break this down more
    public void updateWorkoutPlan(){
        // The following code was modified from
        // https://android--code.blogspot.com/2015/08/android-listview-add-items.html
        final List < String > AddWorkoutElements = workoutPlan.getWorkoutPlan_asStrings();
        addToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseItem exerciseItem = setExerciseItem();
                addExerciseItemToDatabase(exerciseItem);
                viewWorkoutPlan();
            }
        });
    }

    private ExerciseItem setExerciseItem(){
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.name(exerciseName);
        exerciseItem.reps(exerciseReps);
        exerciseItem.date(date);
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
                showExerciseDropdownSelection(selectedItemText);
                getExerciseDropdownSelection();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showExerciseDropdownSelection(String selectedItemText){
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

    private void addExerciseItemToDatabase(ExerciseItem exerciseItem){
        WorkoutInfoDatabaseAccess databaseAccess = getWorkoutInfoDatabaseAccess();
        boolean ok = databaseAccess.addExerciseToWorkout(exerciseItem);
        Log.e("DB_working", String.valueOf(ok));
    }

    private WorkoutInfoDatabaseAccess getWorkoutInfoDatabaseAccess(){
        WorkoutInfoDatabaseAccess databaseAccess;
        databaseAccess = WorkoutInfoDatabaseAccess.getInstance(this, null);
        return databaseAccess;
    }

    private void showMuscleGroups() {
        ExerciseDatabaseAccess databaseAccess = getExerciseDatabaseAccess();
        List<String> muscleGroups = databaseAccess.getMuscleGroups();
        this.muscleGroups.setAdapter(getArrayAdapter(muscleGroups));
    }

    private void showExercises(String muscleGroup) {
        ExerciseDatabaseAccess databaseAccess = getExerciseDatabaseAccess();
        List<String> exercises = databaseAccess.getExercises(muscleGroup);
        this.exercises.setAdapter(getArrayAdapter(exercises));
    }

    private ExerciseDatabaseAccess getExerciseDatabaseAccess(){
        ExerciseDatabaseAccess databaseAccess;
        databaseAccess = ExerciseDatabaseAccess.getInstance(this, null);
        return databaseAccess;
    }

    // http://www.java2s.com/Code/Android/UI/FilldatatoSpinnerwithArrayAdapter.htm
    private ArrayAdapter getArrayAdapter(List<String> display){
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