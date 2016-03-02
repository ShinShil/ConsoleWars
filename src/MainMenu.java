package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu {
	
	private void setBtn(Button btn) {
		btn.setCursor(Cursor.HAND);
		btn.defaultButtonProperty().bind(btn.focusedProperty());
		btn.setPrefWidth(200);
		btn.setFont(new Font("Arial", 24));
	}
	
	public void start(Window window) {
		VBox root = new VBox();
		VBox menu = new VBox();
		Label header = new Label("Console Wars");
		header.setFont(new Font("arial", 36));
		header.setPadding(new Insets(20, 0, 20, 0));
		header.setAlignment(Pos.BASELINE_CENTER);
		header.setPrefWidth(600);
		CornerRadii borderRadiusBtn = new CornerRadii(5, true);
		Button duelMode = new Button("Start Duel");
		duelMode.setOnAction(e -> {
			window.startDuel();
		});
		Button settings = new Button("Settings");
		settings.setOnAction(e -> {
			window.startSettings();
		});
		//settings.setBackground(new Background(new BackgroundFill(Color.BROWN, borderRadiusBtn, new Insets(0))));
		Button exit = new Button("Exit Game");
		exit.setOnAction(e -> {
			window.confirmCloseProgram(window.getStage());
		});
		setBtn(duelMode);
		setBtn(settings);
		setBtn(exit);
		menu.getChildren().addAll(duelMode, settings, exit);
		menu.setAlignment(Pos.BASELINE_CENTER);
		menu.setSpacing(15);
		
		root.getChildren().addAll(header, menu);
		Scene scene = new Scene(root, 600,400);
		window.setScene(scene);
		window.show();
	}
}
