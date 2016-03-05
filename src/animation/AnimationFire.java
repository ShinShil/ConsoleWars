package animation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class AnimationFire {
	private AnimationTimer timer;
	private GraphicsContext g;
	private FireEmmiter emmiter = new FireEmmiter();
	private List<Particle> particles = new ArrayList<>();
	private double speedX;
	private double speedY;
	private double x;
	private double y;
	private double maxY;
	private double maxX;
	private boolean direction; //0 - top, 1 - down
	
	public AnimationFire(Canvas canvas) {
		this.g = canvas.getGraphicsContext2D();
		this.maxY = canvas.getHeight();
		this.maxX = canvas.getWidth();
		//emmiter.setEmmiterOptions(speedX, 123, 0.1, 20);
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				onUpdate();
			}
		};
	}
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setSpeed(double speedY) {
		this.speedY = speedY;
	}
	public void start() {
		timer.start();
	}
	public void stop() {
		timer.stop();
	}
	
	private void onUpdate() {
		g.setGlobalAlpha(1.0);
		g.setGlobalBlendMode(BlendMode.SRC_OVER);
		g.setFill(Color.BLACK);
		g.fillRect(0,0,maxX,maxY);
		
		particles.addAll(emmiter.emmit(x,y));
		x += speedX;
		y += speedY;
		
		System.out.println(particles.size());
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
	
	//start fire
	//move fire
	//stop fire
	//fire reach target
	//fire state - if reach target?: reach, blocked, freeze .. etc
}
