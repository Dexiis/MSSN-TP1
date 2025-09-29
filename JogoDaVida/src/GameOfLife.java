import processing.core.PApplet;

public class GameOfLife extends PApplet {
  
  int cols, rows;
  int cellSize = 10;
  int[][] grid;
  int[][] next;

  public static void main(String[] args) {
    PApplet.main("GameOfLife");
  }

  public void settings() {
    size(800, 600);
  }

  public void setup() {
    frameRate(10);
    
    cols = width / cellSize;
    rows = height / cellSize;
    
    grid = new int[cols][rows];
    next = new int[cols][rows];
    
    initGridRandom();
  }

  public void draw() {
    background(255);
    
    calculateNextGeneration();
    drawGrid();
  }

  void initGridRandom() {
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        grid[x][y] = (int)random(4) == 0 ? 1 : 0; 
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
        
        int liveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue;
            liveNeighbors += grid[x + i][y + j];
          }
        }
        
        if (grid[x][y] == 1) {
          if (liveNeighbors < 2 || liveNeighbors > 3) {
            next[x][y] = 0;
          }
        } 
        else { 
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
    initGridRandom();
  }
}