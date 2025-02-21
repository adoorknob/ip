package duke.task;

public class Todo extends Task {

    public Todo() {
        super();
    }

    public Todo(String title) {
        super(title);
    }

    public String toTaskString() {
        return "[T][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle();
    }

    public String toFileString() {
        return "T | " + (this.isCompleted() ? "1" : "0") + " | " + this.getTitle();
    }
}
