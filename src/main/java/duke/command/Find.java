package duke.command;

import duke.Duck;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.Task;
import duke.task.TaskList;

public class Find extends DuckCommand{
    String searchTerm;
    TaskList filteredTaskList;

    public Find(String commandBody) throws InvalidCommandException {
        super(commandBody);
        commandName = Duck.COMMAND_FIND;
        searchTerm = commandBody;
        filteredTaskList = new TaskList();
    }

    @Override
    public void execute(TaskList taskList) throws EmptyTaskNameException, InvalidCommandException {
        for (Task task : taskList.getTasks()) {
            if (task.contains(searchTerm)) {
                filteredTaskList.addTask(task);
            }
        }

       ui.printFilteredList(filteredTaskList);
    }
}
