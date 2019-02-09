package kate.workoutplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkoutInfoDatabaseAccess {
    private ExternalSQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static WorkoutInfoDatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private WorkoutInfoDatabaseAccess(Context context, String sourceDirectory) {
        if (sourceDirectory == null) {
            this.openHelper = new DatabaseOpenHelper(context, "workoutInfo.db");
        } else {
            this.openHelper = new DatabaseOpenHelper(context, "workoutInfo.db", sourceDirectory);
        }
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context         the Context
     * @param sourceDirectory optional external directory
     * @return the instance of DabaseAccess
     */
    public static WorkoutInfoDatabaseAccess getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new WorkoutInfoDatabaseAccess(context, sourceDirectory);
        }
        return instance;
    }

    public List<String> getExerciseCountForDate(String date) {
        String sqlQuery = "SELECT count(*) FROM workoutInfo WHERE date = '" + date + "'";
        return getQuery(sqlQuery);
    }

    public List<String> getWorkoutPlanForDate(String date) {
        String sqlQuery = "SELECT * FROM workoutInfo WHERE date = '" + date + "'";
        return getQuery(sqlQuery);
    }

    public void deleteExercisesFromWorkout(String[] seletionArgs){
        this.open();
        String selection = "id LIKE ?";
        int deletedRows = database.delete("workoutInfo", selection, seletionArgs);
        this.close();
    }

    public boolean addExerciseToWorkout(ExerciseItem exerciseItem){
        this.open();
        ContentValues values = new ContentValues();
        values.put("date", exerciseItem.getDate());
        values.put("exercise", exerciseItem.getName());
        values.put("reps", String.valueOf(exerciseItem.getReps()));
        Log.e("HELPF", String.valueOf(exerciseItem.getExerciseItemText()));
        try {
            database.insert("workoutInfo", null, values);
            //database.execSQL("CREATE TABLE IF NOT EXISTS workoutInfo(id INTEGER, date TEXT, exercise TEXT, reps INTEGER);");
            //database.execSQL("INSERT INTO workoutInfo VALUES('admin','" + exerciseItem.getDate()+"','" + exerciseItem.getName()+"','" + String.valueOf(exerciseItem.getReps())+"');");
        }
        catch (Exception e){
            Log.e("HELPF", e.getMessage());
            return false;
        }
        Log.e("HELPF", String.valueOf(this.getExerciseCountForDate(exerciseItem.getDate())));
        this.close();
        return true;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
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
