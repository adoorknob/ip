package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.parser.Parser;
import duke.task.Event;
import duke.task.Task;

import java.time.format.DateTimeParseException;

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
        String title = commandBody.substring(0, fromDateTimeIndex).trim();
        String fromDateTimeString = commandBody.substring(fromDateTimeIndex + Duck.FROM_COMMAND_BUFFER, toDateTimeIndex).trim();
        String toDateTimeString = commandBody.substring(toDateTimeIndex + Duck.TO_COMMAND_BUFFER).trim();

        try {
            String fromDateTime = Parser.parseDate(fromDateTimeString);
            String toDateTime = Parser.parseDate(toDateTimeString);

            if (title.isEmpty() || toDateTime.isEmpty() || fromDateTime.isEmpty()) {
                throw new EmptyTaskNameException();
            }

            return new Event(title, fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            ui.printDateTimeFormatError(fromDateTimeString + "/" + toDateTimeString);
            throw new RuntimeException();
        }
    }
}
