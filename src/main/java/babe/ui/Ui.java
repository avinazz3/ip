package babe.ui;

import java.util.ArrayList;
import java.util.Scanner;
import babe.task.Task;
import babe.task.TaskList;


public class Ui {
    private Scanner scanner;
    private static final String DIVIDER = "    ____________________________________________________________";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = """
                 ____        _         \s
                | __ )  __ _| |__   ___\s
                |  _ \\ / _` | '_ \\ / _ \\
                | |_) | (_| | |_) |  __/
                |____/ \\__,_|_.__/ \\___|
                """;
        System.out.println("Hello from\n" + logo);
        showGreeting();
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the list of tasks.
     *
     * @param taskList The TaskList containing the tasks to be displayed.
     */
    public void showList(TaskList taskList) {
        if (taskList.size() == 0) {
            System.out.println("     Your babe.task list is empty!");
            return;
        }
        System.out.println("     Here are the tasks in your list:");
        ArrayList<Task> tasks = taskList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding the new task.
     */
    public void showAddedTask(Task task, int totalTasks) {
        System.out.println("     Got it. I've added this babe.task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was removed.
     * @param remainingTasks The total number of tasks remaining after deletion.
     */
    public void showDeletedTask(Task task, int remainingTasks) {
        System.out.println("     Noted. I've removed this babe.task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + remainingTasks + " tasks in the list.");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showMarkedTask(Task task) {
        System.out.println("     Nice! I've marked this babe.task as done:");
        System.out.println("       " + task);
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showUnmarkedTask(Task task) {
        System.out.println("     OK, I've marked this babe.task as not done yet:");
        System.out.println("       " + task);
    }

    public void showError(String message) {
        System.out.println("     ERROR: " + message);
    }

    public void showGreeting() {
        System.out.println(DIVIDER);
        System.out.println("     Hello! I'm babe.Babe");
        System.out.println("     What can I do for you?");
        System.out.println(DIVIDER);
        System.out.println();
    }

    public void showExit() {
        System.out.println(DIVIDER);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
        System.out.println();
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }
}