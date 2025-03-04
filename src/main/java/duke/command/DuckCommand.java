package duke.command;

import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.task.TaskList;
import duke.ui.Ui;

public abstract class DuckCommand {
    public String commandName;
    public String commandBody;
    public Ui ui;

    public DuckCommand() {
        commandName = "";
        commandBody = "";
        ui = new Ui();
    }

    public DuckCommand(String commandBody) throws InvalidCommandException {
        commandName = "";
        try {
            this.commandBody = commandBody.trim();
            ui = new Ui();
        } catch (ClassCastException e) {
            throw new InvalidCommandException();
        }
    }

    public abstract void execute(TaskList taskList) throws EmptyTaskNameException, InvalidCommandException;
}
