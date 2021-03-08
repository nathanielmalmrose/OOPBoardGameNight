package controllers;

import models.CheckerBoard;
import models.CheckerPiece;
import models.Player;
import views.ConsoleIO;

import java.util.ArrayList;

public class Checkers {

    private Player playerOne;
    private Player playerTwo;
    private CheckerBoard checkerBoard = new CheckerBoard();
    private int playerTurn = 1;

    private ArrayList<BoardSpace> validMoves = new ArrayList<>();

    public Checkers() {

    }

    public Checkers(Player playerOne, Player playerTwo) {
        setPlayerOne(playerOne);
        setPlayerTwo(playerTwo);
        playGame();
    }

    public void takeTurn() {
        do {
            checkerBoard.printBoard();
            checkForWin();
            switch (playerTurn) {
                case 1:

//                    playerTurn = 2;
                    movePiece(playerTurn(playerOne));
                    break;
                case 2:
                    playerTurn = 1;
                    playerTurn(playerTwo);
                    break;
            }
        } while (playerTurn > 0);
    }

    // get player's pieces
    // to array for selection prompt

    //Prompt the user to select the piece

    //get the right-side piece
    //get the letf-sice piece

    // if right piece is enemy piece than do some logic
    // if the left piece is enemy piece than do some logic

    // add the new piece at new location
    // remove the enemy piece
    // remove the old piece at old location

    private int[] playerTurn(Player p) {
        CheckerPiece cp;
        int rowStart;
        int colStart;

        BoardSpace spaceOne = null;
        BoardSpace spaceTwo = null;
        BoardSpace spaceThree = null;
        BoardSpace spaceFour = null;


        boolean invalidMove = true;
        do {
            do {
                colStart = ConsoleIO.promptForInt("Choose col", 0, 7);
                rowStart = ConsoleIO.promptForInt("Choose row", 0, 7);
                cp = checkerBoard.getBoard()[rowStart][colStart];
            } while (!cp.getPlayer().getPlayerName().equals(p.getPlayerName()));

            BoardSpace startSpace = new BoardSpace(rowStart, colStart);
            if (p.getPlayerNum() == 1) {
                if (checkerBoard.getBoard()[rowStart + 1][colStart + 1] == null) {
                    spaceOne = new BoardSpace(rowStart + 1, colStart + 1);
                } else if (checkerBoard.getBoard()[rowStart + 1][colStart + 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName())) {
                    spaceOne = null;
                } else if (!checkerBoard.getBoard()[rowStart + 1][colStart + 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName()) &&
                        (checkerBoard.getBoard()[rowStart + 1][colStart + 1] != null) &&
                        (checkerBoard.getBoard()[rowStart + 2][colStart + 2] != null)) {
                    spaceOne = new BoardSpace(rowStart + 2, colStart + 2);
                }

                if (checkerBoard.getBoard()[rowStart + 1][colStart - 1] == null) {
                    spaceTwo = new BoardSpace(rowStart + 1, colStart - 1);
                } else if (checkerBoard.getBoard()[rowStart + 1][colStart - 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName())) {
                    spaceTwo = null;
                } else if (!checkerBoard.getBoard()[rowStart + 1][colStart - 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName()) &&
                        (checkerBoard.getBoard()[rowStart + 1][colStart - 1] != null) &&
                        (checkerBoard.getBoard()[rowStart + 2][colStart - 2] != null)) {
                    spaceTwo = new BoardSpace(rowStart + 2, colStart - 2);
                }

                if (cp.isKing()) {
                    if (checkerBoard.getBoard()[rowStart - 1][colStart + 1] == null) {
                        spaceThree = new BoardSpace(rowStart + 1, colStart + 1);
                    } else if (checkerBoard.getBoard()[rowStart - 1][colStart + 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName())) {
                        spaceThree = null;
                    } else if (!checkerBoard.getBoard()[rowStart - 1][colStart + 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName()) &&
                            (checkerBoard.getBoard()[rowStart - 1][colStart + 1] != null) &&
                            (checkerBoard.getBoard()[rowStart - 2][colStart + 2] != null)) {
                        spaceThree = new BoardSpace(rowStart - 2, colStart + 2);
                    }

                    if (checkerBoard.getBoard()[rowStart - 1][colStart - 1] == null) {
                        spaceFour = new BoardSpace(rowStart + 1, colStart - 1);
                    } else if (checkerBoard.getBoard()[rowStart - 1][colStart - 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName())) {
                        spaceFour = null;
                    } else if (!checkerBoard.getBoard()[rowStart - 1][colStart - 1].getPlayer().getPlayerName().equals(cp.getPlayer().getPlayerName()) &&
                            (checkerBoard.getBoard()[rowStart - 1][colStart - 1] != null) &&
                            (checkerBoard.getBoard()[rowStart - 2][colStart - 2] != null)) {
                        spaceFour = new BoardSpace(rowStart + 2, colStart + 2);
                    }
                }
            }

            validMoves.clear();
            if (spaceOne != null) {
                validMoves.add(spaceOne);
                invalidMove = false;
            }
            if (spaceTwo != null) {
                validMoves.add(spaceTwo);
                invalidMove = false;
            }
            if (spaceThree != null) {
                validMoves.add(spaceThree);
                invalidMove = false;
            }
            if (spaceFour != null) {
                validMoves.add(spaceFour);
                invalidMove = false;
            }
            if (spaceOne == null && spaceTwo == null && spaceThree == null && spaceFour == null) {
                System.out.println("Please choose another piece: ");
            }
        } while (invalidMove);
        return new int[] {rowStart, colStart};
    }

    private void movePiece(int[] startPiece) {
        int playerChoice = ConsoleIO.promptForMenuSelection(validMovesToArray(), false);

        int rowChoice = validMoves.get(playerChoice-1).getRow();
        int colChoice = validMoves.get(playerChoice-1).getCol();

        checkerBoard.movePiece(startPiece, rowChoice, colChoice);
        
    }


    private void checkForWin() {
        //  Scan the board for null
        //  checkMoves() passing in every piece
    }

    public String[] validMovesToArray() {

        String[] validMoveArray = new String[validMoves.size()];
        for (int i = 0; i < validMoves.size(); i++) {
            validMoveArray[i] = validMoves.get(i).toString();
        }
        return validMoveArray;
    }




    public void playGame() {
        checkerBoard.resetBoard(playerOne, playerTwo);
        checkerBoard.printBoard();
        takeTurn();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }
}
