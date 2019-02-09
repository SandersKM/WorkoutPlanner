package kate.workoutplanner;

import android.content.Context;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;

// I modified this code from
// https://www.javahelps.com/2016/07/deploy-and-upgrade-android-database.html
public class DatabaseOpenHelper extends ExternalSQLiteOpenHelper {


    // Workout data from https://www.edu.gov.mb.ca/k12/cur/physhlth/frame_found_gr11/rm/resist_train_planner.xls
    //private static final String DATABASE_NAME = "exercises.db";

    /**
     * Version of the database. Only used to import from assets.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Use this constructor if you want to import database from assets/database directory.
     */
    public DatabaseOpenHelper(Context context, String database_name) {
        super(context, database_name, null, DATABASE_VERSION);
    }

    /**
     * Use this constructor if you want to import database from external directory.
     */
    public DatabaseOpenHelper(Context context, String database_name, String sourceDirectory) {
        super(context, database_name, sourceDirectory, null);
    }
}