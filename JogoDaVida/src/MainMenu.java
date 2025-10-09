import GameModes.*;
import processing.core.PApplet;

public class MainMenu extends PApplet {

	String title = "Chose your game:";
	String option1 = "Start Custom Game of Life 23/3";
	String option2 = "Start Game of Life 23/3 with colours";
	String option3 = "Start Game of Life 23/36 with colours";
	String option4 = "Start 2D Cellular Automata";
	String option5 = "Start Game of Life 23/3 with music";

	public void settings() {
		size(500, 360);
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

		drawButton(width / 2, 200, 300, 40, option3, 3);

		drawButton(width / 2, 260, 300, 40, option4, 4);

		drawButton(width / 2, 320, 300, 40, option5, 5);
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
			PApplet.main(GameOfLifeCustom.class);
		}

		float y2 = 140;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y2 - h / 2 && mouseY < y2 + h / 2) {
			PApplet.main(GameOfLifeColours233.class);
		}

		float y3 = 200;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y3 - h / 2 && mouseY < y3 + h / 2) {
			PApplet.main(GameOfLifeColours2336.class);
		}

		float y4 = 260;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y4 - h / 2 && mouseY < y4 + h / 2) {
			PApplet.main(TwoDCellularAutomata.class);
		}

		float y5 = 320;
		if (mouseX > x - w / 2 && mouseX < x + w / 2 && mouseY > y5 - h / 2 && mouseY < y5 + h / 2) {
			PApplet.main(GameOfLifeMusic.class);
		}
	}

	public static void main(String[] args) {
		PApplet.main(MainMenu.class);
	}
}