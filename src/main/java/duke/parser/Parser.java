package duke.parser;

import duke.Duck;
import duke.command.*;
import duke.command.addtask.AddDeadline;
import duke.command.addtask.AddEvent;
import duke.command.addtask.AddTodo;
import duke.command.changetaskstatus.Mark;
import duke.command.changetaskstatus.RemoveTask;
import duke.command.changetaskstatus.Unmark;
import duke.exception.InvalidCommandException;

import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    public static DuckCommand getCommand() throws InvalidCommandException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] splitInput = input.split(" ");
        String command = splitInput[0].toLowerCase();
        String inputWithoutCommand = String.join(" ", Arrays.asList(splitInput).subList(1, splitInput.length));

        if (isExitCommand(command)) {
            return new Exit();
        }

        return switch (command) {
            case (Duck.COMMAND_LIST) -> new List();
            case (Duck.COMMAND_FIND) -> new Find(inputWithoutCommand);
            case (Duck.COMMAND_MARK) -> new Mark(inputWithoutCommand);
            case (Duck.COMMAND_UNMARK) -> new Unmark(inputWithoutCommand);
            case (Duck.COMMAND_DELETE) -> new RemoveTask(inputWithoutCommand);
            case (Duck.TASK_NAME_TODO) -> new AddTodo(inputWithoutCommand);
            case (Duck.TASK_NAME_DEADLINE) -> new AddDeadline(inputWithoutCommand);
            case (Duck.TASK_NAME_EVENT) -> new AddEvent(inputWithoutCommand);
            default -> throw new InvalidCommandException();
        };
    }

    public static boolean isExitCommand(String command) {
        return Duck.COMMAND_EXIT_LIST.contains(command);
    }
}
