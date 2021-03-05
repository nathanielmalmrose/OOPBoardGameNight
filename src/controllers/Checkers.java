package controllers;

import models.Player;

public class Checkers {

    private Player playerOne;
    private Player playerTwo;

    public Checkers() {

    }

    public Checkers(Player playerOne, Player playerTwo) {
        setPlayerOne(playerOne);
        setPlayerTwo(playerTwo);
        playGame();
    }

    public void playGame() {

        System.out.println("Checkers was played between " + getPlayerOne().getPlayerName() + " and " + getPlayerTwo().getPlayerName());
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
