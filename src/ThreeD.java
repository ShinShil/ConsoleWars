package application;
//this is effects polygon
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ThreeD extends GameScene{

	private Emmiter emitter = new FireEmmiter();
	
	private List<Particle> particles = new ArrayList<>();
	
	private GraphicsContext g; 
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(600, 600);
		
		Canvas canvas = new Canvas(600,600);
		g = canvas.getGraphicsContext2D();
		
		root.getChildren().add(canvas);
		return root;
	}
	
	private int y = 300;
	private int x = 100;
	int delta = 8;
	int deltax = 8;
	private void onUpdate() {
		g.setGlobalAlpha(1.0);
		g.setGlobalBlendMode(BlendMode.SRC_OVER);
		g.setFill(Color.BLACK);
		g.fillRect(0,0,600,600);
		
		particles.addAll(emitter.emit(x, y));
		x+=deltax;
		y-=delta;
		if(y<0 || y>600) {
			delta *= -1;
		}
		if(x<0 || x>600) {
			deltax *= -1;
		}
		
		for(Iterator<Particle> it = particles.iterator(); it.hasNext();) {
			Particle p = it.next();
			p.update();
			
			if(!p.isAlive()) {
				it.remove();
				continue;
			}
			
			p.render(g);
		}
	}
	AnimationTimer timer;
	@Override
	public int start(Window window) {
		
		window.setScene(new Scene(createContent()));
		window.show();
		
		 timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				onUpdate();
			}
		};
		timer.start();
		return 0;
	}

}
