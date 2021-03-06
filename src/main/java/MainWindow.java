import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
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

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Martin.png"));

    /**
     * Initializes the window with a greeting dialog box.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("Please include the data folder with the "
                        + "tasks.txt file in the same directory "
                        + "as this jar file for storage processing!", dukeImage),
                DialogBox.getDukeDialog("Hello! Martin here!\nWhat can I do?", dukeImage)
        );
    }

    /**
     * Sets the duke class required for the main to the specific duke instance.
     * @param d The specified duke instance.
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply.
     * Follwong which, appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String in = userInput.getText();
        String out = duke.getResponse(in);
        if (out.equals(".")) {
            System.exit(0);
        } else {
            if (duke.isStoragePresent()) {
                dialogContainer.getChildren().addAll(
                        DialogBox.getUserDialog(in, userImage),
                        DialogBox.getDukeDialog(out, dukeImage)
                );
            } else {
                dialogContainer.getChildren().addAll(
                        DialogBox.getUserDialog(in, userImage),
                        DialogBox.getDukeDialog("Please include the data folder with the "
                                + "tasks.txt file in the same directory "
                                + "as this jar file for storage processing!", dukeImage),
                        DialogBox.getDukeDialog(out, dukeImage)
                );
            }
            userInput.clear();
        }
    }
}