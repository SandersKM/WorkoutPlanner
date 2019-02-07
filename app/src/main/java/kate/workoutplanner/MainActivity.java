package kate.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Button forward;
    private Button[] weekOfWorkouts;
    private WorkoutPlan[] workouts;
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


}
