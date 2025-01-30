package babe.command;

import babe.task.TaskList;
import babe.ui.Ui;

public class ExitCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showExit();
    }
}
