package babe.task;

public class Todo extends Task {

    /**
     * Constructs a Todo task with a description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a Todo task with a description and completion status.
     *
     * @param description The description of the to-do task.
     * @param isDone Whether the to-do task is completed.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Creates and returns a copy of this Todo task.
     *
     * @return A new Todo object with the same properties.
     */
    @Override
    public Todo copy() {
        return new Todo(this.description, this.isDone);
    }
}