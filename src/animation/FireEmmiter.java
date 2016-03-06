package animation;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class FireEmmiter extends Emmiter {

	@Override
	public List<Particle> emmit(double x, double y) {
		
		List<Particle> particles = new ArrayList<>();
		
		int numParticles = 15;
		for(int i = 0; i<numParticles; ++i) {
			Particle p = new Particle(x,y,new Point2D((Math.random()*4 - 2), Math.random() * 4), 
					20, 2.0, Color.rgb(230, 40, 45), BlendMode.ADD);//deathFire 40, 40, 45
			particles.add(p);
		}	
		return particles;
	}
	
	public static double getRadius() {
		return 20;
	}

}
