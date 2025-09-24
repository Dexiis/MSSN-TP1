import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameOfLifeGUI implements ActionListener {

    private static final int GRID_SIZE = 50;
    private static final double INITIAL_DENSITY = 0.2;

    private JFrame frame;
    private GamePanel gamePanel;
    private JButton startButton;
    private JButton stopButton;
    private Timer timer;

    // A grade agora usa int para diferenciar células "novas"
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    public GameOfLifeGUI() {
        frame = new JFrame("Jogo da Vida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gamePanel = new GamePanel(GRID_SIZE);
        frame.add(gamePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        
        startButton = new JButton("Iniciar");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        stopButton = new JButton("Parar");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);

        initializeGrid();
        timer = new Timer(100, this);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // Atribui 1 (vivo) ou 0 (morto)
                grid[i][j] = (random.nextDouble() < INITIAL_DENSITY) ? 1 : 0;
            }
        }
        gamePanel.setGrid(grid);
        gamePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            timer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else if (e.getSource() == stopButton) {
            timer.stop();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
        
        if (e.getSource() == timer) {
            updateGrid();
            gamePanel.setGrid(grid);
            gamePanel.repaint();
        }
    }

    private void updateGrid() {
        int[][] nextGenerationGrid = new int[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                boolean isCurrentlyAlive = grid[i][j] > 0;

                if (isCurrentlyAlive) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGenerationGrid[i][j] = 0; // Morre
                    } else {
                        nextGenerationGrid[i][j] = 1; // Sobrevive (cor antiga)
                    }
                } else {
                    if (liveNeighbors == 3) {
                        nextGenerationGrid[i][j] = 2; // Nasce (cor nova)
                    } else {
                        nextGenerationGrid[i][j] = 0; // Permanece morta
                    }
                }
            }
        }
        this.grid = nextGenerationGrid;
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int neighborRow = row + i;
                int neighborCol = col + j;

                if (neighborRow >= 0 && neighborRow < GRID_SIZE &&
                    neighborCol >= 0 && neighborCol < GRID_SIZE) {
                    if (grid[neighborRow][neighborCol] > 0) { // Verifica se está viva
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameOfLifeGUI());
    }
}