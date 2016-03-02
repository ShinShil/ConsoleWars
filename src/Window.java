package application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Window {
	private Stage window;
	
	public Window(Stage stage) {
		window = stage;
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmCloseProgram(window);
		});
		
		window.setTitle("Console wars");
		window.getIcons().add(new Image("file:resources/icon.png"));
	}
	

	public Stage getStage() {
		return window;
	}
	
	public void startSettings() {
		Settings set = new Settings();
		set.start(this);
	}
	
	public void setScene(Scene scene) {
		window.setScene(scene);
	}
	
	public void show() {
		window.show();
	}
	
	public void startDuel() {
		DuelGame duel = new DuelGame();
		duel.start(this);
	}
	
	public void startMenu() {
		MainMenu menu = new MainMenu();
		menu.start(this);
	}
	
	public void close() {
		window.close();
	}
	
	public void confirmCloseProgram(Stage window) {
		boolean answer = ConfirmBox.display("Exit?", "Are you sure you want to exit?");
		if(answer) {
			window.close();
		}
	}
	
}
