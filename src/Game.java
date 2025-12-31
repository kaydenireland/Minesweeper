import java.util.*;

public class Game {
    private final int width;
    private final int height;
    private final int bombs;
    private final Cell[][] board;
    private boolean gameLoop;
    private boolean winCondition;

    public Game(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.board = initializeBoard();
        this.analyzeNeighbors();
        this.gameLoop = true;
        this.winCondition = false;
    }

    private Cell[][] initializeBoard() {
        Random rng = new Random();
        Cell[][] temp = new Cell[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                temp[x][y] = new Cell(false);
            }
        }

        List<int[]> positions = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                positions.add(new int[]{x, y});
            }
        }

        Collections.shuffle(positions, rng);

        for (int i = 0; i < bombs; i++) {
            int[] p = positions.get(i);
            temp[p[0]][p[1]] = new Cell(true);
        }

        return temp;
    }


    private void analyzeNeighbors() {
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (board[x][y].isBomb()) continue;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dy == 0) continue;

                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height
                                && board[nx][ny].isBomb()) {
                            board[x][y].addNearbyBomb();
                        }
                    }
                }

            }
        }
    }

    public void print() {
        int rowDigits = String.valueOf(height).length();
        int colDigits = String.valueOf(width).length();
        int cellWidth = Math.max(rowDigits, colDigits);

        System.out.print(" ".repeat(cellWidth + 1));

        // Column Label
        for (int x = 1; x <= width; x++) {
            System.out.printf("%" + cellWidth + "d ", x);
        }
        System.out.println();

        // Rows
        for (int y = 1; y <= height; y++) {
            // Row Label
            System.out.printf("%" + cellWidth + "d ", y);

            // Cells
            for (int x = 1; x <= width; x++) {
                board[x - 1][y - 1].print(cellWidth);
            }
            System.out.println();
        }
    }


    public void revealAll() {
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                this.board[w][h].reveal();
            }
        }
    }


    public void play() {
        Scanner input = new Scanner(System.in);
        String command;

        // First Turn
        // Not Implemented
        // Move mine if hit on first turn

        // Rest of Game
        while (gameLoop && !winCondition) {
            this.print();
            System.out.print("\nEnter Command or 'help' for Command List: ");   // Move to first turn later
            command = input.nextLine();
            this.parseCommand(command);
            this.checkWinCondition();
        }

        this.revealAll();
        this.print();
        if (winCondition) {
            System.out.println("\nWinner");
        } else {
            System.out.println("\nGame Over. Try Again.");
        }

    }

    public void parseCommand(String cmd) {
        String[] args = cmd.split(" ");

        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            this.help();
        } else if (args.length == 2) {
            try {
                int y = Integer.parseInt(args[0]) - 1;
                int x = Integer.parseInt(args[1]) - 1;

                if (x < 0 || x >= width || y < 0 || y >= height) {
                    System.out.println("Invalid Coordinates");
                    return;
                }

                board[x][y].click();
                if (board[x][y].isBomb()) this.gameLoop = false;
            } catch (NumberFormatException e) {
                System.out.println("Both args for click must be ints");
            }
        } else if (args.length == 3) {
            try {
                int y = Integer.parseInt(args[1]) - 1;
                int x = Integer.parseInt(args[2]) - 1;
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    System.out.println("Invalid Coordinates");
                    return;
                }

                if (args[0].equalsIgnoreCase("f") || args[0].equalsIgnoreCase("flag")) {
                    board[x][y].flag();
                } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("click")) {
                    board[x][y].click();
                    if (board[x][y].isBomb()) this.gameLoop = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Both args for click must be ints");
            }
        } else {
            System.out.println("Invalid Command");
        }
    }

    private void help() {
        System.out.println("Click Cell:\n\tx y\n\tc x y\n\tclick x y\nWhere x and y are ints, with the top left cell being 1 1");
        System.out.println("Flag Cell:\n\tx y\n\tf x y\n\tflag x y\nWhere x and y are ints, with the top left cell being 1 1");
        System.out.println("Help");
    }

    private void checkWinCondition() {
        for(int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (!board[w][h].isBomb() && !board[w][h].isRevealed()) return;
                if (!board[w][h].isBomb() && board[w][h].isFlagged()) return;
            }
        }
        winCondition = true;
    }

}
