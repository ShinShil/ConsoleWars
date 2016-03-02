package application;

import javafx.stage.Stage;

public class CommonDisplay {
	public static void confirmCloseProgram(Stage window) {
		boolean answer = ConfirmBox.display("Exit?", "Are you sure you want to exit?");
		if(answer) {
			window.close();
		}
	}
}
