package GameModes;
import GameModes.Cells.*;
import processing.core.PApplet;
import processing.sound.*;

public class GameOfLifeMusic extends PApplet {

	int cols, rows;
	int cellSize = 10;

	CellularAutomata ca;

	int[] frequencies;
	SinOsc sine = new SinOsc(this);

	boolean isPaused = false;

	int buttonWidth = 60;
	int buttonHeight = 30;
	int buttonX1 = 10;
	int buttonX2 = buttonX1 + buttonWidth + 10;
	int buttonY = 10;

	int gridYStart = buttonY + buttonHeight + 10;

	public void settings() {
		size(200, 280);
	}

	public void setup() {
		frameRate(10);

		cols = width / cellSize;
		rows = (height - gridYStart) / cellSize;

		ca = new CellularAutomata(this, rows, cols);

		int[] colors = new int[2];
		colors[0] = color(255);
		colors[1] = color(0);
		ca.setStateColors(colors);

		initGridRandom();
	}

	public void draw() {
		background(255);

		stroke(200);
		line(0, gridYStart, width, gridYStart);

		pushMatrix();
		translate(0, gridYStart);
		ca.display();
		popMatrix();

		drawButtons();

		if (!isPaused) {
			calculateNextGeneration();
		}
	}

	void initGridRandom() {
		ca.setRandomStates();
	}

	void calculateNextGeneration() {
		
		sine.stop();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).countAlives();
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).applyRuleMusic(sine);
			}
		}
		
	}

	void drawButtons() {
		textSize(14);
		textAlign(CENTER, CENTER);

		if (isPaused) {
			fill(255, 150, 150);
			stroke(255, 0, 0);
		} else {
			fill(200);
			stroke(0);
		}
		rect(buttonX1, buttonY, buttonWidth, buttonHeight, 5);
		fill(0);
		text("PARAR", buttonX1 + buttonWidth / 2, buttonY + buttonHeight / 2);

		if (!isPaused) {
			fill(150, 255, 150);
			stroke(0, 255, 0);
		} else {
			fill(200);
			stroke(0);
		}
		rect(buttonX2, buttonY, buttonWidth, buttonHeight, 5);
		fill(0);
		text("CONT.", buttonX2 + buttonWidth / 2, buttonY + buttonHeight / 2);
	}

	public void mousePressed() {
		if (mouseX >= buttonX1 && mouseX <= buttonX1 + buttonWidth && mouseY >= buttonY
				&& mouseY <= buttonY + buttonHeight) {
			isPaused = true;
			sine.stop();
			return;
		}

		if (mouseX >= buttonX2 && mouseX <= buttonX2 + buttonWidth && mouseY >= buttonY
				&& mouseY <= buttonY + buttonHeight) {
			isPaused = false;
			return;
		}

		if (mouseY >= gridYStart) {
			int x_click = mouseX;
			int y_click = mouseY - gridYStart;

			Cell clickedCell = ca.getCell(x_click, y_click);

			if (clickedCell != null) {
				clickedCell.flipState();
			}
		}
	}
}