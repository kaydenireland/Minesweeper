import java.util.Random;

public class Game {
    private final int width;
    private final int height;
    private final int bombs;
    private final Cell[][] board;

    public Game(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.board = initializeBoard();
        this.analyzeNeighbors();
    }

    private Cell[][] initializeBoard() {
        Random rng = new Random();
        final int area = width * height;
        final int bombChance = Math.max((area / bombs), 1);


        Cell[][] temp = new Cell[width][height];

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                temp[w][h] = new Cell(rng.nextInt(bombChance) == 0);
            }
        }

        return temp;
    }

    private void analyzeNeighbors() {
        for(int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {

                // Above
                if (h - 1 >= 0) {
                    // Left
                    if (w - 1 >= 0 && board[w - 1][h - 1].isBomb()) board[w][h].addNearbyBomb();
                    // Center
                    if (board[w][h - 1].isBomb()) board[w][h].addNearbyBomb();
                    // Right
                    if (w + 1 < board.length && board[w + 1][h - 1].isBomb()) board[w][h].addNearbyBomb();
                }
                // Below
                if (h + 1 < board[0].length) {
                    // Left
                    if (w - 1 >= 0 && board[w - 1][h + 1].isBomb()) board[w][h].addNearbyBomb();
                    // Center
                    if (board[w][h + 1].isBomb()) board[w][h].addNearbyBomb();
                    // Right
                    if (w + 1 < board.length && board[w + 1][h + 1].isBomb())board[w][h].addNearbyBomb();
                }
                // Sides
                {
                    // Left
                    if (w - 1 >= 0 && board[w - 1][h].isBomb()) board[w][h].addNearbyBomb();
                    // Right
                    if (w + 1 < board.length && board[w + 1][h].isBomb()) board[w][h].addNearbyBomb();
                }

            }
        }
    }

    public void print() {
        for(int h = 0; h < height; h++) {
            System.out.println();
            for(int w = 0; w < width; w++) {
                this.board[w][h].print();
            }
        }
    }

    public void revealAll() {
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                this.board[w][h].click();
                this.board[w][h].click();
            }
        }
    }

}
