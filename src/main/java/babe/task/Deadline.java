// Deadline.java
package babe.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");
    private static final DateTimeFormatter STORAGE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a Deadline task with a description and due date.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time of the deadline.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a Deadline task with a description, due date, and priority.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time of the deadline.
     * @param priority The priority level of the task.
     */
    public Deadline(String description, LocalDateTime by, Priority priority) {
        super(description, priority);
        this.by = by;
    }

    /**
     * Constructs a Deadline task with a description, due date, and completion status.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time of the deadline.
     * @param isDone Whether the deadline task is completed.
     */
    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Constructs a Deadline task with a description, due date, completion status, and priority.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time of the deadline.
     * @param isDone Whether the deadline task is completed.
     * @param priority The priority level of the task.
     */
    public Deadline(String description, LocalDateTime by, boolean isDone, Priority priority) {
        super(description, isDone, priority);
        this.by = by;
    }

    /**
     * Returns the due date and time of the deadline.
     *
     * @return The due date as a LocalDateTime object.
     */
    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns the due date formatted for storage purposes.
     *
     * @return The due date as a formatted string.
     */
    public String getStorageString() {
        return by.format(STORAGE_FORMAT);
    }

    /**
     * Creates and returns a copy of this Deadline task.
     *
     * @return A new Deadline object with the same properties.
     */
    @Override
    public Deadline copy() {
        return new Deadline(this.description, this.by, this.isDone, this.priority);
    }
}