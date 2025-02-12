package babe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Babe babe;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image babeImage = new Image(this.getClass().getResourceAsStream("/images/babe.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setBabe(Babe babe) {
        this.babe = babe;
        // Move the welcome message setup here
        dialogContainer.getChildren().addAll(
                DialogBox.getBabeDialog(babe.getUi().getWelcomeMessage(), babeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        String response = babe.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getBabeDialog(response, babeImage));

        userInput.clear();
    }
}