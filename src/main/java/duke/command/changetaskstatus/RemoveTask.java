package duke.command.changetaskstatus;

import duke.Duck;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;

public class RemoveTask extends ChangeTaskStatus {
    public RemoveTask(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.COMMAND_DELETE;
    }

    @Override
    void changeTaskStatus(TaskList taskList) {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        ui.printTaskDeleteAcknowledgement(taskList.getTask(taskId - 1));
        taskList.removeTask(taskId - 1);
        ui.printTaskCounter(taskList);
    }
}
