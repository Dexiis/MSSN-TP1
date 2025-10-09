package DLA;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker extends PApplet{

	public enum State {
		STOPPED, WANDER
	}

	private PVector gravityPoint;
	private State state;
	private int colour;
	private static int radius = 1;

	public Walker() {

		gravityPoint = new PVector(width / 2, height / 2);
		PVector d = PVector.random2D();
		gravityPoint.add(d.mult(height / 2));

		setState(State.WANDER);
	}

	public Walker(PVector position) {
		this.gravityPoint = position;

		setState(State.STOPPED);
	}

	public void setState(State state) {
		this.state = state;
		if (state == State.STOPPED) {
			colour = color(0);
		} else {
			colour = color(255);
		}

	}

	public State getState() {
		return state;
	}

	public void gravaty() {
		PVector step = PVector.random2D();
		gravityPoint.add(step);

		gravityPoint.lerp(new PVector(width / 2, height / 2), 0.0002f);
	}

	public void updateState(List<Walker> walkers) {

		for (Walker walker : walkers) {
			if (walker.state == State.STOPPED) {
				float dist = PVector.dist(this.gravityPoint, walker.gravityPoint);
				if (dist < 2 * radius) {
					setState(State.STOPPED);
					break;
				}
			}
		}
	}

	public void display() {
		fill(colour);
		circle(gravityPoint.x, gravityPoint.y, 2 * radius);
	}
}
