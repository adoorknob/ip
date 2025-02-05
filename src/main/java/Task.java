public abstract class Task {
    private final String title;
    private boolean complete = false;

    public abstract void printTask();

    public Task() {
        this("Untitled");
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return complete;
    }

    public void markAsComplete() {
        this.complete = true;
    }

    public void markAsUncomplete() {
        this.complete = false;
    }
}
