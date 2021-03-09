package models;

import java.util.ArrayList;

public class Human extends Player {
    private int score;
    private String nickName;
    private int playerNumOfCards;
    private ArrayList<BJCard> playerHand = new ArrayList<>();

    //used for testing the leader board printing
    public Human(String playerName, int playerWinCount) {
        setPlayerName(playerName);
        addPlayersWin(playerWinCount);
    }

    public Human(String playerName) {
        setPlayerName(playerName);
    }

    //adds what ever int is passed in to the score
    public void addToScore(int score){
        this.score += score;
    }

    //returns the score of the player
    public int getScore(){
        return score;
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
}
