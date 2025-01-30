package babe.command;
import babe.exception.*;
import babe.task.TaskList;
import babe.ui.Ui;

public interface Command {
    void execute(TaskList tasks, Ui ui) throws BabeException;
}
