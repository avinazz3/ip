package babe.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");
    private static final DateTimeFormatter STORAGE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param start The start time of the event.
     * @param end The end time of the event.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs an Event task with a description, start time, end time, and completion status.
     *
     * @param description The description of the event.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @param isDone Whether the event is completed.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start time of the event.
     *
     * @return The start time as a LocalDateTime object.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time as a LocalDateTime object.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                start.format(DISPLAY_FORMAT) + " to: " +
                end.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns the start time formatted for storage purposes.
     *
     * @return The start time as a formatted string.
     */
    public String getStorageStartString() {
        return start.format(STORAGE_FORMAT);
    }

    /**
     * Returns the end time formatted for storage purposes.
     *
     * @return The end time as a formatted string.
     */
    public String getStorageEndString() {
        return end.format(STORAGE_FORMAT);
    }

    /**
     * Creates and returns a copy of this Event task.
     *
     * @return A new Event object with the same properties.
     */
    @Override
    public Event copy() {
        return new Event(this.description, this.start, this.end, this.isDone);
    }
}