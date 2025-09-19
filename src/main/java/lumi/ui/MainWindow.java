package lumi.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lumi.Lumi;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Lumi lumi;

    private Image lumiImage = new Image(getClass().getResourceAsStream("/images/Lumi.jpg"));
    private Image userImage = new Image(getClass().getResourceAsStream("/images/User.jpg"));
    private Dialogue dialogue = new Dialogue();

    /**
     * Initializes the UI components when the controller is loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String greeting = dialogue.greet();
        dialogContainer.getChildren().add(
                DialogBox.getLumiDialog(greeting, lumiImage)
        );
    }

    /** Injects the Lumi instance */
    public void setLumi(Lumi d) {
        lumi = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String lumiText = lumi.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getLumiDialog(lumiText, lumiImage)
        );
        userInput.clear();
    }
}
