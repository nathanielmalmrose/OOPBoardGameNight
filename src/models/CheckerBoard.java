package models;

public class CheckerBoard {

    private CheckerPiece[][] board = new CheckerPiece[8][8];

    public CheckerBoard() {

    }
    public CheckerBoard(Player playerOne, Player playerTwo) {
        resetBoard(playerOne, playerTwo);
    }

    public void resetBoard(Player playerOne, Player playerTwo) {

        board = null;
        board = new CheckerPiece[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Player One
                if (row == 0 || row == 2) {
                    if (col %2 == 0) {
                        board[row][col] = new CheckerPiece(playerOne, "x");
                    }
                }
                if (row == 1) {
                    if (col %2 == 1){
                        board[row][col] = new CheckerPiece(playerOne, "x");
                    }
                }

                // Player Two
                if (row == 7 || row == 5) {
                    if (col %2 == 1) {
                        board[row][col] = new CheckerPiece(playerTwo, "o");
                    }
                }
                if (row == 6) {
                    if (col %2 == 0) {
                        board[row][col] = new CheckerPiece(playerTwo, "o");
                    }
                }
            }
        }
    }

    public CheckerPiece[][] getBoard() {
        return board;
    }

    public void movePiece(int[] startPiece, int row, int col) {
        board[row][col] = board[startPiece[0]][startPiece[1]];
        board[startPiece[0]][startPiece[1]] = null;
    }

    public void setBoard(CheckerPiece[][] board) {
        this.board = board;
    }


    public void checkKing(Player playerOne, Player playerTwo) {
        for (int col = 0; col < board.length; col++) {
            if (board[0][col].getPlayer().getPlayerName() == playerTwo.getPlayerName()) {
                board[0][col].setKing(true);
            }
            if (board[7][col].getPlayer().getPlayerName() == playerOne.getPlayerName()) {
                board[7][col].setKing(true);
            }
        }
    }

    public void printBoard() {
        System.out.println("[ ] [A] [B] [C] [D] [E] [F] [G] [H]");
        int row = 1;
        for (CheckerPiece[] spaces : this.board) {
            System.out.print("[" + row + "]\t");
            for (CheckerPiece space : spaces) {
                if (space == null) {
                    System.out.print("[ ]\t");
                } else {
                    System.out.print("[" + space.getColor() + "]\t");
                }
            }
            System.out.println();
            row++;
        }
        System.out.println("\n");
    }
}
