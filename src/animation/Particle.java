package animation;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Paint;

public class Particle {
		
	private double x;
	private double y;
	
	private Point2D velocity;
	
	private double radius;
	private double life = 1.0;
	private double decay;
	
	private Paint color;
	private BlendMode blendMode;
	
	public Particle(double x, double y, Point2D velocity, double radius, double expireTime, Paint color,
			BlendMode blendBode) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		this.radius = radius;
		this.color = color;
		this.blendMode = blendBode;

		this.decay = 0.08;
	}
	
	public boolean isAlive() {
		return life>0;
	}
	
	public void test() {
		System.out.println("TEST");
	}
	
	public void update() {
		x += velocity.getX();
		y += velocity.getY();
		
		life -= decay;
	}
	
	public void render(GraphicsContext g) {
		g.setGlobalAlpha(life);
		g.setGlobalBlendMode(blendMode );
		g.setFill(color);
		g.fillOval(x, y, radius, radius);
	}
}
