import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        GameConfig config = new GameConfig(args);

        Game game = new Game(config.width, config.height, config.bombCount);
        game.play();
    }
}

