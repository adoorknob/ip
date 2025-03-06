package duke.command.addtask;

import duke.command.DuckCommand;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Represents the general command to add a task to the task list
 */

public abstract class AddTask extends DuckCommand {
    /**
     * Constructs a task without params
     */
    public AddTask() {
        super();
    }

    /**
     * Constructs a class with user input
     * @param commandBody User input apart from command name
     * @throws InvalidCommandException If input is invalid
     */
    public AddTask(String commandBody) throws InvalidCommandException {
        super(commandBody);
    }

    /**
     * Creates and adds task to task list
     * @param taskList Task list to add the task to
     * @throws EmptyTaskNameException If task name is empty
     */
    @Override
    public void execute(TaskList taskList) throws EmptyTaskNameException {
        Task task = createTask();
        taskList.addTask(task);
        ui.printTaskAddAcknowledgement(task);
    }

    /**
     * Creates task
     * @return Task Task created
     * @throws EmptyTaskNameException If task name is empty
     */
    abstract Task createTask() throws EmptyTaskNameException;
}
