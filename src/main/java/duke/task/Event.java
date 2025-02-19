package duke.task;

public class Event extends Task {
    private final String fromDateTime;
    private final String toDateTime;

    public Event(String fromDateTime, String toDateTime) {
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public Event(String title, String fromDateTime, String toDateTime) {
        super(title);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public String getToDateTime() {
        return toDateTime;
    }

    public void printTask() {
        System.out.println("[E][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle()
                + " (from: " + this.getFromDateTime()
                + ", to: " + this.getToDateTime()
                + ")");
    }
    public String toString() {
        return "E | " + (this.isCompleted() ? "1" : "0")
                + " | " + this.getTitle()
                + " | from: " + this.getFromDateTime()
                + " | to: " + this.getToDateTime();
    }
}
