package babe.task;

public class Task {
    protected String description;
    protected boolean isDone;
    protected Priority priority;  // Add priority field

    public enum Priority {
        HIGH(1),
        MEDIUM(2),
        LOW(3);

        private final int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public static Priority fromLevel(int level) {
            return switch (level) {
                case 1 -> HIGH;
                case 2 -> MEDIUM;
                case 3 -> LOW;
                default -> throw new IllegalArgumentException("Invalid priority level: " + level);
            };
        }
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.priority = Priority.MEDIUM;  // Default priority
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
        this.priority = Priority.MEDIUM;  // Default priority
    }

    /**
     * Creates a new babe.task with the given description
     */
    public Task(String description, Priority priority) {
        this.description = description;
        this.isDone = false;
        this.priority = priority;
    }

    /**
     * Creates a babe.task with the given description and completion status
     * Used when loading tasks from babe.storage
     */
    public Task(String description, boolean isDone, Priority priority) {
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
    }


    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns whether the babe.task is done
     * Used by babe.storage to save babe.task status
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the babe.task description
     * Used by babe.storage to save babe.task details
     */
    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "][P" + this.priority.getLevel() + "] " + this.description;
    }

    /**
     * Returns a copy of this babe.task
     * Used to prevent external modifications to babe.task data
     */
    public Task copy() {
        return new Task(this.description, this.isDone, this.priority);
    }

}