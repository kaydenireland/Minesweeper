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

    protected void addNearbyBomb() {
        this.nearbyBombs++;
    }

    public void click() {
        switch (this.state) {
            case HIDDEN:
                if (bomb) {
                    this.setState(CellState.BOMB);
                } else {
                    this.setState(CellState.REVEALED);
                }
                break;

            case FLAGGED:
                this.setState(CellState.HIDDEN);
                break;

            default:
                break;
        }
    }

    public void print() {
        switch (this.state) {
            case HIDDEN -> System.out.print(" -");
            case REVEALED -> System.out.print(this.nearbyBombs > 0 ? (" " + this.nearbyBombs) : " .");
            case BOMB -> System.out.print(" B");
            case FLAGGED -> System.out.print(" f");
        }
    }

}
