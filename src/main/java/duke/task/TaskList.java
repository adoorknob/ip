package duke.task;

import duke.exception.EmptyTaskNameException;
import duke.Duck;

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

    public static Task addTodo(String input) throws EmptyTaskNameException {
        if (input.isEmpty()) {
            throw new EmptyTaskNameException();
        }
        return new Todo(input.trim());
    }

    public static Task addDeadline(String input) throws EmptyTaskNameException {
        int byDateIndex = input.indexOf("/by");
        if (byDateIndex == -1) {
            throw new NumberFormatException();
        }
        String byDate = input.substring(byDateIndex + Duck.BY_COMMAND_BUFFER).trim();
        String title = input.substring(0, byDateIndex).trim();

        if (title.isEmpty() || byDate.isEmpty()) {
            throw new EmptyTaskNameException();
        }

        return new Deadline(title, byDate);
    }

    public static Task addEvent(String input) throws EmptyTaskNameException {
        int fromDateTimeIndex = input.indexOf("/from");
        int toDateTimeIndex = input.indexOf("/to");
        if (fromDateTimeIndex == -1 || toDateTimeIndex == -1) {
            throw new NumberFormatException();
        }
        String fromDateTime = input.substring(fromDateTimeIndex + Duck.FROM_COMMAND_BUFFER, toDateTimeIndex).trim();
        String toDateTime = input.substring(toDateTimeIndex + Duck.TO_COMMAND_BUFFER).trim();
        String title = input.substring(0, fromDateTimeIndex).trim();

        if (title.isEmpty() || toDateTime.isEmpty() || fromDateTime.isEmpty()) {
            throw new EmptyTaskNameException();
        }

        return new Event(title, fromDateTime, toDateTime);
    }
}
