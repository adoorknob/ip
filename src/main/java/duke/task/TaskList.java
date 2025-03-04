package duke.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    private int taskCounter;

    public TaskList() {
        tasks = new ArrayList<>();
        taskCounter = 0;
    }

    public void addTask(Task task) {
        tasks.add(task);
        taskCounter++;
    }

    public Task getTask(int taskIndex) {
        return tasks.get(taskIndex);
    }

    public void removeTask(int taskIndex) {
        tasks.remove(taskIndex);
        taskCounter--;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskCounter() {
        return taskCounter;
    }
}
