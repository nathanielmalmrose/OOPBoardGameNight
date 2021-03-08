package models;

import java.util.Random;

public class Die {
    private boolean isPutAside;
    private int rollResult;

    //used for testing different winning dice combinations
    public void setRollResult(int result){
        this.rollResult = result;
    }

    //generates a random num between 1 and 6 inclusive
    public void roll(){
        rollResult = generateRandomInt(1,6);
    }

    //returns the roll result for the die
    public int getRollResult() {
        return rollResult;
    }

    //sets the property of isPutAside to true
    public void putAside(){
        isPutAside = true;
    }

    //returns if the die is put aside or not
    public boolean getIsPutAside(){
        return isPutAside;
    }

    //resets the die to its original starting values
    public void resetDie(){
        isPutAside = false;
        rollResult = 0;
    }

    //generates a random number based on the min and max
    public static int generateRandomInt(int min, int max){
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }
}
