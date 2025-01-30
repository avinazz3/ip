package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");
    private static final DateTimeFormatter STORAGE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public Event(String description, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                start.format(DISPLAY_FORMAT) + " to: " +
                end.format(DISPLAY_FORMAT) + ")";
    }

    public String getStorageStartString() {
        return start.format(STORAGE_FORMAT);
    }

    public String getStorageEndString() {
        return end.format(STORAGE_FORMAT);
    }

    @Override
    public Event copy() {
        return new Event(this.description, this.start, this.end, this.isDone);
    }
}