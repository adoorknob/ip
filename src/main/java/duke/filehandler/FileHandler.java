package duke.filehandler;

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

public class FileHandler {
    String outputFilePath;
    String outputList = "";
    Ui ui;
    TaskList taskList;

    public FileHandler(String outputFilePath) {
        this.outputFilePath = outputFilePath;
        ui = new Ui();
        taskList = new TaskList();
    }

    public TaskList loadOldFile() throws FileNotFoundException {
        File f = new File(outputFilePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String currentLine = s.nextLine();
            outputList += currentLine + "\n";
            parseOldFileEntry(currentLine);
        }
        ui.printFileLoadedMessage();
        return taskList;
    }

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

    private Task addTodoFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();
        return new Todo(title);
    }

    private Task addDeadlineFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();
        int byDateIndex = splitInput[3].indexOf("by");
        String byDate = splitInput[3].substring(byDateIndex + Duck.BY_COMMAND_BUFFER).trim();
        return new Deadline(title, byDate);
    }

    private Task addEventFromOldFile(String oldFileEntry) {
        String[] splitInput = oldFileEntry.split("\\|");
        String title = splitInput[2].trim();
        int fromDateTimeIndex = splitInput[3].indexOf("from");
        String fromDateTime = splitInput[3].substring(fromDateTimeIndex + Duck.FROM_COMMAND_BUFFER).trim();
        int toDateTimeIndex = splitInput[4].indexOf("to");
        String toDateTime = splitInput[4].substring(toDateTimeIndex + Duck.TO_COMMAND_BUFFER).trim();
        return new Event(title, fromDateTime, toDateTime);
    }

    public void updateFile() throws IOException {
        FileWriter fw = new FileWriter(outputFilePath);
        updateOutputList();
        fw.write(outputList);
        fw.close();
    }

    public void updateOutputList() throws IOException {
        outputList = "";
        for (int i = 0; i < taskList.getTasks().size(); i++) {
            outputList += taskList.getTask(i).toFileString() + "\n";
        }
    }
}
