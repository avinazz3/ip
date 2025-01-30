package babe;

import babe.command.Command;
import babe.command.ExitCommand;
import babe.exception.BabeException;
import babe.parser.Parser;
import babe.task.TaskList;
import babe.ui.Ui;

public class Babe {
    private TaskList tasks;
    private Ui ui;

    public Babe() {
        ui = new Ui();
        tasks = new TaskList();
    }

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

    public static void main(String[] args) {
        new Babe().run();
    } //A-MoreOOP
}