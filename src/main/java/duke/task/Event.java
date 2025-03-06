package duke.task;

/**
 * Represents Event task
 */

public class Event extends Task {
    private final String fromDateTime;
    private final String toDateTime;

    /**
     * Constructs Event object with from datetime and to datetime
     * @param fromDateTime Datetime that the event starts
     * @param toDateTime Datetime that the event ends
     */
    public Event(String fromDateTime, String toDateTime) {
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    /**
     * Constructs event object with name of event, from datetime and to datetime
     * @param title Name of event
     * @param fromDateTime Datetime that the event starts
     * @param toDateTime Datetime that the event ends
     */
    public Event(String title, String fromDateTime, String toDateTime) {
        super(title);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    /**
     * Returns datetime when event ends
     * @return fromDateTime Datetime that the event ends
     */
    public String getFromDateTime() {
        return fromDateTime;
    }

    /**
     * Returns datetime when event starts
     * @return toDatetime Datetime that the event starts
     */
    public String getToDateTime() {
        return toDateTime;
    }

    /**
     * Returns task in String to print to standard output
     *
     * @return taskString in Task format
     */
    @Override
    public String toTaskString() {
        return "[E][" + (this.isCompleted() ? "X" : " ") + "] " + this.getTitle()
                + " (from: " + this.getFromDateTime()
                + ", to: " + this.getToDateTime()
                + ")";
    }

    /**
     * Returns task in String to add to file
     *
     * @return taskString in File format
     */
    @Override
    public String toFileString() {
        return "E | " + (this.isCompleted() ? "1" : "0")
                + " | " + this.getTitle()
                + " | from: " + this.getFromDateTime()
                + " | to: " + this.getToDateTime();
    }
}
