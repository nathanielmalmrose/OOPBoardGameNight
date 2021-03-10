package models;

import java.util.ArrayList;

public abstract class Player {

        private int playerWinCount = 0;
        private int playerNum;
        private String playerName;
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
}
