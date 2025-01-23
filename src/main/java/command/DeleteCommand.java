package command;

import task.Task;
import task.TaskList;
import ui.Ui;
import exception.BabeException;

public class DeleteCommand implements Command {
    private int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        Task deletedTask = tasks.deleteTask(targetIndex - 1); // Convert to 0-based index
        ui.showDeletedTask(deletedTask, tasks.size());
    }
}
