import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Office Hour Queue class
 * allows for students to be added and removed from a queue
 * only able to remove with privelage (know password)
 * @author aprasad72
 * @version 1.0
 */
public class OfficeHourQueue extends Application {

    private int currentCounter = 0;
    private int maxCounter = 0;
    /**
     * @param stage (Stage object in javafx)
     * provides platform for viewing GUI interface
     * includes additions of Labels and buttons (add and deque)
     * with show method, will display all components
     */
    @Override public void start(Stage stage) {
        ObservableList<String> studentList =
            FXCollections.observableArrayList();
        ListView<String> listView = new ListView<String>(studentList);

        Button addButton = new Button();
        addButton.setText("Add Student");

        Button dequeButton = new Button();
        dequeButton.setText("Deque Student");

        Label currentCount = new Label("Current Number Of Students"
            + " In Queue: 0");
        Label maxCount = new Label("Max Number Of Students In Queue: 0");

        TextField inputField = new TextField();

        addButton.setOnAction(e -> {
                studentList.add(inputField.getText());
                inputField.setText("");
                inputField.requestFocus();
                currentCount.setText("Current Number Of Students In Queue: "
                    + (++currentCounter));
                if (currentCounter > maxCounter) {
                    maxCounter = currentCounter;
                }
                maxCount.setText("Max Number Of Students In Queue: "
                    + (maxCounter));
            });

        dequeButton.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Privelage Check");
                dialog.setHeaderText("Confirmation");
                dialog.setContentText("Please Enter Password: ");
                dialog.showAndWait().ifPresent(name -> {
                        if (name.equals("CS1321R0X")) {
                            studentList.remove(0);
                            currentCount.setText("Current Number Of Students "
                                + "In Queue: " + (--currentCounter));
                        }
                    });
            });

        addButton.disableProperty().bind(
            Bindings.isEmpty(inputField.textProperty()));
        HBox entryBox = new HBox();
        entryBox.getChildren().addAll(inputField, addButton, dequeButton);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(currentCount, maxCount, listView, entryBox);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("CS 1321 Office Hours Queue");
        stage.show();
    }
}
