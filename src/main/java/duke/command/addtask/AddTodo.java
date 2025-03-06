package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Todo;
import duke.task.Task;

/**
 * Represents the command to add a _todo task object to the task list
 */

public class AddTodo extends AddTask{

    /**
     * Constructs AddTodo object with user input
     * @param commandBody User input apart from command name
     * @throws InvalidCommandException If command is invalid
     */
    public AddTodo(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.TASK_NAME_TODO;
    }

    /**
     * Creates and returns _Todo task
     *
     * @return _Todo _Todo task constructed from user input
     * @throws EmptyTaskNameException If task name is empty
     */
    Task createTask() throws EmptyTaskNameException {
        if (commandBody.isEmpty()) {
            throw new EmptyTaskNameException();
        }
        return new Todo(commandBody.trim());
    }
}
