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

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents the parser for user commands.
 */

public class Parser {
    static String[][] dateTimeFormats = {
            {"dd/MM/yyyy", "MMM dd yyyy"},
            {"dd/MM/yyyy HH:mm", "MMM dd yyyy HH:mm"},
            {"dd/MM/yyyy HH:mm:ss", "MMM dd yyyy HH:mm:ss"},
            {"dd/MM/yyyy HHmm", "MMM dd yyyy HH:mm"},
    };

    /**
     * Returns the executable command object that corresponds to the user input.
     *
     * @return DuckCommand Command object of the specified command class
     * @throws InvalidCommandException If input is not a valid command
     */
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

    public static String parseDate(String date) throws DateTimeParseException {
        for (String[] format : dateTimeFormats) {
            DateTimeFormatter readFormatter = DateTimeFormatter.ofPattern(format[0]);
            DateTimeFormatter writeFormatter = DateTimeFormatter.ofPattern(format[1]);
            try {
                return LocalDateTime.parse(date, readFormatter).format(writeFormatter);
            } catch (DateTimeParseException e) {
                // continue
            } catch (NullPointerException e) {
                break;
            }

            try {
                return LocalDate.parse(date, readFormatter).format(writeFormatter);
            } catch (DateTimeParseException e) {
                // continue
            } catch (NullPointerException e) {
                break;
            }
        }

        throw new DateTimeParseException("", date, 0);
    }

    /**
     * Returns if the given string is a valid exit command
     *
     * @param command Command name
     * @return If string is one of the valid exit commands in COMMAND_EXIT_LIST
     */
    public static boolean isExitCommand(String command) {
        return Duck.COMMAND_EXIT_LIST.contains(command);
    }
}
