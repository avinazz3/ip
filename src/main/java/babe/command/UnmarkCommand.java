package babe.command;
import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class UnmarkCommand implements Command {
    private int targetIndex;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     *
     * @param targetIndex The 1-based index of the task to be marked as not done.
     */
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the unmark command by updating the task to be marked as not done
     * and displaying a confirmation message.
     *
     * @param tasks The task list in which the task will be unmarked.
     * @param ui The UI handler for displaying messages.
     * @throws BabeException If the provided index is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        Task task = tasks.getTask(targetIndex - 1);
        task.markAsNotDone();
        ui.showUnmarkedTask(task);
    }
}
