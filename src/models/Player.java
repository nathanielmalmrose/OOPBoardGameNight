package models;

import controllers.Checkers;
import controllers.Move;
import java.util.ArrayList;

public abstract class Player {

    private int playerWinCount = 0;
    private int playerNum;private String playerName;
    private String color;
    private int direction;

    private ArrayList<Piece> pieces = new ArrayList<>();

    private ArrayList<BJCard> playerHand = new ArrayList<>();

    public Player() {
    }

    public Player(String playerName, int playerNum) {
        setPlayerName(playerName);
        setPlayerNum(playerNum);
    }

    public Player(String playerName) {
        setPlayerName(playerName);
    }

    public void addPlayersWin(int numberOfWins){
        this.playerWinCount += numberOfWins;
    }

    public int getPlayerWinCount(){
        return playerWinCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
        //this is blackjack!

    public BJCard getCard(int i) {
        return this.playerHand.get(i);
    }
    public void removeCard(int i) {
        this.playerHand.remove(i);
    }

    public void MovetoDeck(BJCardDeck moveTo){
            int deckSize = this.playerHand.size();
            int i;
            for (i =0; i < deckSize;++i){
                moveTo.addCard(this.getCard(i));
            }
            for(i = 0; i < deckSize; ++i) {
                this.removeCard(0);
            }
        }

    //Add a card into your hand
    public void addCard(BJCard aCard){
        playerHand.add(aCard);
    }
    //will see the value of your hand

    public int sumOfHand(){
        int totalSum = 0;
        for(BJCard countSum: playerHand){
            totalSum = totalSum + countSum.getFaceValue();
        }
        return totalSum;
    }
    //this is the player hand and hides the card if needed you
    public String getHandAsString(boolean hideCard) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPlayerName()+"\'s current hand:");
        sb.append('\n');
        for (int i = 0; i < playerHand.size(); i++) {
            if (i == 0 && hideCard) {
                sb.append("[Hidden card]");
                sb.append('\n');
            } else {
                sb.append(playerHand.get(i));
                sb.append('\n');
            }
        }
        return sb.toString();
    }


    //This is blackJack!!
    @Override
    public String toString() {
        return getPlayerName();
    }


    //  Checkers
    public abstract int chooseMove(Player p);

    //  Creates a new Piece and adds it to the Player's list of Pieces. Returns the created Piece.
    public Piece addPiece(Tile initialPosition) {
        Piece p = new Piece(this, initialPosition, this.direction, this.color);
        if (!initialPosition.isOccupied()) {
            pieces.add(p);
        }
        return p;
    }

    public void updatePieces() {
        pieces.clear();
        for (Tile[] tiles : Checkers.getCheckerBoard()) {
            for (Tile tile : tiles) {
                if (tile.getOccupant() != null) {
                    if (tile.getOccupant().getPlayer().getPlayerName().equals(this.getPlayerName())) {
                        pieces.add(tile.getOccupant());
                    }
                }
            }
        }
    }

    public ArrayList<Move> getMoveOptions() {
        ArrayList<Move> options = new ArrayList<>();

        for (Piece piece : getPieces()) {
            for (Move move : piece.moveOptions()) {
                options.add(move);
            }
        }

        return options;
    }

    public String[] moveOptionsToArray(ArrayList<Move> options) {
        String[] moves = new String[options.size()];
        for (int i = 0; i < moves.length; i++) {
            moves[i] = options.get(i).toString();
        }
        return moves;
    }

    //  Returns all of the Player's remaining Pieces as an array of Pieces.
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public int getNumPieces() {
        return pieces.size();
    }

    //  Removes the provided Piece from the player's list of Pieces.
    public void removePieces(Piece remove) {
        ArrayList<Piece> toRemove = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece.getTile().equals(remove.getTile())) {
                toRemove.add(piece);
            }
        }
        pieces.removeAll(toRemove);
    }

    //  Sets this Player's color to the color specified.
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getDirection() {
        return direction;
    }

    //  Sets the direction in which the Player's un-kinged Pieces can legally move.
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
