package duke.command.changetaskstatus;

import duke.Duck;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;

/**
 * Represents the command to remove a task given the taskId.
 */

public class RemoveTask extends ChangeTaskStatus {
    /**
     * Constructs RemoveTask object
     *
     * @param commandBody Input text for rest of user input apart from command
     * @throws InvalidCommandException If command format is invalid
     */
    public RemoveTask(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.COMMAND_DELETE;
    }

    /**
     * Removes task of TaskId id
     *
     * @param taskList Current working taskList
     */
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
