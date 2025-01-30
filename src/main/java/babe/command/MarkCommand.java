package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class MarkCommand implements Command {
    private int targetIndex;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param targetIndex The 1-based index of the task to be marked as done.
     */
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the mark command by updating the task to be marked as done
     * and displaying a confirmation message.
     *
     * @param tasks The task list in which the task will be marked.
     * @param ui The UI handler for displaying messages.
     * @throws BabeException If the provided index is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        Task task = tasks.getTask(targetIndex - 1);
        task.markAsDone();
        ui.showMarkedTask(task);
    }
}
