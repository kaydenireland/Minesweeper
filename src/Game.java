import java.util.Random;
import java.util.Scanner;

public class Game {
    private final int width;
    private final int height;
    private final int area;
    private final int bombs;
    private final Cell[][] board;
    private boolean gameLoop;
    private boolean winCondition;

    public Game(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.area = width * height;
        this.bombs = bombs;
        this.board = initializeBoard();
        this.analyzeNeighbors();
        this.gameLoop = true;
        this.winCondition = false;
    }

    private Cell[][] initializeBoard() {
        Random rng = new Random();
        final int bombChance = Math.max((this.area / bombs), 1);
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

                if (board[w][h].isBomb()) continue;

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
        System.out.print(".");
        for(int w = 0; w < width; w++) {
            System.out.print(" " + (w + 1));
        }
        for(int h = 0; h < height; h++) {
            System.out.print("\n" + (h + 1));
            for(int w = 0; w < width; w++) {
                this.board[w][h].print();
            }
        }
        System.out.println();
    }

    public void revealAll() {
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                this.board[w][h].click();
                this.board[w][h].click();
            }
        }
    }


    public void play() {
        Scanner input = new Scanner(System.in);
        String command = "";

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

                try {
                    board[x][y].click();
                    if (board[x][y].isBomb()) this.gameLoop = false;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid Coordinates");
                }
            } catch (NumberFormatException e) {
                System.out.println("Both args for click must be ints");
                System.exit(0);
            }
        } else if (args.length == 3) {
            try {
                int y = Integer.parseInt(args[1]) - 1;
                int x = Integer.parseInt(args[2]) - 1;
                try {
                    if (args[0].equalsIgnoreCase("f") || args[0].equalsIgnoreCase("flag")) {
                        board[x][y].flag();
                    } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("click")) {
                        board[x][y].click();
                        if (board[x][y].isBomb()) this.gameLoop = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid Coordinates");
                }
            } catch (NumberFormatException e) {
                System.out.println("Both args for click must be ints");
                System.exit(0);
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
