package controllers;

import models.Tile;

public class Move {

//    A Move represents the most basic unit of play in Checkers.
//    We will be using an object to encapsulate a move so we can later save, load, undo, and redo Moves in a game.
//    A potential Move should take in two Tiles on the board.
//    From those Tiles, it should infer the Piece that was moved, and the Piece that was captured, and save both, if applicable.
//    Once set, the details of a Move should never change.
//    You can define accessors if you like, but since a Move should be immutable, it needs no other methods, and its members can have public visibility.
//    A Move does not perform any type of check as to its legality - it is perfectly acceptable to create an illegal Move
//      (however, that Move should never be executed by the Game).
//    Do not worry about complex jumps (complex moves that capture multiple pieces).

    private Tile moveStart;
    private Tile moveEnd;
    private boolean capture;

    public Move() {

    }

    public Move(Tile s, Tile e) {
        moveStart = s;
        moveEnd = e;
        if (Math.abs(e.getRow() - s.getRow()) > 1) {
            capture = true;
        } else {
            capture = false;
        }
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public Tile getMoveStart() {
        return moveStart;
    }

    public void setMoveStart(Tile moveStart) {
        this.moveStart = moveStart;
    }

    public Tile getMoveEnd() {
        return moveEnd;
    }

    public void setMoveEnd(Tile moveEnd) {
        this.moveEnd = moveEnd;
    }

    @Override
    public String toString() {
        return moveStart.getRow() + "," + moveStart.getCol() + " move to " + moveEnd.getRow() + "," + moveEnd.getCol();
    }
}
