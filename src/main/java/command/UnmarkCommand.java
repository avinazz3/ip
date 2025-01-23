package command;
import task.Task;
import task.TaskList;
import ui.Ui;
import exception.BabeException;

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
