# habit-tracker
Tracks your habits and genereates valuable insights through statistics.

# Problem solving
This program helps you create and maintain positive habits by providing a fast and convenient way to track your progress and by collecting data and generating statistics that help you identify common pitfalls.

# Functionality
- Creating new habits and scheduling them to specific days of the week.
- Fast and effortless habit tracking.
- Provides statistics that highlight the most common reasons for failure. With this information, you can develop strategies to remove these obstacles.

# Example
### Statistics:
![Screenshot 2023-09-26 103055](https://github.com/raphaelgebel/habit-tracker/assets/98976609/32c305ca-6b9d-4a17-8fab-de1dd5c241de)
- As the example shows, the two most common reasons for missing the habit “Working out” are a lack of motivation and a lack of time.
- Thanks to this insight, you can adjust your routine accordingly.

# Usage
1. Clone the repository.
2. Create and schedule your own habits.
3. Compile and run the Java application.

# How to create and schedule habits
1. Open the file Main.java.
2. If you are opening the file for the first time, you will see two example habits defined in lines 41 to 46. Delete these examples and replace them with your own habits in the same code section.
   
    1. Start by defining the days on which your habit should be scheduled. If your habit is daily, you can skip this step and use the "DAILY" variable in the next step. Define your scheduled days like this:
        ```Java
        int[] habitNameDays = {days of the week};
        ```
        The days of the week are represented using the DAY_OF_WEEK values within the ‘Calendar’ class (Java.util.Calendar).
       
    2. Next, create an instance of your habit like this:
        ```Java
        Habit habitName = new Habit(”<habit name>”, habitNameDays);
        ```
        If your habit is scheduled daily, use the constant “DAILY”:
        ```Java
        Habit habitName = new Habit("<habit name>", DAILY);
        ```
        
    3. Finally, add your habit to the list of habits using the “addNewHabit()” method: 
        ```Java
        addNewHabit(habitName);
        ```
    ### Here are two examples:
    #### Example 1:
    ```Java
    int[] workingOutDays = {Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY};
    Habit workingOut = new Habit("Working Out", workingOutDays);
    addNewHabit(workingOut);
    ```
    #### Example 2: 
    ```Java
    Habit stretching = new Habit("Stretching", DAILY);
    addNewHabit(stretching);
    ```
