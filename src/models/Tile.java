package models;

public class Tile {

    private int row;
    private int col;
    private Piece occupant;
    private boolean isOccupied = false;

    public Tile() {

    }

    //  Initializes the Tile with the given row and column.
    public Tile(int row, int col) {
        setRow(row);
        setCol(col);
    }

    //  Returns true if the Tile has a Piece on it, false otherwise.
    public boolean isOccupied() {
        if (occupant == null) {
            isOccupied = false;
        } else {
            isOccupied = true;
        }
        return isOccupied;
    }

    //  Returns the Piece that is occupying this tile, or null if the Tile has no occupant.
    public Piece getOccupant() {
        return occupant;
    }

    //  Sets the occupant of this Tile to the Piece specified.
    public void setOccupant(Piece newOccupant) {
        if (newOccupant != null) {
            setOccupied(true);
        } else {
            setOccupied(false);
        }
        this.occupant = newOccupant;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }
}
