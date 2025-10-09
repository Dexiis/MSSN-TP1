package GameModes.Cells;

import processing.core.PApplet;
import processing.sound.SinOsc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {
	protected CellularAutomata ca;
	protected int row, col;
	protected int state;
	private int nAlives;
	protected Cell[] neighboringCells;
	protected int width, height;
	int[] frequencies;

	private Map<Integer, Integer> colorCounts;

	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		state = 0;
		neighboringCells = null;
		width = ca.getCellWidth();
		height = ca.getCellHeight();
		colorCounts = new HashMap<>();
		frequencies = new int[] { 131, 131 * 2, 131 * 3, 131 * 4, 131 * 5 };
	}

	public void setNeighboringCells(Cell[] neighboringCells) {
		this.neighboringCells = neighboringCells;
	}

	public Cell[] getNeighboringCells() {
		return neighboringCells;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public int getNAlives() {
		return nAlives;
	}

	public void display(PApplet p) {
		p.pushStyle();
		p.fill(ca.getStateColors()[state]);
		p.rect(col * width, row * height, width, height);
		p.popStyle();
	}

	public void flipState() {
		if (state == 0)
			state = 1;
		else
			state = 0;
	}

	public void countAlives() {
		nAlives = 0;
		colorCounts.clear();

		for (Cell neighbor : neighboringCells) {
			int neighborState = neighbor.state;

			if (neighbor == this)
				continue;

			if (neighborState != 0) {
				nAlives++;
				colorCounts.put(neighborState, colorCounts.getOrDefault(neighborState, 0) + 1);
			}
		}
	}

	private int determineNewColor() {
		if (colorCounts.isEmpty()) {
			return (int) ca.getPApplet().random(1, ca.getNumberOfStates());
		}

		int maxCount = Collections.max(colorCounts.values());

		List<Integer> winningColors = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
			if (entry.getValue() == maxCount) {
				winningColors.add(entry.getKey());
			}
		}

		int randomIndex = (int) ca.getPApplet().random(winningColors.size());
		return winningColors.get(randomIndex);
	}

	public void applyRule233() {
		if (state == 0 && this.nAlives == 3)
			state = 1;
		if (state == 1 && (this.nAlives < 2 || this.nAlives > 3))
			state = 0;
	}

	public void applyRuleMusic(SinOsc sine) {
		if (this.state == 1 && (this.nAlives < 2 || this.nAlives > 3))
			state = 0;
		else if (this.nAlives == 3) {
			state = 1;
			int scaleIndex = this.row % frequencies.length;
			float frequency = frequencies[scaleIndex];
			sine.freq(frequency);
			sine.amp(0.2f);
			sine.play();
		}
	}

	public void applyRuleColours233() {
		if (this.state != 0) {
			if (this.nAlives < 2 || this.nAlives > 3) {
				this.state = 0;
			} else {
				this.state = determineNewColor();
			}
		} else {
			if (this.nAlives == 3) {
				this.state = determineNewColor();
			}
		}
	}

	public void applyRuleColours2336() {
		if (this.state != 0) {
			if (this.nAlives < 2 || this.nAlives > 3) {
				this.state = 0;
			} else {
				this.state = determineNewColor();
			}
		} else {
			if (this.nAlives == 3 || this.nAlives == 6) {
				this.state = determineNewColor();
			}
		}
	}

	public void applyRuleColoursMajority() {

		if (this.state != 0) {

			Map<Integer, Integer> finalColorCounts = new HashMap<>(colorCounts);
			finalColorCounts.put(this.state, finalColorCounts.getOrDefault(this.state, 0) + 1);

			if (!finalColorCounts.isEmpty()) {

				int maxCount = Collections.max(finalColorCounts.values());

				List<Integer> winningColors = new ArrayList<>();
				for (Map.Entry<Integer, Integer> entry : finalColorCounts.entrySet()) {
					if (entry.getValue() == maxCount) {
						
						winningColors.add(entry.getKey());
						
					}
				}

				if (winningColors.size() == 1) {
					
					this.state = winningColors.get(0);
					
				} else if (winningColors.size() > 1) {

					if (!winningColors.contains(this.state)) {

						int randomIndex = (int) ca.getPApplet().random(winningColors.size());
						this.state = winningColors.get(randomIndex);
						
					}
				}
			}

		}
	}
}