package animation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import pvp.EndAnimationAction;

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
	private EndAnimationAction endAction;
	boolean isReachBox;
	
	public AnimationFire(Canvas canvas) {
		this.g = canvas.getGraphicsContext2D();
		this.maxY = canvas.getHeight();
		this.maxX = canvas.getWidth();
		this.endAction = null;
		//emmiter.setEmmiterOptions(speedX, 123, 0.1, 20);
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				onUpdate();
			}
		};
	}
	public AnimationFire(Canvas canvas, EndAnimationAction endAction) {
		this.g = canvas.getGraphicsContext2D();
		this.maxY = canvas.getHeight();
		this.maxX = canvas.getWidth();
		this.endAction = endAction;
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
		isReachBox = false;
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
		
		if(!isReachBox) particles.addAll(emmiter.emmit(x,y));
		x += speedX;
		y += speedY;
		
		if(speedY<0 && y<0 || speedY>0 && y>570) {
			isReachBox = true;
		}
		if(particles.size() == 0) {
			endAction.action();
			g.setFill(Color.BLACK);
			g.fillRect(0,0,maxX,maxY);
			timer.stop();
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
	
	//start fire
	//move fire
	//stop fire
	//fire reach target
	//fire state - if reach target?: reach, blocked, freeze .. etc
}
