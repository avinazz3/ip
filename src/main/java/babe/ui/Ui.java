package babe.ui;

import babe.task.Task;
import babe.task.TaskList;
import java.util.ArrayList;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";

    public String getWelcomeMessage() {
        String logo = """
                 ____        _         \s
                | __ )  __ _| |__   ___\s
                |  _ \\ / _` | '_ \\ / _ \\
                | |_) | (_| | |_) |  __/
                |____/ \\__,_|_.__/ \\___|
                """;
        return "Hello from\n" + logo + "\n" + getGreetingMessage();
    }

    /**
     * Returns the string representation of the task list.
     *
     * @param taskList The TaskList containing the tasks to be displayed.
     * @return A formatted string containing the task list.
     */
    public String getListView(TaskList taskList) {
        if (taskList.size() == 0) {
            return "Your task list is empty!";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        ArrayList<Task> tasks = taskList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("%d.%s\n", (i + 1), tasks.get(i)));
        }
        return sb.toString();
    }

    /**
     * Returns a message indicating that a task has been added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding the new task.
     * @return A formatted string containing the add confirmation message.
     */
    public String getAddedTaskMessage(Task task, int totalTasks) {
        return String.format("Got it. I've added this task:\n" +
                "  %s\n" +
                "Now you have %d tasks in the list.", task, totalTasks);
    }

    /**
     * Returns a message indicating that a task has been deleted.
     *
     * @param task The task that was removed.
     * @param remainingTasks The total number of tasks remaining after deletion.
     * @return A formatted string containing the delete confirmation message.
     */
    public String getDeletedTaskMessage(Task task, int remainingTasks) {
        return String.format("Noted. I've removed this task:\n" +
                "  %s\n" +
                "Now you have %d tasks in the list.", task, remainingTasks);
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @return A formatted string containing the mark-as-done confirmation message.
     */
    public String getMarkedTaskMessage(Task task) {
        return String.format("Nice! I've marked this task as done:\n  %s", task);
    }

    /**
     * Returns a message indicating that a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     * @return A formatted string containing the mark-as-not-done confirmation message.
     */
    public String getUnmarkedTaskMessage(Task task) {
        return String.format("OK, I've marked this task as not done yet:\n  %s", task);
    }

    /**
     * Returns an error message.
     *
     * @param message The error message to be displayed.
     * @return A formatted string containing the error message.
     */
    public String getErrorMessage(String message) {
        return "ERROR: " + message;
    }

    /**
     * Returns the greeting message.
     *
     * @return A formatted string containing the greeting message.
     */
    public String getGreetingMessage() {
        return DIVIDER + "\n" +
                "Hello! I'm Babe\n" +
                "What can I do for you?\n" +
                DIVIDER;
    }

    /**
     * Returns the exit message.
     *
     * @return A formatted string containing the exit message.
     */
    public String getExitMessage() {
        return DIVIDER + "\n" +
                "Bye. Hope to see you again soon!\n" +
                DIVIDER;
    }

    /**
     * Returns the divider line.
     *
     * @return The divider line string.
     */
    public String getDivider() {
        return DIVIDER;
    }
}