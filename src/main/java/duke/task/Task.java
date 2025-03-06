package duke.task;

/**
 * Generic class representing Tasks
 */

public abstract class Task {
    private final String title;
    private boolean complete = false;

    /**
     * Returns task in String to print to standard output
     *
     * @return taskString in Task format
     */
    public abstract String toTaskString();

    /**
     * Returns task in String to add to file
     *
     * @return taskString in File format
     */
    public abstract String toFileString();

    /**
     * Constructor with no params for Task
     */
    public Task() {
        this("Untitled");
    }

    /**
     * Constructor with task name
     *
     * @param title
     */
    public Task(String title) {
        this.title = title;
    }

    /**
     * Returns name of task
     *
     * @return title Name of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return if task is completed
     *
     * @return complete If task is completed
     */
    public boolean isCompleted() {
        return complete;
    }

    /**
     * Change status of task to completed
     */
    public void markAsComplete() {
        this.complete = true;
    }

    /**
     * Change status of task to not completed
     */
    public void markAsIncomplete() {
        this.complete = false;
    }

    public boolean contains(String searchTerm) {
        return title.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
