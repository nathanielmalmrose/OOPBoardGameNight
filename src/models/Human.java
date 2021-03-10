package models;


import views.ConsoleIO;

import java.io.Serializable;

public class Human extends Player implements Serializable {
    private int score;

    //used for testing the leader board printing
    public Human(String playerName, int playerWinCount) {
        setPlayerName(playerName);
        addPlayersWin(playerWinCount);
    }

    //  Each derived class will implement this method to specify the move they will make when it is their turn.
    //  Returns a Move object which represents the chosen move.
    @Override
    public int chooseMove(Player p) {
        int choice = ConsoleIO.promptForMenuSelection(moveOptionsToArray(getMoveOptions()), false);

        return choice;
    }

    public Human(String playerName) {
        setPlayerName(playerName);
    }

    //adds what ever int is passed in to the score
    public void addToScore(int score) {
        this.score += score;
    }

    //returns the score of the player
    public int getScore() {
        return score;
    }
}