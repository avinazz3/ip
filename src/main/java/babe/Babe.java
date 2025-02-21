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
            FXMLLoader fxmlLoader = new FXMLLoader(Babe.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Configure stage
            stage.setScene(scene);
            stage.setTitle("Babe Task Manager");
            stage.setMinWidth(300);
            stage.setMinHeight(400);

            // Get controller and set up Babe instance
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setBabe(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ui getUi() {
        return ui;
    }

    public String getResponse(String input) {
        try {
            Command c = Parser.parseCommand(input);
            String result = c.execute(tasks, ui);

            if (c instanceof ExitCommand) {
                System.exit(0);
            }

            return result;
        } catch (BabeException e) {
            return "ERROR: " + e.getMessage();
        }
    }
}