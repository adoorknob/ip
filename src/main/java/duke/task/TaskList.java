package duke.task;

import java.util.ArrayList;

/**
 * Represents list of tasks
 */

public class TaskList {
    private final ArrayList<Task> tasks;
    private int taskCounter;

    /**
     * Constructs task list
     */
    public TaskList() {
        tasks = new ArrayList<>();
        taskCounter = 0;
    }

    /**
     * Adds task to list and adds to task counter
     *
     * @param task Task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        taskCounter++;
    }

    /**
     * Returns task of index taskIndex
     * @param taskIndex Index of task
     * @return task Task
     */
    public Task getTask(int taskIndex) {
        return tasks.get(taskIndex);
    }

    /**
     * Removes task at index taskIndex
     * @param taskIndex Index of task to remove
     */
    public void removeTask(int taskIndex) {
        tasks.remove(taskIndex);
        taskCounter--;
    }

    /**
     * Returns task list
     * @return tasks Task list
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns number of tasks
     * @return taskCounter Counter for number of tasks
     */
    public int getTaskCounter() {
        return taskCounter;
    }
}
