package application;
	
import gameDisplay.Window;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Window window = new Window(primaryStage);
		window.startPvp();
	}
	
	public static void main(String[] args) {
		//Room room = new Room(2);
		//room.startGame();
		launch(args);
		return;
	}
}
