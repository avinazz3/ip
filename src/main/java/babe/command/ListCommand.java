package babe.command;

import babe.task.TaskList;
import babe.ui.Ui;

public class ListCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui) {
        return ui.getListView(tasks);
    }
}