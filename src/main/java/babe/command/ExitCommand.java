package babe.command;

import babe.task.TaskList;
import babe.ui.Ui;

public class ExitCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui) {
        return ui.getExitMessage();
    }
}
