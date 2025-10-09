import Cells.CellularAutomata;
import processing.core.PApplet;

public class TwoDCellularAutomata extends PApplet {

	int cols;
	int rows;
	int cellSize = 10;

	CellularAutomata ca;

	final int NUMBER_OF_STATES = 9;
	final int DEAD_COLOR_INDEX = 0;

	public static void main(String[] args) {
		PApplet.main("TwoDAutomata.TwoDCellularAutomataAdapted");
	}

	public void settings() {
		size(1600, 900);
	}

	public void setup() {
		frameRate(10);

		cols = width / cellSize;
		rows = height / cellSize;

		ca = new CellularAutomata(this, rows, cols);

		setupColorStates();

		initGridRandom();
	}

	public void draw() {
		background(255);

		calculateNextGeneration();

		ca.display();
	}

	void setupColorStates() {
		int[] customColors = new int[NUMBER_OF_STATES];
		customColors[DEAD_COLOR_INDEX] = color(0, 0, 0);
		customColors[1] = color(255, 0, 0);
		customColors[2] = color(255, 255, 0);
		customColors[3] = color(0, 255, 0);
		customColors[4] = color(0, 0, 255);
		customColors[5] = color(128, 0, 128);
		customColors[6] = color(255, 165, 0);
		customColors[7] = color(255, 192, 203);
		customColors[8] = color(0, 255, 255);

		ca.setStateColors(customColors);
	}

	void initGridRandom() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).setState((int) random(1, NUMBER_OF_STATES));
			}
		}
	}

	void calculateNextGeneration() {

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).countAlives();
			}
		}

		int[][] nextStates = new int[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				nextStates[i][j] = ca.getCellGrid(i, j).applyRuleColoursMajority();
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ca.getCellGrid(i, j).setState(nextStates[i][j]);
			}
		}
	}

	public void mousePressed() {
		initGridRandom();
	}
}