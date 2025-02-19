// Todo.java
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
     * Constructs a Todo task with a description and priority.
     *
     * @param description The description of the to-do task.
     * @param priority The priority level of the task.
     */
    public Todo(String description, Priority priority) {
        super(description, priority);
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

    /**
     * Constructs a Todo task with a description, completion status, and priority.
     *
     * @param description The description of the to-do task.
     * @param isDone Whether the to-do task is completed.
     * @param priority The priority level of the task.
     */
    public Todo(String description, boolean isDone, Priority priority) {
        super(description, isDone, priority);
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
        return new Todo(this.description, this.isDone, this.priority);
    }
}
