import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 15;
    private final int gridSize;
    private int[][] grid;

    private final Color[] colors = {
        Color.BLACK, // Cor para a célula morta (índice 0)
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.MAGENTA,
        Color.ORANGE,
        Color.CYAN,
        Color.PINK,
        Color.LIGHT_GRAY,
        Color.WHITE
    };

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
        
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                g.setColor(colors[grid[row][col]]);
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        
        g.setColor(Color.GRAY);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, gridSize * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, gridSize * CELL_SIZE, i * CELL_SIZE);
        }
    }
}