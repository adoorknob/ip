public class Task {
    private String title;
    private boolean completed = false;

    public Task() {
        this("null");
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public void unmarkAsCompleted() {
        this.completed = false;
    }
}
