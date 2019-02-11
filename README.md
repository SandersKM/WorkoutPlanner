# WorkoutPlanner
Android app for scheduling workouts. The following screen captures were taken on a Fire tablet.

## Main Screen
<img src="/ScreenCaptures/MainScreenChecked.png" height="384" width="225"> 

This screen shows a ListView of the workouts selected for the current day. There is also a display to show the date and number of exercises in the workouts for the next week. The current and future workouts can be edited by hitting the corresponding blue button. 

## Main Screen
<img src="/ScreenCaptures/EditWorkoutOnOpen.png" height="384" width="225">

Once a blue button is pushed, the user is brought to a screen where they can edit the selected workout. 

<img src="/ScreenCaptures/EditWorkoutSelectMuscleGroupAndReps.png" height="384" width="225">

They can select a muscle group, which will auto-populate the exercise options. The user must also select the number of reps to perform the exercise (0-25) before adding it to their workout. If the user wishes to perform more than 25 reps, they can add the same exercise multple times.

<img src="/ScreenCaptures/EditWorkoutDeleteSelected.png" height="384" width="225">

The user can also chose to delete an exercise from the workout by selecting the exercising and pushing "Delete Selected". Once a user is happy with their workout, they can push "Save Workout" and return to the updated Main Screen. All changes are written to a database.
