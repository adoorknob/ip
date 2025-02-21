package duke.task;

public class Deadline extends Task {
    private final String byDate;

    public Deadline(String title, String byDate) {
        super(title);
        this.byDate = byDate;
    }

    public Deadline(String byDate) {
        this.byDate = byDate;
    }

    public String getByDate() {
        return byDate;
    }

    public String toTaskString() {
        return "[D][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle()
                + " (by: " + this.getByDate() + ")";
    }

    public String toFileString() {
        return "D | " + (this.isCompleted() ? "1" : "0")
                + " | " + this.getTitle()
                + " | by: " + this.getByDate();
    }
}