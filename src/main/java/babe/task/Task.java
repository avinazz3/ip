package babe.task;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new babe.task with the given description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a babe.task with the given description and completion status
     * Used when loading tasks from babe.storage
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
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

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns a copy of this babe.task
     * Used to prevent external modifications to babe.task data
     */
    public Task copy() {
        return new Task(this.description, this.isDone);
    }
}