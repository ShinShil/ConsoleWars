package pvp;


import animation.AnimationFire;
import animation.FireEmmiter;
import gameDisplay.GameScene;
import gameDisplay.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Pvp implements GameScene {
	
	private GraphicsContext g;
	private PvpControl control;
	TextField input = new TextField();
	@Override
	public int start(Window window) {
		Scene scene = new Scene(createContent());
		
		if(window!=null) {
			window.close();
		}
		window.setScene(scene);
		window.show();
		
		return 0;
	}
	
	private Parent createContent() {
		int dummyAmount = 9;
		int dummyW = 80;
		int dummyH = 80;
		int dummyFont = 24;
		int dummyInsetsPadding = 4;
		int rootW = dummyW*dummyAmount;
		int canvasW = rootW;
		int canvasH = 600;
		
		
		Pane root = new Pane();		
		root.setBackground(new Background(new BackgroundFill(Color.BLACK , new CornerRadii(0), new Insets(0))));

		//hBox has only labels, so player1.getChildren().get(index) - will be enough, instead of creating playerLabels lists
		HBox player2 = new HBox();
		for(int i = 1; i<10; ++i) {
			Label dummy = new Label(String.format("%d", i));
			dummy.setFont(Font.font("arial", dummyFont));
			dummy.setTextFill(Color.RED);
			dummy.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			dummy.setPadding(new Insets(dummyInsetsPadding));
			dummy.setPrefSize(dummyW, dummyH);
			dummy.setAlignment(Pos.CENTER);
			player2.getChildren().add(dummy);
		}
		root.getChildren().add(player2);
		
		HBox player1 = new HBox();
		for(int i = 1; i<10; ++i){
			Label dummy = new Label(String.format("%d", i));
			dummy.setFont(Font.font("arial", dummyFont));
			dummy.setTextFill(Color.RED);
			dummy.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			dummy.setPadding(new Insets(dummyInsetsPadding));
			dummy.setPrefSize(dummyW, dummyH);
			dummy.setAlignment(Pos.CENTER);
			player1.getChildren().add(dummy);
		}
		player1.setLayoutX(0);
		player1.setLayoutY(dummyH + canvasH);
		root.getChildren().add(player1);
		
		Canvas canvas = new Canvas(canvasW, canvasH);
		canvas.setLayoutX(0);
		canvas.setLayoutY(dummyH);
		g = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		control = new PvpControl(canvas, player1, player2);
		
		
		input.setPrefWidth(rootW);
		input.setLayoutX(0);
		input.setLayoutY(canvasH + dummyH*2);
		input.setOnAction( e-> {
			String inputText = input.getText();
			int tmpIndex = Integer.parseInt(input.getText().split(" +")[1], 10);
			control.cast(canvas, inputText, tmpIndex);
		});
		
		root.getChildren().add(input);
		return root;
	}
	
}
