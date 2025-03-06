package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Deadline;
import duke.task.Task;

/**
 * Represents the command to add a deadline task object to the task list
 */

public class AddDeadline extends AddTask {
    /**
     * Constructs AddDeadline object with user input
     *
     * @param commandBody User input apart from command name
     * @throws InvalidCommandException If command is invalid
     */
    public AddDeadline(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.TASK_NAME_DEADLINE;
    }

    /**
     * Creates a Deadline task
     * @return Deadline Deadline object based off user input
     * @throws EmptyTaskNameException If task name is empty
     */
    @Override
    Task createTask() throws EmptyTaskNameException {
        int byDateIndex = commandBody.indexOf("/by");
        if (byDateIndex == -1) {
            throw new NumberFormatException();
        }
        String byDate = commandBody.substring(byDateIndex + Duck.BY_COMMAND_BUFFER).trim();
        String title = commandBody.substring(0, byDateIndex).trim();

        if (title.isEmpty() || byDate.isEmpty()) {
            throw new EmptyTaskNameException();
        }

        return new Deadline(title, byDate);
    }
}
