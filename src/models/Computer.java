package models;

import java.util.Random;

public class Computer extends Player {

    public Computer() {
        setPlayerName("Computer");
    }

    //  Each derived class will implement this method to specify the move they will make when it is their turn.
    //  Returns a Move object which represents the chosen move.
    @Override
    public int chooseMove(Player p) {
        int choice = randomGenerator(0, getMoveOptions().size() - 1);

        return choice;
    }

    private int randomGenerator(int min, int max) {
        Random randomValue = new Random();
        int randomNum = randomValue.nextInt((max + 1) - min) + 1;
        return randomNum;
    }
}
