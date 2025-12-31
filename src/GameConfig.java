import java.util.Scanner;

public class GameConfig {
    int width;
    int height;
    int bombCount;

    public GameConfig(String[] args) {
        if (args.length == 3) {
            try {
                this.width = Integer.parseInt(args[0]);
                this.height = Integer.parseInt(args[1]);
                this.bombCount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("All 3 args must be ints");
                System.exit(0);
            }
        }
        else if (args.length == 1) {
            this.initDifficulty(args[0]);
        }
        else {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter Custom Settings Y/N? (Default: N): ");
            String custom = in.nextLine().toLowerCase();

            if (custom.equals("y") || custom.equals("yes")) {
                System.out.print("Enter Width: ");
                this.width = in.nextInt();
                System.out.print("Enter Height: ");
                this.height = in.nextInt();
                System.out.print("Enter Bomb Count: ");
                this.bombCount = in.nextInt();
            } else {
                setDefault();
            }
        }

        System.out.println("Welcome to Minesweeper");
    }


    private void initDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy" -> set(10, 8, 10);
            case "medium" -> set(18, 14, 40);
            case "hard" -> set(28, 24, 100);
            default -> {
                System.out.println("Available difficulties: EASY, MEDIUM, HARD");
                System.exit(0);
            }
        }
    }

    private void setDefault() {
        set(10, 8, 10);
    }

    private void set(int w, int h, int b) {
        this.width = w;
        this.height = h;
        this.bombCount = b;
    }


}
