package babe.command;

import babe.task.Task;
import babe.task.TaskList;
import babe.ui.Ui;
import babe.exception.BabeException;
import java.util.List;

public class FindCommand implements Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified search keyword.
     *
     * @param keyword The keyword to search for in tasks.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks containing the keyword.
     *
     * @param tasks The task list to search through.
     * @param ui The UI handler for formatting messages.
     * @return A string containing the search results.
     * @throws BabeException If the search keyword is empty.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws BabeException {
        if (keyword.trim().isEmpty()) {
            throw new BabeException("The search keyword cannot be empty!");
        }

        List<Task> matchingTasks = tasks.findTasks(keyword);
        StringBuilder result = new StringBuilder();

        if (matchingTasks.isEmpty()) {
            result.append("No matching tasks found.");
        } else {
            result.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                result.append(String.format("%d.%s\n", (i + 1), matchingTasks.get(i)));
            }
        }

        return result.toString();
    }
}