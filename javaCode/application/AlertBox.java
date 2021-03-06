package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
  public static void display(String title, String message) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    Label label = new Label(message);
    Button closeWindow = new Button("Close the window");
    closeWindow.setOnAction(e -> window.close());

    VBox layout = new VBox(20);
    layout.getChildren().addAll(label, closeWindow);
    layout.setAlignment(Pos.CENTER);
    Scene scene = new Scene(layout, 300, 70);
    window.setScene(scene);
    window.showAndWait();
  }
}
