package command;

import task.Task;
import task.TaskList;
import ui.Ui;
import exception.BabeException;

public class ListCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showList(tasks);
    }
}