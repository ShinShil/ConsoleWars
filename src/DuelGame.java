package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DuelGame {
	int chatsAmount = 2;
	Scene clientGame;
	Room room = new Room(2);
	DisplayControl dc = new DisplayControl(room.getPlayers(), room.getCurrPlayer());
	VBox[] chats = new VBox[chatsAmount];
	Label clientHP, clientDefFire;
	Player currPlayer = room.getCurrPlayer();
	int oldLabel = 0;
	
	public void start(Window window) {

		VBox root = new VBox();
		TextField commandLine = new TextField();
		commandLine.setPromptText("action");
		commandLine.setPrefWidth(1000);
		commandLine.setOnAction(e -> {
			int res = currPlayer.cast(commandLine.getText(), room.getPlayers());
			Color col;
			if(res!=0) {
				col = Color.BISQUE;
			}else {
				col = Color.GREEN;
			}
			Label newLabel = new Label("    " + commandLine.getText());
			newLabel.setBackground(new Background(new BackgroundFill(col, null, null)));
			newLabel.setPrefWidth(250);
			newLabel.setFont(Font.font("arial", 22));
			chats[room.getCurrPlayerIndex()].getChildren().add(newLabel);
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
		HBox tools = addTools(window);
		VBox personalInfo = addPersonalInfo();
		VBox players = addPlayers();
		HBox middlePart = new HBox();
		middlePart.getChildren().addAll(personalInfo, chats, players);
/*
		root.setTop(tools);
		root.setCenter(chats);
		root.setLeft(personalInfo);
		root.setRight(players);
		root.setBottom(commandLine);
*/
		root.getChildren().addAll(tools, middlePart, commandLine);
		
		clientGame = new Scene(root, 800,600);
		window.getStage().setScene(clientGame);
		window.getStage().show();
	}

	

	private VBox addPlayers() {
		VBox box = new VBox();
		box.setPrefWidth(200);
		Label header = new Label();
		header.setFont(new Font("arial", 20));
		header.setText("Players in game");
		header.setAlignment(Pos.CENTER);
		header.setPrefWidth(200);
		header.setPadding(new Insets(10,0,10,0));
		box.getChildren().add(header);
		dc.refreshVals();
		for(int i = 0; i<dc.allPlayersInGame(); ++i) {
			HBox boxPlayer = new HBox();
			Label hp = new Label(" HP: ");
			Label def = new Label(" Def: ");
			Label numLabe = new Label(String.format(" %d. ", i+1));
			numLabe.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			//boxPlayer.getChildren().addAll(numLabe, dc.getLabelIndexName(i),hp, dc.getLabelIndexHp(i),def, dc.getLabelIndexDef(i));
			
			Label tmp = new Label(": ");
			Label dummy = dc.getLabelIndexName(i);
			dummy.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			tmp.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			HBox nameBox = new HBox(dummy, tmp);
			Label tmp2 = new Label(" ");
			tmp2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			Label tmp3 = new Label(" ");
			tmp3.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
			boxPlayer.getChildren().addAll(tmp3, nameBox, dc.getLabelIndexHp(i),tmp2, dc.getLabelIndexDef(i));
			box.getChildren().add(boxPlayer);
		}
		return box;
	}
	
	private void setBtn(Button btn) {
		btn.setCursor(Cursor.HAND);
		btn.defaultButtonProperty().bind(btn.focusedProperty());
		btn.setFont(new Font("Arial", 16));
	}
	
	private HBox addTools(Window window) {
		HBox box = new HBox();
		box.setStyle("-fx-background-color: #dedede; -fx-padding: 5 0 12 0");
		Button goMenu = new Button("Go to menu");
		goMenu.setOnAction(e -> {
			window.startMenu();
		});
		setBtn(goMenu);
		
		Button exitBtn = new Button("Exit");
		exitBtn.setOnAction(e -> {
			window.confirmCloseProgram(window.getStage());
		});
		setBtn(exitBtn);
		
		box.setSpacing(5);
		box.getChildren().addAll(goMenu, exitBtn);
		return box;
	}
	
	private VBox addPersonalInfo() {
		VBox box = new VBox();
		box.setPrefWidth(200);
		
		Label header = new Label("Player");
		header.setFont(new Font("arial", 20));
		header.setAlignment(Pos.CENTER);
		header.setPrefWidth(200);
		header.setPadding(new Insets(10,0,10,0));
		
		HBox hitPoints = new HBox();
		Label hits = new Label(" Hit points: ");
		hits.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		hitPoints.getChildren().addAll(hits, dc.getCurrHp());

		HBox defPoints = new HBox();
		Label defs = new Label(" Fire defense: ");
		defs.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		defPoints.getChildren().addAll(defs, dc.getCurrDef());
		
		Label name = new Label(" Name: ");
		name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		HBox nameBox = new HBox(name, dc.getCurrName());
		
		box.getChildren().addAll(header, nameBox, hitPoints, defPoints);
		return box;
	}
	
	private HBox addChats() {
		HBox box = new HBox();
		for(int i = 0; i<chatsAmount; ++i) {
			VBox tmpBox = new VBox();
			chats[i] = new VBox();
			chats[i].setPrefWidth(199);
			chats[i].setPrefHeight(600);
			Label header = new Label("Player: " + room.getPlayers().get(i).getName());
			header.setFont(Font.font("arial", FontWeight.BOLD, 18));
			header.setPrefWidth(250);
			header.setAlignment(Pos.CENTER);
			header.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			header.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0,0,1,0), new Insets(0))));
			tmpBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1), new Insets(0))));
			//ScrollPane tmpScroll = new ScrollPane();
			//tmpScroll.setContent(chats[i]);
			tmpBox.getChildren().addAll(header, chats[i]);
			box.getChildren().add(tmpBox);
		}
		box.setStyle("-fx-background-color: #dddddd; -fx-border-width: 0; -fx-margin: 0 0 0 0;");
		return box;
	}
}