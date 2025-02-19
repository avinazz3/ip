package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

/**
 * Command to set the priority level of a task in the task list.
 */
public class SetPriorityCommand implements Command {
    private final int taskIndex;
    private final int priorityLevel;

    /**
     * Constructs a SetPriorityCommand to change the priority of a task.
     *
     * @param taskIndex The index of the task to modify (0-based)
     * @param priorityLevel The new priority level to set (1-3)
     */
    public SetPriorityCommand(int taskIndex, int priorityLevel) {
        this.taskIndex = taskIndex;
        this.priorityLevel = priorityLevel;
    }

    /**
     * Executes the command to set the priority of the specified task.
     * Sets the priority and returns a message indicating the change.
     *
     * @param tasks The task list containing the task to modify
     * @param ui The UI component for formatting messages
     * @return A string containing the result of setting the priority
     * @throws BabeException if the task index is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws BabeException {
        try {
            Task task = tasks.getTask(taskIndex);
            task.setPriority(Task.Priority.fromLevel(priorityLevel));
            return ui.getUpdatedPriorityMessage(task);
        } catch (IndexOutOfBoundsException e) {
            throw new BabeException("The task number is invalid!");
        } catch (IllegalArgumentException e) {
            throw new BabeException("Priority level must be between 1 (High) and 3 (Low)!");
        }
    }
}