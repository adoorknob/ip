package duke.task;

public class Todo extends Task {

    public Todo() {
        super();
    }

    public Todo(String title) {
        super(title);
    }

    public void printTask() {
        System.out.println("  [T][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle());
    }

    public String toString() {
        return "T | " + (this.isCompleted() ? "1" : "0") + " | " + this.getTitle();
    }
}
