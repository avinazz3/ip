package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class DeleteCommand implements Command {
    private int targetIndex;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param targetIndex The 1-based index of the task to be deleted.
     */
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the delete command by removing the specified task from the task list
     * and displaying a confirmation message.
     *
     * @param tasks The task list from which the task will be deleted.
     * @param ui The UI handler for displaying messages.
     * @throws BabeException If the provided index is out of range.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws BabeException {
        Task deletedTask = tasks.deleteTask(targetIndex - 1); // Convert to 0-based index
        return ui.getDeletedTaskMessage(deletedTask, tasks.size());
    }
}
