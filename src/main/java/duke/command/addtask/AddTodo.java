package duke.command.addtask;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Todo;
import duke.task.Task;

public class AddTodo extends AddTask{

    public AddTodo(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.TASK_NAME_TODO;
    }

    Task createTask() throws EmptyTaskNameException {
        if (commandBody.isEmpty()) {
            throw new EmptyTaskNameException();
        }
        return new Todo(commandBody.trim());
    }
}
