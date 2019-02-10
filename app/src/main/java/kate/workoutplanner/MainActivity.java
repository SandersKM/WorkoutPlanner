package kate.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Button forward;
    private Button[] weekOfWorkouts;
    private WorkoutPlan[] workouts;
    private ListView todaysWorkout;
    private String[] dates;
    DaysOfWeek dow;
    WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the application context
        mContext = getApplicationContext();
        Intent intent = this.getIntent();

        workoutInfoDatabaseAccess = WorkoutInfoDatabaseAccess.getInstance(this, null);
        WorkoutPlan workoutPlan = (WorkoutPlan) intent.getSerializableExtra("myWorkout");
        dow = new DaysOfWeek();
        dates = dow.getDates();
        initializeButtons();


        // TODO for loop to populate dayViews
    }

    private void initializeButtons() {
        createButtonArray();
        setButtonText();
        setGoForward();
    }

    private void setGoForward() {
        for(int i = 0; i < dates.length; i++){
            goForward(i);
        }
    }

    private void createButtonArray(){
        weekOfWorkouts = new Button[8];
        weekOfWorkouts[0] = findViewById(R.id.day0);
        weekOfWorkouts[1] = findViewById(R.id.day1);
        weekOfWorkouts[2] = findViewById(R.id.day2);
        weekOfWorkouts[3] = findViewById(R.id.day3);
        weekOfWorkouts[4] = findViewById(R.id.day4);
        weekOfWorkouts[5] = findViewById(R.id.day5);
        weekOfWorkouts[6] = findViewById(R.id.day6);
        weekOfWorkouts[7] = findViewById(R.id.day7);
    }

    private void setButtonText(){
        for(int i = 1; i< weekOfWorkouts.length; i++){
            Log.e("DEBUG", dates[i]);
            // https://stackoverflow.com/questions/25852961/how-to-remove-brackets-character-in-string-java
            String numExercises = workoutInfoDatabaseAccess.getExerciseCountForDate(dates[i])
                    .toString().replaceAll("[\\[\\]]","");
            String text = dates[i] + "\nExercises: " +  numExercises;
            weekOfWorkouts[i].setText(text);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        todaysWorkout = findViewById(R.id.todaysWorkoutView);
        //WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess = WorkoutInfoDatabaseAccess.getInstance(this, null);
        List<String> todaysExerciseItems = workoutInfoDatabaseAccess.getWorkoutPlanForDate(dates[0]);
        final ArrayAdapter< String > workoutDisplayAdapter = new ArrayAdapter < String >
                (this, android.R.layout.simple_list_item_multiple_choice,
                        todaysExerciseItems);
        todaysWorkout.setAdapter(workoutDisplayAdapter);
        setButtonText();
    }


    public void goForward(int i){
        final int index = i;
        weekOfWorkouts[index].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forwardIntent = new Intent(MainActivity.this, AddWorkoutScreen.class);
                forwardIntent.putExtra("date", dates[index]);
                startActivity(forwardIntent);
            }
        });
    }




}
