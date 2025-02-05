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
}
