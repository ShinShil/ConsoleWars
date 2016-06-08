package application;


import javafx.application.Application;
import javafx.stage.Stage;
import scenes.Window;



public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    Window window = new Window(primaryStage);
    
    
    window.startMenu();
  }

  public static void main(String[] args) {
    launch(args);
    return;
  }
}
