package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Event;
import duke.task.Task;

public class AddEvent extends AddTask {
    public AddEvent(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.TASK_NAME_EVENT;
    }

    @Override
    Task createTask() throws EmptyTaskNameException{
        int fromDateTimeIndex = commandBody.indexOf("/from");
        int toDateTimeIndex = commandBody.indexOf("/to");
        if (fromDateTimeIndex == -1 || toDateTimeIndex == -1) {
            throw new NumberFormatException();
        }
        String fromDateTime = commandBody.substring(fromDateTimeIndex + Duck.FROM_COMMAND_BUFFER, toDateTimeIndex).trim();
        String toDateTime = commandBody.substring(toDateTimeIndex + Duck.TO_COMMAND_BUFFER).trim();
        String title = commandBody.substring(0, fromDateTimeIndex).trim();

        if (title.isEmpty() || toDateTime.isEmpty() || fromDateTime.isEmpty()) {
            throw new EmptyTaskNameException();
        }

        return new Event(title, fromDateTime, toDateTime);
    }
}
