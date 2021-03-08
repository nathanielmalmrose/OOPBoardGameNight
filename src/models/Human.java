package models;

public class Human extends Player {
    private int score;

    //adds what ever int is passed in to the score
    public void addToScore(int score){
        this.score += score;
    }

    //returns the score of the player
    public int getScore(){
        return score;
    }

    public Human() {

    }

    public Human(String playerName) {
        setPlayerName(playerName);
    }

}
