package duke.command.addtask;

import duke.command.DuckCommand;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Task;
import duke.task.TaskList;

public abstract class AddTask extends DuckCommand {
    public AddTask() {
        super();
    }

    public AddTask(String commandBody) throws InvalidCommandException {
        super(commandBody);
    }

    public void execute(TaskList taskList) throws EmptyTaskNameException {
        Task task = createTask();
        taskList.addTask(task);
        ui.printTaskAddAcknowledgement(task);
    }

    abstract Task createTask() throws EmptyTaskNameException;
}
