package models;

public class Human extends Player {
    private int score;

    //used for testing the leader board printing
    public Human(String playerName, int playerWinCount) {
        setPlayerName(playerName);
        addPlayersWin(playerWinCount);
    }

    public Human() {

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
}
