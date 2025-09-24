import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 15;
    private final int gridSize;
    private int[][] grid;

    public GamePanel(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        setPreferredSize(new Dimension(gridSize * CELL_SIZE, gridSize * CELL_SIZE));
        setBackground(Color.DARK_GRAY);
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha a grade
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] == 1) { // Célula viva "antiga"
                    g.setColor(Color.GREEN);
                } else if (grid[row][col] == 2) { // Célula "nova"
                    g.setColor(Color.CYAN); // Ou outra cor, como Color.YELLOW
                } else { // Célula morta
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        
        // Desenha as linhas da grade
        g.setColor(Color.GRAY);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, gridSize * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, gridSize * CELL_SIZE, i * CELL_SIZE);
        }
    }
}