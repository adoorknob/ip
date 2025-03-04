package duke.command;

import duke.Duck;
import duke.task.TaskList;

public class List extends DuckCommand{

    public List() {
        commandName = Duck.COMMAND_LIST;
    }

    @Override
    public void execute(TaskList taskList) {
        ui.printList(taskList);
    }
}
