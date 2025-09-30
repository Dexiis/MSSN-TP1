import processing.core.PApplet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoDCellularAutomata extends PApplet {

	int cols, rows;
	int cellSize = 10;
	int[][] grid;
	int[][] next;

	Map<Integer, Integer> colorMap;

	final int DEAD_COLOR_INDEX = 0;
	final int MAX_LIVE_COLOR_INDEX = 8;

	public void settings() {
		size(800, 600);
	}

	public void setup() {
		frameRate(10);

		cols = width / cellSize;
		rows = height / cellSize;

		grid = new int[cols][rows];
		next = new int[cols][rows];

		setupColorMap();
		initGridRandom();
	}

	void setupColorMap() {
		colorMap = new HashMap<>();
		colorMap.put(DEAD_COLOR_INDEX, color(0, 0, 0));
		colorMap.put(1, color(255, 0, 0));
		colorMap.put(2, color(255, 255, 0));
		colorMap.put(3, color(0, 255, 0));
		colorMap.put(4, color(0, 0, 255));
		colorMap.put(5, color(128, 0, 128));
		colorMap.put(6, color(255, 165, 0));
		colorMap.put(7, color(255, 192, 203));
		colorMap.put(8, color(0, 255, 255));
	}

	public void draw() {
		background(255);

		calculateNextGeneration();
		drawGrid();
	}

	void initGridRandom() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {

				if (x == 0 || x == cols - 1 || y == 0 || y == rows - 1) {
					grid[x][y] = DEAD_COLOR_INDEX;
				} else {
					grid[x][y] = (int) random(1, MAX_LIVE_COLOR_INDEX + 1);
				}
			}
		}
	}

	void drawGrid() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {

				int colorIndex = grid[x][y];

				fill(colorMap.get(colorIndex));

				stroke(100);

				rect(x * cellSize, y * cellSize, cellSize, cellSize);
			}
		}
	}

	void calculateNextGeneration() {

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				next[x][y] = grid[x][y];
			}
		}

		for (int x = 1; x < cols - 1; x++) {
			for (int y = 1; y < rows - 1; y++) {

				int currentColor = grid[x][y];

				Map<Integer, Integer> colorCounts = new HashMap<>();

				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0)
							continue;

						int neighborColor = grid[x + i][y + j];

						if (neighborColor != DEAD_COLOR_INDEX) {
							colorCounts.put(neighborColor, colorCounts.getOrDefault(neighborColor, 0) + 1);
						}
					}
				}

				int maxCount = 0;
				for (int count : colorCounts.values()) {
					if (count > maxCount) {
						maxCount = count;
					}
				}

				List<Integer> winningColors = new ArrayList<>();
				for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
					if (entry.getValue() == maxCount) {
						winningColors.add(entry.getKey());
					}
				}

				if (winningColors.size() == 1) {
					next[x][y] = winningColors.get(0);
				} else {
					next[x][y] = currentColor;
				}
			}
		}

		int[][] temp = grid;
		grid = next;
		next = temp;
	}

	public void mousePressed() {
		initGridRandom();
	}
}