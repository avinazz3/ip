package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;

public class AddCommand implements Command {
    private Task task;

    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the command by adding the task to the task list and displaying a confirmation message.
     *
     * @param tasks The task list to which the task will be added.
     * @param ui The UI handler for displaying messages.
     * @throws BabeException If an error occurs while adding the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws BabeException {
        tasks.addTask(task);
        ui.showAddedTask(task, tasks.size());
    }
}