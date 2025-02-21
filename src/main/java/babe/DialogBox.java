package babe;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private static final double AVATAR_SIZE = 32.0;

    private DialogBox(String text, Image img, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            // Configure text
            dialog.setText(text);
            dialog.setWrapText(true);
            dialog.setMinWidth(100);
            dialog.setMaxWidth(400);

            // Configure image
            displayPicture.setImage(img);
            displayPicture.setFitHeight(AVATAR_SIZE);
            displayPicture.setFitWidth(AVATAR_SIZE);

            // Add spacing and padding
            setSpacing(8);
            setPadding(new Insets(8));

            // Create flexible spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Clear existing children (to avoid duplicates)
            getChildren().clear();

            // Style based on message type
            if (isUser) {
                // User message styling
                setAlignment(Pos.CENTER_RIGHT);
                dialog.setStyle("-fx-background-color: #0084ff; -fx-text-fill: white; " +
                        "-fx-padding: 8 12; -fx-background-radius: 15;");
                getChildren().addAll(spacer, dialog, displayPicture);
            } else {
                // Bot message styling
                setAlignment(Pos.CENTER_LEFT);
                dialog.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                        "-fx-padding: 8 12; -fx-background-radius: 15;");
                getChildren().addAll(displayPicture, dialog, spacer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    public static DialogBox getBabeDialog(String text, Image img) {
        return new DialogBox(text, img, false);
    }

    public static DialogBox getBabeErrorDialog(String text, Image img) {
        DialogBox errorBox = new DialogBox(text, img, false);
        errorBox.dialog.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #c62828; " +
                "-fx-padding: 8 12; -fx-background-radius: 15;");
        return errorBox;
    }
}