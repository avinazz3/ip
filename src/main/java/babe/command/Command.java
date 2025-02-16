package babe.command;
import babe.exception.*;
import babe.task.TaskList;
import babe.ui.Ui;

/**
 * Interface for all commands in the application.
 * Each command should return a String representing its execution result.
 */
public interface Command {
    /**
     * Executes the command and returns the result as a string.
     *
     * @param tasks The task list to perform operations on
     * @param ui The UI component for formatting messages
     * @return A string containing the result of the command execution
     * @throws BabeException if there is an error during command execution
     */
    String execute(TaskList tasks, Ui ui) throws BabeException;
}