package duke.storage;

import duke.Duck;
import duke.exception.InvalidCommandException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the file handler for Duck
 */

public class Storage {
    String outputFilePath;
    String outputList = "";
    Ui ui;
    TaskList taskList;

    /**
     * Constructs the Storage class
     * @param outputFilePath filepath to output file to read from/write to
     */
    public Storage(String outputFilePath) {
        this.outputFilePath = outputFilePath;
        ui = new Ui();
        taskList = new TaskList();
    }

    /**
     * Adds old task list to current working task list.
     *
     * @return taskList Task list updated with old tasks
     * @throws FileNotFoundException if unable to find file based on given file path
     */
    public TaskList loadOldFile() throws FileNotFoundException {
        File f = new File(outputFilePath);
        try {
            if (f.createNewFile()) {
                ui.printFileCreated(outputFilePath);
            } else {
                ui.printFileLoadedMessage();
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String currentLine = s.nextLine();
            outputList += currentLine + "\n";
            parseOldFileEntry(currentLine);
        }
        return taskList;
    }

    /**
     * Parses one line in the output file. Called by loadOldFile() for each line of the file.
     *
     * @param line Line in file representing task
     * @throws FileNotFoundException If exception is thrown while adding a task
     */
    private void parseOldFileEntry(String line) throws FileNotFoundException {
        String[] splitInput = line.split("\\|");
        Task newTask;
        try {
            switch (splitInput[0].trim()) {
            case "T": {
                newTask = addTodoFromOldFile(line);
                break;
            }
            case "D": {
                newTask = addDeadlineFromOldFile(line);
                break;
            }
            case "E": {
                newTask = addEventFromOldFile(line);
                break;
            }
            default: {
                throw new InvalidCommandException();
            }
            }
        } catch (Exception e) {
            ui.printParserError(line);
            throw new FileNotFoundException();
        }

        taskList.addTask(newTask);
    }

    /**
     * Returns _Todo task object.
     *
     * @param oldFileEntry Input string to parse to a _Todo object
     * @return newTask _Todo task
     */
    private Task addTodoFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();

        Task newTask = new Todo(title);
        if (splitInput[1].trim().equals("1")) {
            newTask.markAsComplete();
        }

        return newTask;
    }

    /**
     * Returns Deadline task object.
     *
     * @param oldFileEntry Input string to parse to a Deadline object
     * @return newTask Deadline task
     */
    private Task addDeadlineFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();
        int byDateIndex = splitInput[3].indexOf("by");
        String byDate = splitInput[3].substring(byDateIndex + Duck.BY_COMMAND_BUFFER).trim();

        Task newTask = new Deadline(title, byDate);
        if (splitInput[1].trim().equals("1")) {
            newTask.markAsComplete();
        }

        return newTask;
    }

    /**
     * Returns Event task object.
     *
     * @param oldFileEntry Input string to parse to an Event object
     * @return newTask Event task
     */
    private Task addEventFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();
        int fromDateTimeIndex = splitInput[3].indexOf("from");
        String fromDateTime = splitInput[3].substring(fromDateTimeIndex + Duck.FROM_COMMAND_BUFFER).trim();
        int toDateTimeIndex = splitInput[4].indexOf("to");
        String toDateTime = splitInput[4].substring(toDateTimeIndex + Duck.TO_COMMAND_BUFFER).trim();

        Task newTask = new Event(title, fromDateTime, toDateTime);
        if (splitInput[1].trim().equals("1")) {
            newTask.markAsComplete();
        }

        return newTask;
    }

    /**
     * Updates the string to output to file
     *
     * @throws IOException If file is unable to write to file
     */
    public void updateFile() throws IOException {
        FileWriter fw = new FileWriter(outputFilePath);
        updateOutputList();
        fw.write(outputList);
        fw.close();
    }

    public void updateOutputList() {
        outputList = "";
        for (int i = 0; i < taskList.getTasks().size(); i++) {
            outputList += taskList.getTask(i).toFileString() + "\n";
        }
    }
}
