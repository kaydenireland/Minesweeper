enum CellState {
    HIDDEN,
    REVEALED,
    FLAGGED,
    BOMB
}

public class Cell {

    private CellState state;
    private final boolean bomb;
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
            case HIDDEN   -> "-";
            case BOMB     -> "B";
            case FLAGGED  -> "f";
            case REVEALED -> (nearbyBombs > 0 ? String.valueOf(nearbyBombs) : ".");
        };

        System.out.printf("%" + cellWidth + "s ", s);
    }


}
