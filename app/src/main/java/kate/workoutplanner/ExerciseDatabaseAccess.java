package kate.workoutplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// I modified this code from
// https://www.javahelps.com/2016/07/deploy-and-upgrade-android-database.html

public class ExerciseDatabaseAccess {
    private ExternalSQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ExerciseDatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private ExerciseDatabaseAccess(Context context, String sourceDirectory) {
        if (sourceDirectory == null) {
            this.openHelper = new DatabaseOpenHelper(context, "exercises.db");
        } else {
            this.openHelper = new DatabaseOpenHelper(context, "exercises.db", sourceDirectory);
        }
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context         the Context
     * @param sourceDirectory optional external directory
     * @return the instance of DabaseAccess
     */
    public static ExerciseDatabaseAccess getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new ExerciseDatabaseAccess(context, sourceDirectory);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getMuscleGroups() {
        return getQuery("SELECT DISTINCT muscle_group FROM exercises");
    }

    public List<String> getExercises(String muscleGroup) {
        String sqlQuery = "SELECT exercise FROM exercises WHERE muscle_group = '" + muscleGroup + "'";
        return getQuery(sqlQuery);
    }

    public List<String> getQuery(String sqlQuery){
        this.open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return list;
    }
}
