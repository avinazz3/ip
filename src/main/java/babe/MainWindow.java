package babe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

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
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private final Image babeImage = new Image(this.getClass().getResourceAsStream("/images/babe.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        userInput.setOnAction(event -> handleUserInput());
        userInput.requestFocus();
    }

    public void setBabe(Babe babe) {
        this.babe = babe;
        dialogContainer.getChildren().add(
                DialogBox.getBabeDialog(babe.getUi().getWelcomeMessage(), babeImage)
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input, userImage)
        );

        String response = babe.getResponse(input);
        DialogBox responseBox = response.startsWith("ERROR:")
                ? DialogBox.getBabeErrorDialog(response, babeImage)
                : DialogBox.getBabeDialog(response, babeImage);

        dialogContainer.getChildren().add(responseBox);
        userInput.clear();
    }
}