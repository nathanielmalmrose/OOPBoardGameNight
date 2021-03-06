package controllers;

public class BoardSpace {

    private int col;
    private int row;

    public BoardSpace() {

    }

    public BoardSpace(int row, int col) {
        setCol(col);
        setRow(row);
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
