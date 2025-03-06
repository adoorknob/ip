package duke.task;

/**
 * Represents a Deadline task
 */

public class Deadline extends Task {
    private final String byDate;

    /**
     * Constructs the Deadline object
     *
     * @param title Title of the deadline
     * @param byDate Date for deadline
     */
    public Deadline(String title, String byDate) {
        super(title);
        this.byDate = byDate;
    }

    /**
     * Constructs the Deadline object with no title
     *
     * @param byDate Date for deadline
     */
    public Deadline(String byDate) {
        this.byDate = byDate;
    }

    /**
     * Returns deadline
     *
     * @return byDate Deadline of deadline object
     */
    public String getByDate() {
        return byDate;
    }

    /**
     * Returns task in String to print to standard output
     *
     * @return taskString in Task format
     */
    @Override
    public String toTaskString() {
        return "[D][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle()
                + " (by: " + this.getByDate() + ")";
    }

    /**
     * Returns task in String to add to file
     *
     * @return taskString in File format
     */
    @Override
    public String toFileString() {
        return "D | " + (this.isCompleted() ? "1" : "0")
                + " | " + this.getTitle()
                + " | by: " + this.getByDate();
    }
}