import processing.core.PApplet;

public class GameOfLifeCustom extends PApplet {

	int cols, rows;
	int cellSize = 10;

	int[][] grid;
	int[][] next;

	boolean isPaused = false;

	int buttonWidth = 100;
	int buttonHeight = 30;
	int buttonX1 = 10;
	int buttonX2 = buttonX1 + buttonWidth + 10;
	int buttonY = 10;

	int gridYStart = buttonY + buttonHeight + 10;

	public void settings() {
		size(800, 600);
	}

	public void setup() {
		frameRate(10);

		cols = width / cellSize;
		rows = (height - gridYStart) / cellSize;

		grid = new int[cols][rows];
		next = new int[cols][rows];

		initGridRandom();
	}

	public void draw() {
		background(255);

		if (!isPaused) {
			calculateNextGeneration();
		}

		drawGrid();
		drawButtons();
	}

	void initGridRandom() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				grid[x][y] = (int) random(4) == 0 ? 1 : 0;
			}
		}
	}

	void drawGrid() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				if (grid[x][y] == 1) {
					fill(0);
				} else {
					fill(255);
				}
				stroke(200);
				rect(x * cellSize, y * cellSize + gridYStart, cellSize, cellSize);
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
		text("CONTINUAR", buttonX2 + buttonWidth / 2, buttonY + buttonHeight / 2);
	}

	void calculateNextGeneration() {

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				next[x][y] = grid[x][y];
			}
		}

		for (int x = 1; x < cols - 1; x++) {
			for (int y = 1; y < rows - 1; y++) {

				int liveNeighbors = 0;

				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0)
							continue;
						liveNeighbors += grid[x + i][y + j];
					}
				}

				if (grid[x][y] == 1) {
					if (liveNeighbors < 2 || liveNeighbors > 3) {
						next[x][y] = 0;
					}

				} else {
					if (liveNeighbors == 3) {
						next[x][y] = 1;
					}
				}
			}
		}

		grid = next;
		next = new int[cols][rows];

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

		if (mouseY >= gridYStart) {

			int x_grid = mouseX / cellSize;
			int y_grid = (mouseY - gridYStart) / cellSize;

			if (x_grid >= 0 && x_grid < cols && y_grid >= 0 && y_grid < rows) {

				grid[x_grid][y_grid] = 1 - grid[x_grid][y_grid];
			}
		}
	}
}