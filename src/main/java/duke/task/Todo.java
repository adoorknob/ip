package duke.task;

/**
 * Represents _Todo task
 */

public class Todo extends Task {

    /**
     * Contructs _Todo with no params
     */
    public Todo() {
        super();
    }

    /**
     * Constructs _Todo with title
     * @param title Name of _Todo task
     */
    public Todo(String title) {
        super(title);
    }


    /**
     * Returns task in String to print to standard output
     *
     * @return taskString in Task format
     */
    public String toTaskString() {
        return "[T][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle();
    }

    /**
     * Returns task in String to add to file
     *
     * @return taskString in File format
     */
    public String toFileString() {
        return "T | " + (this.isCompleted() ? "1" : "0") + " | " + this.getTitle();
    }
}
