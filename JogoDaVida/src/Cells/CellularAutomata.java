package Cells;

import processing.core.PApplet;

public class CellularAutomata {
	protected int nRows, nCols;
	protected int width, height;
	protected Cell[][] cells;
	protected int numberOfStates;
	protected int[] colors;
	protected PApplet p;

	public CellularAutomata(PApplet p, int nRows, int nCols) {
		this.p = p;
		this.nRows = nRows;
		this.nCols = nCols;
		this.numberOfStates = 2;
		width = p.width / nCols;
		height = p.height / nRows;
		cells = new Cell[nRows][nCols];
		createGrid();
		colors = new int[numberOfStates];
		colors[0] = p.color(255);
		colors[1] = p.color(0);
	}

	protected void createGrid() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		setNeighbors();
	}

	public Cell getCellGrid(int row, int col) {
		return cells[row][col];
	}

	private void setNeighbors() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				Cell[] neigh = new Cell[9];
				int n = 0;
				for (int ii = -1; ii <= 1; ii++) {
					for (int jj = -1; jj <= 1; jj++) {
						int row = (i + ii + nRows) % nRows;
						int col = (j + jj + nCols) % nCols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighboringCells(neigh);
			}
		}
	}

	public void setStateColors(int[] colors) {
		this.colors = colors;
		this.numberOfStates = colors.length;
	}

	public int[] getStateColors() {
		return colors;
	}

	public PApplet getPApplet() {
		return p;
	}

	public int getCellWidth() {
		return width;
	}

	public int getCellHeight() {
		return height;
	}

	public int getNumberOfStates() {
		return numberOfStates;
	}

	public Cell getCell(int x, int y) {
		int row = y / height;
		int col = x / width;
		if (row >= nRows)
			row = nRows - 1;
		if (col >= nCols)
			col = nCols - 1;

		return cells[row][col];
	}

	public void setRandomStates() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				cells[i][j].setState(p.random(1) < 0.30f ? 1 : 0);
			}
		}
	}

	public void display() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				cells[i][j].display(p);
			}
		}
	}
}