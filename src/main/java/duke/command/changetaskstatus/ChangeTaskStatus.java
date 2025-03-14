package duke.command.changetaskstatus;

import duke.command.DuckCommand;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;

/**
 * Represents the general command to change the status of a task given its taskId. Parses and saves the taskId of the user.
 */

public abstract class ChangeTaskStatus extends DuckCommand {
    public int taskId;

    public ChangeTaskStatus(String commandBody) throws InvalidCommandException {
        super(commandBody);
        taskId = 0;
    }

    @Override
    public void execute(TaskList taskList) throws EmptyTaskNameException {
        try {
            taskId = Integer.parseInt(commandBody);
        } catch (NumberFormatException e) {
            throw new EmptyTaskNameException();
        }
        changeTaskStatus(taskList);
    }

    abstract void changeTaskStatus(TaskList taskList);
}
