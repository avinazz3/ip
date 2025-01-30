package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

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
