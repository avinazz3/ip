package task;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with the given description and completion status
     * Used when loading tasks from storage
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
     * Returns whether the task is done
     * Used by storage to save task status
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the task description
     * Used by storage to save task details
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns a copy of this task
     * Used to prevent external modifications to task data
     */
    public Task copy() {
        return new Task(this.description, this.isDone);
    }
}