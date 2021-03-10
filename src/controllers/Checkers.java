package controllers;

import controllers.Move;
import models.Player;
import models.Tile;

public class Checkers {

    private Player playerOne = null;
    private Player playerTwo = null;
    private static Tile[][] checkerBoard;
    private int playerTurn = 1;
    private Player winner = null;


    //  Initializes the game and creates the board, an 8 by 8 array of Tiles.
    //  Does not yet add Pieces to the Board.
    public Checkers() {

    }

    public Checkers(Player one, Player two) {
        addPlayer(one);
        addPlayer(two);
        checkerBoard = new Tile[8][8];
    }

    //  If the game has no players, sets the supplied Player to be Player One.
    //  If the game currently has one player, sets the supplied Player to be Player Two,
    //      assigns colors and directions to each Player as appropriate, and starts the game.
    //  If the game currently has two non-null players, does nothing.
    public void addPlayer(Player newPlayer) {
        if (playerOne == null) {
            playerOne = newPlayer;
            playerOne.setDirection(1);
            playerOne.setColor("x");
        } else if (playerTwo == null) {
            playerTwo = newPlayer;
            playerTwo.setDirection(-1);
            playerTwo.setColor("o");
        } else {
            System.out.println("The game already has two players.\n");
        }
    }



    //  Returns the Tile on the board at the specified row and column.
    public Tile getTile(int row, int col) {
        return checkerBoard[row][col];
    }

    //  Resets the board,
    //      removing any Pieces that are present from the board and either Player,
    //      adds 12 pieces to each Player in the correct configuration,
    //      sets the first Player added as the Player whose turn it is,
    //      and then initializes game play with the play method.
    protected void startGame() {
        clearBoard();
        setBoard();
    }

    public void clearBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                checkerBoard[row][col] = new Tile(row, col);
            }
        }
    }

    public void setBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Player One
                if (row == 0 || row == 2) {
                    if (col %2 == 0) {
                        checkerBoard[row][col].setOccupant(playerOne.addPiece(checkerBoard[row][col]));
                    }
                }
                if (row == 1) {
                    if (col %2 == 1){
                        checkerBoard[row][col].setOccupant(playerOne.addPiece(checkerBoard[row][col]));
                    }
                }

                // Player Two
                if (row == 7 || row == 5) {
                    if (col %2 == 1) {
                        checkerBoard[row][col].setOccupant(playerTwo.addPiece(checkerBoard[row][col]));
                    }
                }
                if (row == 6) {
                    if (col %2 == 0) {
                        checkerBoard[row][col].setOccupant(playerTwo.addPiece(checkerBoard[row][col]));
                    }
                }
            }
        }
    }

    //  While an end-game state is not reached,
    //      asks the Player whose turn it is for a move.
    //      If that move is not a legal move,
    //          keeps asking until a legal move is given.
    //      Once a valid move is supplied,
    //          the move is executed,
    //          and the turn is switched,
    //          giving the other Player a chance to move.
    protected void play() {
        while (!endGame()) {
            printBoard();
            if (playerTurn == 1) {
                takeTurn(playerOne);
                playerTurn = 2;
            } else {
                takeTurn(playerTwo);
                playerTurn = 1;
            }
        }
        declareWinner();
    }

    //  Returns true if either Player has no more Pieces.
    protected boolean endGame() {
        boolean noPieces = false;
        if (playerOne.getNumPieces() == 0 || playerOne.getMoveOptions().size() == 0) {
            noPieces = true;
            winner = playerTwo;
        } else if (playerTwo.getNumPieces() == 0 || playerTwo.getMoveOptions().size() == 0) {
            noPieces = true;
            winner = playerOne;
        }
        return noPieces;
    }

    public void declareWinner() {
        System.out.println(winner.getPlayerName() + " Wins!");
        winner.addPlayersWin(1);
    }

    public void takeTurn(Player p) {
        System.out.println(p.getPlayerName() + "(" + p.getColor() + ")'s turn: ");
        p.updatePieces();
        int moveChoice = p.chooseMove(p);
        movePiece(p.getMoveOptions().get(moveChoice - 1));
    }

    public void movePiece(Move m) {
        int endRow = m.getMoveEnd().getRow();
        int endCol = m.getMoveEnd().getCol();
        int startRow = m.getMoveStart().getRow();
        int startCol = m.getMoveStart().getCol();


        checkerBoard[endRow][endCol].setOccupant(checkerBoard[startRow][startCol].getOccupant());
        checkerBoard[endRow][endCol].getOccupant().setTile(checkerBoard[endRow][endCol]);
        checkerBoard[startRow][startCol].setOccupant(null);

        if (m.isCapture()) {
            int captRow = ((endRow - startRow) / 2) + startRow;
            int captCol = ((endCol - startCol) / 2) + startCol;
            checkerBoard[captRow][captCol].getOccupant().getPlayer().removePieces(checkerBoard[captRow][captCol].getOccupant());
            checkerBoard[captRow][captCol].setOccupant(null);

        }
        checkKing();
    }

    public void checkKing() {
        for (int col = 0; col < checkerBoard.length; col++) {
            if (checkerBoard[0][col].getOccupant() != null) {
                if (checkerBoard[0][col].getOccupant().getPlayer().getPlayerName() == playerTwo.getPlayerName()) {
                    checkerBoard[0][col].getOccupant().king();
                }
            }
            if (checkerBoard[7][col].getOccupant() != null) {
                if (checkerBoard[7][col].getOccupant().getPlayer().getPlayerName() == playerOne.getPlayerName()) {
                    checkerBoard[7][col].getOccupant().king();
                }
            }
        }
    }

    public void printBoard() {
        System.out.println("[ ] [0] [1] [2] [3] [4] [5] [6] [7] <- 2nd #");
        int row = 0;
        for (Tile[] spaces : this.checkerBoard) {
            System.out.print("[" + row + "]\t");
            for (Tile space : spaces) {
                if (space.getOccupant() == null) {
                    System.out.print("[ ]\t");
                } else {
                    System.out.print("[" + space.getOccupant().getColor() + "]\t");
                }
            }
            System.out.println();
            row++;
        }
        System.out.println("\n");
    }

    public static Tile[][] getCheckerBoard() {
        return checkerBoard;
    }

    public static void setCheckerBoard(Tile[][] checkerBoard) {
        Checkers.checkerBoard = checkerBoard;
    }
}
