package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DuelGame {
	Scene clientGame;
	Room room = new Room(2);
	DisplayControl dc = new DisplayControl(room.getPlayers(), room.getCurrPlayer());
	VBox[] chats = new VBox[3];
	Label clientHP, clientDefFire;
	Player currPlayer = room.getCurrPlayer();


	public void start(Window window) {

		BorderPane root = new BorderPane();
		TextField commandLine = new TextField();
		commandLine.setPromptText("action");
		commandLine.setPrefWidth(1000);
		commandLine.setOnAction(e -> {
			currPlayer.cast(commandLine.getText(), room.getPlayers());
			chats[room.getCurrPlayerIndex()].getChildren().add(new Label(currPlayer.getName() + ": " + commandLine.getText()));
			commandLine.setText("");
			currPlayer = room.getNextPlayer();
			dc.setNewCurrPlayer(currPlayer);
			boolean answer = room.checkTheGameState();
			if(!answer) {
				Player loser = new Player("error");
				for(Player pl:room.getPlayers()) {
					if(pl.getHP()<0) {
						loser = pl;
					}
				}
				AlertBox.display("Result", "Game ended. The winner is: " + loser.getName());
				window.close();
			}
			dc.refreshVals();
		});
		HBox chats = addChats();
		HBox tools = addTools();
		VBox personalInfo = addPersonalInfo();
		VBox players = addPlayers();

		root.setTop(tools);
		root.setCenter(chats);
		root.setLeft(personalInfo);
		root.setRight(players);
		root.setBottom(commandLine);

		clientGame = new Scene(root, 800,600);
		window.getStage().setScene(clientGame);
		window.getStage().show();
	}

	

	private VBox addPlayers() {
		VBox box = new VBox();
		box.setPrefWidth(200);
		
		Label header = new Label();
		header.setFont(new Font("arial", 16));
		header.setText("Players in game");
		box.getChildren().add(header);
		dc.refreshVals();
		for(int i = 0; i<dc.allPlayersInGame(); ++i) {
			HBox boxPlayer = new HBox();
			Label hp = new Label(" HP: ");
			Label def = new Label(" Def: ");
			boxPlayer.getChildren().addAll(dc.getLabelIndexName(i),hp, dc.getLabelIndexHp(i),def, dc.getLabelIndexDef(i));
			box.getChildren().add(boxPlayer);
		}
		return box;
	}
	
	private HBox addTools() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setStyle("-fx-background-color: #dedede; -fx-padding: 5 0 12 0");
		
		Label header = new Label();
		header.setFont(new Font("arial", 16));
		header.setText("Here will be tools");
		box.getChildren().add(header);
		return box;
	}
	
	private VBox addPersonalInfo() {
		VBox box = new VBox();
		box.setPrefWidth(200);
		
		
		HBox hitPoints = new HBox();
		Label hits = new Label("Hit points: ");
		hitPoints.getChildren().addAll(hits, dc.getCurrHp());
		
		HBox defPoints = new HBox();
		Label defs = new Label("Fire defense: ");
		defPoints.getChildren().addAll(defs, dc.getCurrDef());
		
		box.getChildren().addAll(dc.getCurrName(), hitPoints, defPoints);
		return box;
	}
	
	private HBox addChats() {
		HBox box = new HBox();
		for(int i = 0; i<3; ++i) {
			chats[i] = new VBox();
			chats[i].setPrefWidth(199);
			chats[i].getChildren().add(new Label("Hello, World!"));
			box.getChildren().add(chats[i]);
		}
		box.setStyle("-fx-background-color: #dddddd; -fx-border-width: 2; -fx-margin: 5 0 0 0;");
		return box;
	}
}