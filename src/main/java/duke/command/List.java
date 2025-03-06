package duke.command;

import duke.Duck;
import duke.task.TaskList;

/**
 * Represents the command to list the current saved task list.
 */

public class List extends DuckCommand{

    public List() {
        commandName = Duck.COMMAND_LIST;
    }

    @Override
    public void execute(TaskList taskList) {
        ui.printFullList(taskList);
    }
}
