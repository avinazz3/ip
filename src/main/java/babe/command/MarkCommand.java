package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class MarkCommand implements Command {
    private int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        Task task = tasks.getTask(targetIndex - 1);
        task.markAsDone();
        ui.showMarkedTask(task);
    }
}
