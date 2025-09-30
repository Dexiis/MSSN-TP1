import processing.core.PApplet;

public class MainMenu extends PApplet {

	String title = "Chose your game:";
	String option1 = "Start Game of Life)";
	String option2 = "Start 2D Cellular Automata";

	public class Main {
		public static void main(String[] args) {
			PApplet.main("MainMenu");
		}
	}

	public void settings() {
		size(400, 200);
	}

	public void setup() {
		textAlign(CENTER, CENTER);
		textSize(16);
		rectMode(CENTER);
	}

	public void draw() {
		background(200);

		fill(0);
		text(title, width / 2, 30);

		drawButton(width / 2, 80, 300, 40, option1, 1);

		drawButton(width / 2, 140, 300, 40, option2, 2);
	}

	void drawButton(float x, float y, float w, float h, String label, int id) {
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y - h / 2 && mouseY < y + h / 2) {
			fill(150, 200, 255);
		} else {
			fill(180);
		}

		rect(x, y, w, h, 5);

		fill(0);
		text(label, x, y);
	}

	public void mousePressed() {
		float x = width / 2;
		float w = 300;
		float h = 40;

		float y1 = 80;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y1 - h / 2 && mouseY < y1 + h / 2) {
			PApplet.main("GameOfLife");
		}

		float y2 = 140;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y2 - h / 2 && mouseY < y2 + h / 2) {
			PApplet.main("TwoDCellularAutomata");
		}
	}

	public static void main(String[] args) {
		PApplet.main("MainMenu");
	}
}