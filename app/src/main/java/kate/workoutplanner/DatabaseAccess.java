package kate.workoutplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// I modified this code from
// https://www.javahelps.com/2016/07/deploy-and-upgrade-android-database.html

public class DatabaseAccess {
    private ExternalSQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private DatabaseAccess(Context context, String sourceDirectory) {
        if (sourceDirectory == null) {
            this.openHelper = new DatabaseOpenHelper(context);
        } else {
            this.openHelper = new DatabaseOpenHelper(context, sourceDirectory);
        }
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context         the Context
     * @param sourceDirectory optional external directory
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new DatabaseAccess(context, sourceDirectory);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     *
     * @return a List of muscle Groups
     */
    public List<String> getMuscleGroups() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT muscle_group FROM exercises", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     *
     * @return a List of muscle Groups
     */
    public List<String> getExercises(String muscleGroup) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT exercise FROM exercises WHERE muscle_group = 'Back'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
