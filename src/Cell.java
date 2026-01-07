enum CellState {
    HIDDEN,
    REVEALED,
    FLAGGED,
    BOMB
}

public class Cell {

    private CellState state;
    private boolean bomb;
    private int nearbyBombs;

    public Cell(boolean bomb) {
        this.state = CellState.HIDDEN;
        this.bomb = bomb;
        this.nearbyBombs = 0;
    }

    private void setState(CellState state) {
        this.state = state;
    }

    protected boolean isBomb() {
        return bomb;
    }

    protected void setBomb(boolean set) {
        this.bomb = set;
    }

    protected boolean isFlagged() {
        return this.state == CellState.FLAGGED;
    }

    protected boolean isRevealed() {
        return this.state == CellState.REVEALED;
    }

    protected void addNearbyBomb() {
        this.nearbyBombs++;
    }

    protected int getNearbyBombs() {
        return this.nearbyBombs;
    }

    protected void resetNearbyBombs() {
        this.nearbyBombs = 0;
    }

    @Deprecated
    public void click() {
        if (this.state == CellState.HIDDEN) {
            if (bomb) {
                this.setState(CellState.BOMB);
            } else {
                this.setState(CellState.REVEALED);
            }
        }
    }

    public void flag() {
        if (this.state == CellState.HIDDEN) this.setState(CellState.FLAGGED);
        else if(this.state == CellState.FLAGGED) this.state = CellState.HIDDEN;
    }

    public void reveal() {
        switch (this.state) {
            case HIDDEN, FLAGGED -> {
                if (bomb) {
                    this.setState(CellState.BOMB);
                } else {
                    this.setState(CellState.REVEALED);
                }
            }

            default -> {}
        }
    }

    public void print(int cellWidth) {
        String s = switch (state) {
            case HIDDEN ->
                    ColorPrinter.format("-", null, null, TextStyle.BOLD);

            case BOMB ->
                    ColorPrinter.format("B", TextColor.BLACK, TextColor.RED, TextStyle.BOLD);

            case FLAGGED ->
                    ColorPrinter.format("f", TextColor.RED, null, TextStyle.BOLD, TextStyle.ITALIC);

            case REVEALED -> switch (nearbyBombs) {
                case 0 ->
                        ColorPrinter.format(".", TextColor.BLACK, null, null);

                case 1 ->
                        ColorPrinter.format("1", TextColor.BLUE, null, TextStyle.BOLD);

                case 2 ->
                        ColorPrinter.format("2", TextColor.GREEN, null, TextStyle.BOLD);

                case 3 ->
                        ColorPrinter.format("3", TextColor.YELLOW, null, TextStyle.BOLD);

                case 4 ->
                        ColorPrinter.format("4", TextColor.PURPLE, null, TextStyle.BOLD);

                case 5 ->
                        ColorPrinter.format("5", TextColor.RED, null, TextStyle.BOLD);

                case 6 ->
                        ColorPrinter.format("6", TextColor.CYAN, null, TextStyle.BOLD);

                case 7 ->
                        ColorPrinter.format("7", TextColor.BLACK, null, TextStyle.BOLD);

                case 8 ->
                        ColorPrinter.format("8", TextColor.WHITE, null, TextStyle.BOLD);

                default ->
                        ColorPrinter.format(String.valueOf(nearbyBombs), TextColor.WHITE, null, TextStyle.STRIKETHROUGH);
            };
        };


        System.out.printf("%" + cellWidth + "s ", s);
    }


}
