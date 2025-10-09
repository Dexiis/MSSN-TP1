package GameModes;
import GameModes.Cells.*;
import processing.core.PApplet;

public class GameOfLifeCustom extends PApplet {

	private int cols, rows;
	private int cellSize = 10;
	
	private int buttonWidth = 100;
	private int buttonHeight = 30;
	private int buttonX1 = 10;
	private int buttonX2 = buttonX1 + buttonWidth + 10;
	private int buttonX3 = buttonX2 + buttonWidth + 10;
	private int buttonY = 10;
	
	private int gridYStart = buttonY + buttonHeight + 10;

	private boolean isPaused = false;

	private CellularAutomata ca;

	public void settings() {
		size(1600, 900);
	}

	public void setup() {
		frameRate(10);

		cols = width / cellSize;
		rows = (height - gridYStart) / cellSize;

		ca = new CellularAutomata(this, rows, cols);

		ca.setRandomStates();
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

	private void drawButtons() {
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
		text("CONTINUAR", buttonX2 + buttonWidth / 2, buttonY + buttonHeight / 2);

		fill(150, 150, 255);
		stroke(0, 0, 255);
		rect(buttonX3, buttonY, buttonWidth, buttonHeight, 5);
		fill(0);
		text("REINICIAR", buttonX3 + buttonWidth / 2, buttonY + buttonHeight / 2);
	}

	private void calculateNextGeneration() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).countAlives();
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).applyRule233();
			}
		}
		
	}

	public void mousePressed() {
		
		if (mouseX >= buttonX1 && mouseX <= buttonX1 + buttonWidth && mouseY >= buttonY
				&& mouseY <= buttonY + buttonHeight) {

			isPaused = true;
			return;
		}

		if (mouseX >= buttonX2 && mouseX <= buttonX2 + buttonWidth && mouseY >= buttonY
				&& mouseY <= buttonY + buttonHeight) {

			isPaused = false;
			return;
		}

		if (mouseX >= buttonX3 && mouseX <= buttonX3 + buttonWidth && mouseY >= buttonY
				&& mouseY <= buttonY + buttonHeight) {

			ca.setRandomStates();

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