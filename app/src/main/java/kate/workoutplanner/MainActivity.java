package kate.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
        goForward();
        Intent intent = this.getIntent();
        WorkoutPlan workoutPlan = (WorkoutPlan) intent.getSerializableExtra("myWorkout");
        // TODO for loop to populate dayViews
    }

    @Override
    protected void onStart(){
        super.onStart();
        todaysWorkout = findViewById(R.id.todaysWorkoutView);
        WorkoutInfoDatabaseAccess workoutInfoDatabaseAccess = WorkoutInfoDatabaseAccess.getInstance(this, null);
       // Log.e("DB_working", String.valueOf(workoutInfoDatabaseAccess.getExerciseCountForDate(getDate())));
    }


    public void goForward(){
        forward = findViewById(R.id.goForward);
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
