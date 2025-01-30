import command.Command;
import command.ExitCommand;
import exception.BabeException;
import parser.Parser;
import task.TaskList;
import ui.Ui;

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
    }
}