package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class AddCommand implements Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        tasks.addTask(task);
        ui.showAddedTask(task, tasks.size());
    }
}