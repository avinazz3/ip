package command;
import exception.*;
import task.TaskList;
import ui.Ui;

public interface Command {
    void execute(TaskList tasks, Ui ui) throws BabeException;
}
