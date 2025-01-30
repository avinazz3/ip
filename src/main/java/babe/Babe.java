package babe;

import babe.command.Command;
import babe.command.ExitCommand;
import babe.exception.BabeException;
import babe.parser.Parser;
import babe.task.TaskList;
import babe.ui.Ui;

/**
 * The main entry point for the babe task management application.
 * This class is responsible for handling user interactions, processing commands, and managing the task list.
 */
public class Babe {
    private TaskList tasks;
    private Ui ui;

    public Babe() {
        ui = new Ui();
        tasks = new TaskList();
    }

    /**
     * Starts the application and enters the main command loop.
     * The loop continuously waits for user input, processes commands, and updates the UI accordingly.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                ui.showLine();
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command c = Parser.parseCommand(fullCommand);
                c.execute(tasks, ui);
                isExit = (c instanceof ExitCommand);

                if (!isExit) {
                    System.out.println();
                }
            } catch (BabeException e) {
                ui.showError(e.getMessage());
                System.out.println();
            }
        }
    }

    /**
     * The entry point for the application. Creates a new Babe instance and runs it.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Babe().run();
    } //A-MoreOOP
}