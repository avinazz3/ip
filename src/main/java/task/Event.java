package task;

public class Event extends Task {
    protected String start;
    protected String end;

    /**
     * Creates a new event task
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Creates an event task with the given completion status
     * Used when loading tasks from storage
     */
    public Event(String description, String start, String end, boolean isDone) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the event start time
     * Used by storage to save event details
     */
    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    @Override
    public Event copy() {
        return new Event(this.description, this.start, this.end, this.isDone);
    }
}