package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Room room = new Room(2);
		room.startGame();
		/*Player pls[] = new Player[2];
		pls[0] = new Player("Player1");
		pls[1] = new Player("Player2");
		pls[1].show();		
		pls[0].Cast("fire Player2", pls);
		pls[1].show();*/
		//launch(args);
		return;
	}
}
