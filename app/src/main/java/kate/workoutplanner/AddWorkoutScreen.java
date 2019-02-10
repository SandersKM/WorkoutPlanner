package kate.workoutplanner;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.List;

public class AddWorkoutScreen extends AppCompatActivity {
    // Back button is not really needed
    private Button goBack;
    private Button addToWorkout;
    private ListView workoutPlanDisplay;
    private Spinner muscleGroups;
    private Spinner exercises;
    private SeekBar reps;
    private TextView repsNumDisplay;
    private Text title;
    private int exerciseReps;
    private String exerciseName;
    private WorkoutPlan workoutPlan;
    private String date;
    List<String> savedExerciseItems;
    List<String> savedExerciseItemIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_add_workout_screen);
        date = getIntent().getStringExtra("date");
        TextView title = findViewById(R.id.Title);
        title.setText("Edit Workout: " + date.substring(0, date.length() - 4));
        workoutPlanDisplay = (ListView) findViewById(R.id.workoutSelection);
        //workoutPlanDisplay.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        viewWorkoutPlan();
        createWorkoutPlan();
        saveWorkout();
        deleteCheckedItems();
    }

    private void saveWorkout(){
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // // // // // // // //
    // Edit Workout Plan //
    // // // // // // // //

    private void createWorkoutPlan(){
        workoutPlan = new WorkoutPlan();
        addToWorkout = (Button) findViewById(R.id.addExerciseToWorkout);
        setExerciseItemComponents();
        updateWorkoutPlan();
    }

    // https://android--code.blogspot.com/2015/08/android-listview-add-items.html
    public void updateWorkoutPlan(){
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
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
                selectedItems[i] = savedExerciseItemIDs.get(i);
        }
        Log.e("SAVED", String.valueOf(savedExerciseItemIDs.toString()));
        return selectedItems;
    }

    private void viewWorkoutPlan(){
        workoutPlanDisplay.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getDatabaseUpdate();
        ArrayAdapter workoutSelectionAdapter = getAdapter(savedExerciseItems);
        workoutSelectionAdapter.notifyDataSetChanged();
    }

    private void getDatabaseUpdate(){
        WorkoutInfoDatabaseAccess databaseAccess = getWorkoutInfoDatabaseAccess();
        savedExerciseItems = databaseAccess.getWorkoutPlanForDate(date);
        savedExerciseItemIDs = databaseAccess.getIdsForDate(date);
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

    public ArrayAdapter<String> getAdapter(List<String> list){
        final ArrayAdapter < String > workoutSelectionAdapter = new ArrayAdapter < String >
                (AddWorkoutScreen.this, android.R.layout.simple_list_item_multiple_choice,
                        list);
        workoutPlanDisplay.setAdapter(workoutSelectionAdapter);
        return workoutSelectionAdapter;
    }

    // // // // // // // // //
    // Exercise Selection   //
    // // // // // // // // //

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

    // https://android--code.blogspot.com/2015/08/android-spinner-get-selected-item-text.html
    private void getDropdownSelections(){
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

    private void showMuscleGroups() {
        ExerciseDatabaseAccess databaseAccess =  ExerciseDatabaseAccess
                .getInstance(this, null);
        List<String> muscleGroups = databaseAccess.getMuscleGroups();
        this.muscleGroups.setAdapter(getDropdownArrayAdapter(muscleGroups));
    }

    private void showExercises(String muscleGroup) {
        ExerciseDatabaseAccess databaseAccess = ExerciseDatabaseAccess
                .getInstance(this, null);
        List<String> exercises = databaseAccess.getExercises(muscleGroup);
        this.exercises.setAdapter(getDropdownArrayAdapter(exercises));
    }

    // http://www.java2s.com/Code/Android/UI/FilldatatoSpinnerwithArrayAdapter.htm
    private ArrayAdapter getDropdownArrayAdapter(List<String> display){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, display);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    // // // // // // //
    // REP SELECTION  //
    // // // // // // //

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

    // https://tutorialwing.com/android-discrete-seekbar-tutorial-with-example/
    public void getRepSelection(){
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
            enableAddExerciseButton();
        }
        else{
            disableAddExerciseButton();
        }
    }

    public void enableAddExerciseButton(){
        addToWorkout.setEnabled(true);
        addToWorkout.setText("Add Exercise to Workout");
        addToWorkout.setBackgroundColor(Color.parseColor("#FF446299"));
    }

    public void disableAddExerciseButton(){
        addToWorkout.setEnabled(false);
        addToWorkout.setText("Add Reps!");
        addToWorkout.setBackgroundColor(R.drawable.ic_launcher_background);
    }
}