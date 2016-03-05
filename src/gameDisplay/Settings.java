package gameDisplay;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class Settings {
	public void start(Window window) {
		BorderPane root = new BorderPane();
		Label msg = new Label("Coming soon");
		msg.setFont(new Font("Arial", 36));
		msg.setAlignment(Pos.CENTER);
		root.setCenter(msg);
		Scene scene = new Scene(root, 800,600);
		window.setScene(scene);
		window.show();
	}
}
