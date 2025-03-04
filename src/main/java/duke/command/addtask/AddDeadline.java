package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Deadline;
import duke.task.Task;

public class AddDeadline extends AddTask {
    public AddDeadline(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.TASK_NAME_DEADLINE;
    }

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
