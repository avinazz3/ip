package babe;

import babe.command.Command;
import babe.command.ExitCommand;
import babe.exception.BabeException;
import babe.parser.Parser;
import babe.task.TaskList;
import babe.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main entry point for the babe task management application.
 * This class is responsible for handling user interactions, processing commands, and managing the task list.
 */
public class Babe extends Application {
    private TaskList tasks;
    private Ui ui;

    public Babe() {
        ui = new Ui();
        tasks = new TaskList();
    }

    @Override
    public void start(Stage stage) {
        try {
            // Load FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Babe.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Set up the scene
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Babe Task Manager");

            // Get the controller and set up Babe instance
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setBabe(this);

            // Show the window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the UI instance
     */
    public Ui getUi() {
        return ui;
    }

    /**
     * Processes a command and returns the response
     *
     * @param input The command input from the user
     * @return The response string to be displayed
     */
    public String getResponse(String input) {
        try {
            StringBuilder response = new StringBuilder();
            response.append(ui.getDivider()).append("\n");
            response.append("> ").append(input).append("\n");
            response.append(ui.getDivider()).append("\n");

            Command c = Parser.parseCommand(input);
            String result = c.execute(tasks, ui);
            response.append(result);

            if (c instanceof ExitCommand) {
                response.append("\n").append(ui.getExitMessage());
                System.exit(0);
            }

            return response.toString();
        } catch (BabeException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }
}