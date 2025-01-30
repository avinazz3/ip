package babe.command;
import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class UnmarkCommand implements Command {
    private int targetIndex;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        Task task = tasks.getTask(targetIndex - 1);
        task.markAsNotDone();
        ui.showUnmarkedTask(task);
    }
}
