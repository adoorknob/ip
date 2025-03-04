package duke.command.changetaskstatus;

import duke.Duck;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;

public class Unmark extends ChangeTaskStatus {
    public Unmark(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.COMMAND_UNMARK;
    }

    @Override
    void changeTaskStatus(TaskList taskList) {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        taskList.getTask(taskId - 1).markAsIncomplete();
        ui.printTaskUnmarkAcknowledgement(taskList.getTask(taskId - 1));
    }
}
