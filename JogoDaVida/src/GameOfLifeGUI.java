import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class GameOfLifeGUI implements ActionListener {

    private static final int GRID_SIZE = 50;
    private static final double INITIAL_DENSITY = 0.2;
    private static final int NUM_COLORS = 11; // 10 cores iniciais + Roxo (cor 11)

    private JFrame frame;
    private GamePanel gamePanel;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private Timer timer;

    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    // Mapa para as regras de colisão de cores. Chave é uma String "cor1,cor2"
    private final Map<String, Integer> collisionRules = new HashMap<>();

    public GameOfLifeGUI() {
        frame = new JFrame("Jogo da Vida de Conway com Colisão de Cores");
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

        resetButton = new JButton("Resetar");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Define as regras de colisão
        setCollisionRules();
        
        initializeGrid();
        timer = new Timer(100, this);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void setCollisionRules() {
        // Formato da chave: "cor1,cor2" para garantir ordem
        collisionRules.put("2,4", 6); // Amarelo (4) + Azul (2) = Verde (6)
        collisionRules.put("1,2", 11); // Vermelho (1) + Azul (2) = Roxo (11)
        collisionRules.put("1,4", 5); // Vermelho (1) + Amarelo (4) = Laranja (5)
    }

    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (random.nextDouble() < INITIAL_DENSITY) {
                    // Inicializa com as cores que podem colidir
                    grid[i][j] = random.nextInt(4) + 1; // Vermelho (1), Azul (2), Verde (3), Amarelo (4)
                } else {
                    grid[i][j] = 0; // Célula morta
                }
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
            resetButton.setEnabled(false);
        } else if (e.getSource() == stopButton) {
            timer.stop();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            resetButton.setEnabled(true);
        } else if (e.getSource() == resetButton) {
            timer.stop();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            resetButton.setEnabled(false);
            initializeGrid();
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
                Map<Integer, Integer> neighborColorCounts = countLiveNeighborsAndColors(i, j);
                int liveNeighbors = neighborColorCounts.getOrDefault(0, 0); // O mapa usa a chave 0 para contar vizinhos vivos

                boolean isCurrentlyAlive = grid[i][j] > 0;

                if (isCurrentlyAlive) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGenerationGrid[i][j] = 0; // Morre
                    } else {
                        nextGenerationGrid[i][j] = grid[i][j]; // Sobrevive, mantém a cor
                    }
                } else {
                    if (liveNeighbors == 3) {
                        int newColor = determineNewColor(neighborColorCounts);
                        nextGenerationGrid[i][j] = newColor; // Nasce com a cor determinada
                    } else {
                        nextGenerationGrid[i][j] = 0; // Permanece morta
                    }
                }
            }
        }
        this.grid = nextGenerationGrid;
    }
    
    // Novo método para determinar a cor da célula que nasce
    private int determineNewColor(Map<Integer, Integer> neighborColorCounts) {
        // Encontra a cor mais prevalente
        int mostPrevalentColor = 0;
        int maxCount = 0;
        
        // Colhe as cores que estão empatadas no maior número
        ArrayList<Integer> tiedColors = new ArrayList<>();
        
        for (Map.Entry<Integer, Integer> entry : neighborColorCounts.entrySet()) {
            if (entry.getKey() != 0) { // Ignora o contador de vizinhos vivos
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostPrevalentColor = entry.getKey();
                    tiedColors.clear();
                    tiedColors.add(entry.getKey());
                } else if (entry.getValue() == maxCount) {
                    tiedColors.add(entry.getKey());
                }
            }
        }

        // Se houver um empate entre exatamente duas cores, verifica as regras de colisão
        if (tiedColors.size() == 2) {
            Collections.sort(tiedColors); // Ordena para criar uma chave consistente
            String collisionKey = tiedColors.get(0) + "," + tiedColors.get(1);
            if (collisionRules.containsKey(collisionKey)) {
                return collisionRules.get(collisionKey);
            }
        }
        
        // Retorna a cor prevalente (ou a primeira em caso de empate sem colisão)
        return mostPrevalentColor;
    }

    // Método que agora retorna um mapa com a contagem de vizinhos e cores
    private Map<Integer, Integer> countLiveNeighborsAndColors(int row, int col) {
        Map<Integer, Integer> counts = new HashMap<>();
        int liveCount = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int neighborRow = row + i;
                int neighborCol = col + j;

                if (neighborRow >= 0 && neighborRow < GRID_SIZE &&
                    neighborCol >= 0 && neighborCol < GRID_SIZE) {
                    int neighborColor = grid[neighborRow][neighborCol];
                    if (neighborColor > 0) {
                        liveCount++;
                        counts.put(neighborColor, counts.getOrDefault(neighborColor, 0) + 1);
                    }
                }
            }
        }
        counts.put(0, liveCount); // Usa a chave 0 para o total de vizinhos vivos
        return counts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameOfLifeGUI());
    }
}