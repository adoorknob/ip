package duke.command.changetaskstatus;

import duke.Duck;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;

public class Mark extends ChangeTaskStatus {
    public Mark(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.COMMAND_MARK;
    }

    @Override
    public void changeTaskStatus(TaskList taskList) {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        taskList.getTask(taskId - 1).markAsComplete();
        ui.printTaskMarkAcknowledgement(taskList.getTask(taskId - 1));
    }
}
