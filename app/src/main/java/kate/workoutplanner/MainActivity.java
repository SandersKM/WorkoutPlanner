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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the application context
        mContext = getApplicationContext();
        //goForward();
        Intent intent = this.getIntent();
        WorkoutPlan workoutPlan = (WorkoutPlan) intent.getSerializableExtra("myWorkout");
        initializeButtons();
        // TODO for loop to populate dayViews
    }

    private void initializeButtons() {
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

    @Override
    protected void onStart(){
        super.onStart();
        todaysWorkout = findViewById(R.id.todaysWorkoutView);
        WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess = WorkoutInfoDatabaseAccess.getInstance(this, null);
        List<String> todaysExerciseItems = workoutInfoDatabaseAccess.getWorkoutPlanForDate(getDate());
        Log.e("DB_working", String.valueOf(workoutInfoDatabaseAccess.getWorkoutPlanForDate(getDate())));
        final ArrayAdapter< String > workoutDisplayAdapter = new ArrayAdapter < String >
                (this, android.R.layout.simple_list_item_multiple_choice,
                        todaysExerciseItems);
        todaysWorkout.setAdapter(workoutDisplayAdapter);
    }


    public void goForward(){
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forwardIntent = new Intent(MainActivity.this, AddWorkoutScreen.class);
                startActivity(forwardIntent);
            }
        });
    }

    private String getDate(){
        // https://stackoverflow.com/questions/2942857/how-to-convert-current-date-into-string-in-java
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }



}
