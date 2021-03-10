package models;

import controllers.Checkers;
import controllers.Move;

import java.util.ArrayList;

public class Piece {

        private Player player;
        private Tile tile;
        private int direction;
        private String color;
        private boolean isKing = false;

        public Piece() {

        }

        //  Initializes the Piece appropriately, and saves the supplied arguments.
        public Piece(Player p, Tile t, int dir, String color) {
            setPlayer(p);
            setTile(t);
            setDirection(dir);
            setColor(color);
        }

        //  line 58, instead of getting the checkerboard, create a new tile with that row and col.
        //  if it is within bounds, grab the legitimate tile, check if its blocked
        public Tile findLegalTile(int option) {
            Tile legalTile = null;
            Tile target = null;
            if (option == 0 && checkBounds(tile.getRow()+getDirection(), tile.getCol()+1)) {
                target = Checkers.getCheckerBoard()[tile.getRow()+getDirection()][tile.getCol()+1];
            } else if (option == 1 && checkBounds(tile.getRow()+getDirection(), tile.getCol()-1)) {
                target = Checkers.getCheckerBoard()[tile.getRow()+getDirection()][tile.getCol()-1];
            } else if (option == 2 && checkBounds(tile.getRow()-getDirection(), tile.getCol()+1)) {
                if (isKing) {
                    target = Checkers.getCheckerBoard()[tile.getRow()-getDirection()][tile.getCol()+1];
                }
            } else if (option == 3 && checkBounds(tile.getRow()-getDirection(), tile.getCol()-1)) {
                if (isKing) {
                    target = Checkers.getCheckerBoard()[tile.getRow()-getDirection()][tile.getCol()-1];
                }
            }
            if (!isBlocked(target)) {
                if (isJump(target)) {
                    int jumpedRow = (((target.getRow() - tile.getRow()) * 2) + tile.getRow());
                    int jumpedCol = (((target.getCol() - tile.getCol()) * 2) + tile.getCol());
                    target = Checkers.getCheckerBoard()[jumpedRow][jumpedCol];
                }
                legalTile = target;
            }
            return legalTile;
        }

        public ArrayList<Move> moveOptions() {
            ArrayList<Move> moves = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                boolean legal = findLegalTile(i) != null;
                if (legal) {
                    moves.add(new Move(tile, findLegalTile(i)));
                }
            }
            return moves;
        }

        public boolean isJump(Tile t) {
            boolean jump = false;
            if (isBlocked(t)) {
                jump = false;
            } else {
                if (t.isOccupied()) {
                    if (!t.getOccupant().getPlayer().getPlayerName().equals(player.getPlayerName())) {
                        jump = true;
                    }
                }
            }
            return jump;
        }

        public boolean isBlocked(Tile t) {
            boolean blocked = true;
            if (t != null) {

                if (t.isOccupied()) {
                    if (t.getOccupant().getPlayer().getPlayerName().equals(player.getPlayerName())) {
                        blocked = true;
                    } else if (t.getRow() == 0 || t.getCol() == 0 || t.getRow() == 7 || t.getCol() == 7) {
                        blocked = true;
                    } else {
                        if (tile.getCol() > t.getCol()) {
                            if ((tile.getRow() > t.getRow()) && checkBounds(t.getRow()-1, t.getCol()-1)) {
                                blocked = Checkers.getCheckerBoard()[t.getRow() - 1][t.getCol() - 1].isOccupied();
                            } else if (checkBounds(t.getRow()+1, t.getCol()-1)){
                                blocked = Checkers.getCheckerBoard()[t.getRow() + 1][t.getCol() - 1].isOccupied();
                            }
                        } else {
                            if ((tile.getRow() > t.getRow()) && checkBounds(t.getRow()-1, t.getCol()+1)) {
                                blocked = Checkers.getCheckerBoard()[t.getRow() - 1][t.getCol() + 1].isOccupied();
                            } else if (checkBounds(t.getRow()+1, t.getCol()+1)) {
                                blocked = Checkers.getCheckerBoard()[t.getRow() + 1][t.getCol() + 1].isOccupied();
                            }
                        }
                    }
                } else {
                    blocked = false;
                }
            }
            return blocked;
        }

        public boolean checkBounds(int row, int col) {
            boolean inBounds;
            if (col <= 7 && col >= 0 && row <= 7 && row >= 0) {
                inBounds = true;
            } else {
                inBounds = false;
            }
            return inBounds;
        }

        //  Returns the true if this Piece has been kinged, false otherwise.
        public boolean isKing() {
            return isKing;
        }

        //  Sets this Piece to be kinged so that it can move in all directions.
        public void king() {
            this.color = color.toUpperCase();
            isKing = true;
        }

        //  Returns the Player that this Piece belongs to.
        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public Tile getTile() {
            return tile;
        }

        public void setTile(Tile tile) {
            this.tile = tile;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

