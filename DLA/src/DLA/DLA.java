package DLA;

import java.util.ArrayList;
import java.util.List;

import DLA.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;

public class DLA extends PApplet {
	
	private int NUM_WALKERS = 500;
	private int NUM_STEPS_PER_FRAME = 100;
	
	private List<Walker> walkers;
	
	public void settings() {
		size(1600, 900);
	}

	public void setup() {
		walkers = new ArrayList<Walker>();
		Walker walker = new Walker(new PVector(width / 2, height / 2));
		walkers.add(walker);

		for (int i = 0; i < NUM_WALKERS; i++) {
			walker = new Walker();
			walkers.add(walker);
		}
	}

	public void draw(float dt) {
		background(255);

		for (int i = 0; i < NUM_STEPS_PER_FRAME; i++) {
			for (Walker walker : walkers) {
				if (walker.getState() == State.WANDER) {
					walker.gravaty();
					walker.updateState(walkers);
				}
			}
		}

		for (Walker walker : walkers) {
			walker.display();
		}

	}

	public void keyPressed() {

	}

	public void mousePressed() {

	}

}
