package controllers;

import models.Die;
import models.Human;
import views.ConsoleIO;

import java.util.ArrayList;
import java.util.Collections;

public class Farkle {
    private ArrayList<Human> playersPlaying = new ArrayList<>(); //stores all the players playing
    private ArrayList<Human> playerTurns = new ArrayList<>(); //stores the players in the order which they play in
    private ArrayList<Die> dice = new ArrayList<>(); //has Die objects in it which can be rolled and put aside
    private int currentPlayer = 0; //which player is currently playing used as an index in playerTurns
    private int potentialScore = 0; //shows the player what their score for this round is so far
    private int ones = 0; //stores how many ones are rolled in the dice used for checking winning dice
    private int twos = 0; //the next four are all like the first "ones"
    private int threes = 0;
    private int fours = 0;
    private int fives = 0;
    private int sixes = 0;

    //starts the game
    public void startFarkle(ArrayList<Human> playersPlaying){
        getPlayersPlaying(playersPlaying);
        setTurns();
        takeTurns();
    }

    public void takeTurns(){
        //this will loop until someone won
        boolean noOneWon = true;
        do {
            //if the current player gets added to the size of the players playing it means we need to go back to the first
            if (currentPlayer == playerTurns.size()){
                currentPlayer = 0;
            }
            //the current player plays and then gets their score checked if its over 10,000
            playerPlay(playerTurns.get(currentPlayer));
            int score = playerTurns.get(currentPlayer - 1).getScore();
            if (score >= 10000){
                //if the score is more that player gets assigned to the winner
                Human winner = playerTurns.get(currentPlayer - 1);
                noOneWon = false;
                //current player gets set to 0 so we go through all the players one more time
                currentPlayer = 0;
                playerTurns.remove(winner);
                //if someone gets a higher score then the original winner they get assigned to be the winner
                winner = lastTurn(score, winner);

                ConsoleIO.printText("\n\nPlayer: " + winner.getPlayerName() + " won the game with the score of: " + winner.getScore());
                winner.addPlayersWin(1);
            }
        } while (noOneWon);
    }

    public Human lastTurn(int highScore, Human winner){
        ConsoleIO.printText("Player: " + winner.getPlayerName() + " got over 10,000 points all other players get to play one more time to try to beat their high score");
        int highestScore = highScore;
        //all the remaining players take one more turn
        do {
            playerPlay(playerTurns.get(currentPlayer));
        } while (currentPlayer != playerTurns.size());

        //check if anyone has a higher score then the winner if they do they get returned as the new winner
        for (Human player: playerTurns) {
            if (player.getScore() > highestScore){
                highestScore = player.getScore();
                winner = player;
            }
        }
        return winner;
    }

    public void playerPlay(Human player){
        setDice();
        int nextPlayer = 0;
        //say who is playing and what their score is
        ConsoleIO.printText("\nIts players: " + playerTurns.get(currentPlayer).getPlayerName() + "'s turn");
        ConsoleIO.printText("Your score is: " + playerTurns.get(currentPlayer).getScore());

        do {
            //roll all the die which are not put aside
            for (Die die : dice) {
                if (!die.getIsPutAside()) {
                    die.roll();
                }
            }
            ConsoleIO.printDice(dice);
            //if its not a farkle it will ask them if they want to roll again or pass to the next player
            if (!checkUsersChoice()) {
                ConsoleIO.printText("\nWhat would you like to do next?");
                String[] prompt = {"Roll Again", "Pass to the Next Player"};
                nextPlayer = ConsoleIO.promptForMenuSelection(prompt, false);

                //if they pass to the next their score will get saved and the dice will get cleared for the next player
                if (nextPlayer == 2){
                    dice.clear();
                    playerTurns.get(currentPlayer).addToScore(potentialScore);
                    ConsoleIO.printText("Your score is: " + playerTurns.get(currentPlayer).getScore());
                    currentPlayer++;
                    potentialScore = 0;
                }
            }
            //if its a farkle it will go to the next player after clearing the dice as well
            else{
                dice.clear();
                currentPlayer++;
                nextPlayer = 2;
                potentialScore = 0;
            }
        } while (nextPlayer == 1);

    }

    public void putAsideDie(int numToRemove){
        //"numToRemove" is a code for which win the user chose
        int dieRemove = 0;
        switch (numToRemove){
            case 1:
                dieRemove = 1;
                break;
            case 3:
                dieRemove = checkForThree();
                break;
            case 4:
                dieRemove = checkForFour();
                break;
            case 5:
                dieRemove = checkForFive();
                break;
            case 6:
                dieRemove = checkForSix();
                break;
            case 55:
                dieRemove = 5;
                break;
        }
        //which ever die needs to be put aside will get put aside here
        for (Die die : dice) {
            if (die.getRollResult() == dieRemove) {
                die.putAside();
            }
        }
    }

    //if there is a farkle it will return true
    public boolean checkUsersChoice(){
        //we get the possible options and prompt the user for which die they want to remove
        ArrayList<String> options = checkForWinningDice();
        try {
            if (options.size() > 0) ConsoleIO.printText("\nWhich of the following moves would you like to do?");

            int choice = ConsoleIO.promptForMenuSelection(options.toArray(new String[0]), false);
            //the choice the user made gets assigned to a string
            String choiceString = options.get(choice - 1);

            //compares the string to see which option was chosen and adds the correct points and removes the correct dice
            userChoiceRespond(choiceString);

            //tell the user what their potential score is and then check if there is a hot dice
            ConsoleIO.printText("Your potential score for this round is: " + potentialScore);
            checkHotDice();

            return false;

        } catch (IllegalArgumentException iae){
            //If the array returned is empty it means that there is no winning dice which means its a farkle
            ConsoleIO.printText("\nYou Got a Farkle\nYour score is: " + playerTurns.get(currentPlayer).getScore());
            return true;
        }
    }

    public void userChoiceRespond(String choiceString){
        //the string is compared to see which option was chosen
        //each option removes a different die as well as assigns the potential score to do with that set of dice
        if (choiceString.startsWith("Three")){
            putAsideDie(3);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Four")){
            putAsideDie(4);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Five")){
            putAsideDie(5);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Six")){
            putAsideDie(6);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Ones")){
            putAsideDie(1);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Single")){
            //55 is to remove any fives if the user chooses this option
            putAsideDie(55);
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("1-6")){
            for (Die die : dice) {
                die.putAside();
            }
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Triplets")){
            for (Die die : dice) {
                die.putAside();
            }
            potentialScore += getScore(choiceString);
        }
        else if (choiceString.startsWith("Pairs")){
            for (Die die : dice) {
                die.putAside();
            }
            potentialScore += getScore(choiceString);
        }
    }

    public void checkHotDice(){
        //if all the dice are put aside it means its a hot dice and the user can re-roll all six dices again
        int allTrue = 0;
        for (Die die : dice) {
            if(die.getIsPutAside()){
                allTrue++;
            }
        }
        if (allTrue == 6){
            ConsoleIO.printText("\nYou got a hot dice! You get to re-roll all 6 of your dice.");
            for (Die die : dice) {
                die.resetDie();
            }
        }
    }

    public int getScore(String choice){
        //get the points corresponding with the set of dice which then will get assigned to the potential score
        String[] allWords = choice.split(" ");
        String pointsString = allWords[allWords.length - 2];
        try {
            return Integer.parseInt(pointsString);
        } catch (IllegalArgumentException iae){
            return 0;
        }
    }

    public ArrayList<String> checkForWinningDice(){
        //returns an array of strings which are the possible options of the dice the user can put aside
        ArrayList<String> winningDiceOptions = new ArrayList<>();

        //checks how many of each each sides are rolled
        initialCheck();

        //the first four return a number for the side which was rolled the correct amount of times
        //for example with three if there are three fours that were rolled it will return a four
        //if there is nothing matching it returns a zero so the first four check that its not a zero if not it shows what the winning option is
        int three = checkForThree();
        if (three != 0){
            if (three == 1) winningDiceOptions.add("Three " + three + "'s: 300 points");
            else winningDiceOptions.add("Three " + three + "'s: " + (three * 100) + " points");
        }
        int four = checkForFour();
        if (four != 0){
            winningDiceOptions.add("Four " + four + "'s: 1000 points");
        }
        int five = checkForFive();
        if (five != 0){
            winningDiceOptions.add("Five " + five + "'s: 2000 points");
        }
        int six = checkForSix();
        if (six != 0){
            winningDiceOptions.add("Six " + six + "'s: 3000 points");
        }


        //the next two return an array of how many single ones or fives were found
        // if its more then 0 it will show the user how many were found
        int singleOnes = checkForSingleOnes().size();
        if (singleOnes != 0){
            winningDiceOptions.add("Ones x" + singleOnes + ": " + (singleOnes * 100) + " points");
        }
        int singleFives = checkForSingleFives().size();
        if (singleFives != 0){
            winningDiceOptions.add("Single Fives x" + singleFives + ": " + (singleFives * 50) + " points");
        }


        //the last three return a boolean if they are true as they involve all 6 dice
        //if its true it will show the user what was the option and how many points it equals to
        if (checkForOrder()){
            winningDiceOptions.add("1-6 in Order: 1500 points");
        }
        if (checkTriplets()){
            winningDiceOptions.add("Triplets: 2500 points");
        }
        if (checkThreePairs()){
            winningDiceOptions.add("Pairs x3: 1500 points");
        }

        //the array of all the possible options gets returned to be shown to the user
        return winningDiceOptions;
    }

    public void initialCheck(){
        //first make sure that all the checking values are set to zero
        ones = 0;
        twos = 0;
        threes = 0;
        fours = 0;
        fives = 0;
        sixes = 0;

        //goes through the whole dice array and checks how many of each possible option is rolled
        for (Die die : dice) {
            if (!die.getIsPutAside()){
                if (die.getRollResult() == 1) {
                    ones++;
                }
                else if (die.getRollResult() == 2) {
                    twos++;
                }
                else if (die.getRollResult() == 3) {
                    threes++;
                }
                else if (die.getRollResult() == 4) {
                    fours++;
                }
                else if (die.getRollResult() == 5) {
                    fives++;
                }
                else if (die.getRollResult() == 6) {
                    sixes++;
                }
            }

        }
    }

    public boolean checkTriplets(){
        //if any side of the die is rolled 3 times in one go two times it will return true
        int triplets = 0;

        if (ones == 3){
            triplets++;
        }
        if (twos == 3){
            triplets++;
        }
        if (threes == 3){
            triplets++;
        }
        if (fours == 3){
            triplets++;
        }
        if (fives == 3){
            triplets++;
        }
        if (sixes == 3){
            triplets++;
        }

        return triplets == 2;
    }

    public boolean checkThreePairs(){
        int pairs = 0;

        //if any of the die sides are rolled 2 times in one go it will add one to the pairs
        if (ones == 2){
            pairs ++;
        }
        if (twos == 2){
            pairs ++;
        }
        if (threes == 2){
            pairs ++;
        }
        if (fours == 2){
            pairs ++;
        }
        if (fives == 2){
            pairs ++;
        }
        if (sixes == 2){
            pairs ++;
        }

        //if any of the die sides are rolled 4 times in one go it will add two to the pairs
        if (ones == 4){
            pairs += 2;
        }
        if (twos == 4){
            pairs += 2;
        }
        if (threes == 4){
            pairs += 2;
        }
        if (fours == 4){
            pairs += 2;
        }
        if (fives == 4){
            pairs += 2;
        }
        if (sixes == 4){
            pairs += 2;
        }

        // if the pairs are equal to 3 at the end it will return true
        return pairs == 3;
    }

    public boolean checkForOrder(){
        //if there is one of each die side it will return true
        return ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1 && sixes == 1;
    }

    public ArrayList<Integer> checkForSingleOnes(){
        //returns an array of Integers of all ones that are found for the current roll
        ArrayList<Integer> ones = new ArrayList<>();

        for (Die die : dice) {
            if (!die.getIsPutAside()){
                if(die.getRollResult() == 1){
                    ones.add(1);
                }
            }
        }
        return ones;
    }

    public ArrayList<Integer> checkForSingleFives(){
        //returns an array of Integers of all fives that are found for the current roll
        ArrayList<Integer> fives = new ArrayList<>();

        for (Die die : dice) {
            if (!die.getIsPutAside()){
                if(die.getRollResult() == 5){
                    fives.add(5);
                }
            }

        }
        return fives;
    }

    public int checkForThree(){
        //if any of the sides are rolled 3 times it will return which side was rolled three times
        if (ones == 3){
            return 1;
        }
        else if (twos == 3){
            return 2;
        }
        else if (threes == 3){
            return 3;
        }
        else if (fours == 3){
            return 4;
        }
        else if (fives == 3){
            return 5;
        }
        else if (sixes == 3){
            return 6;
        }
        else return 0;
    }

    public int checkForFour(){
        //if any of the sides were rolled four times it will return which side it was
        if (ones == 4){
            return 1;
        }
        else if (twos == 4){
            return 2;
        }
        else if (threes == 4){
            return 3;
        }
        else if (fours == 4){
            return 4;
        }
        else if (fives == 4){
            return 5;
        }
        else if (sixes == 4){
            return 6;
        }
        else return 0;
    }

    public int checkForFive(){
        //if any of the sides were rolled five times it will return which side was rolled five times
        if (ones == 5){
            return 1;
        }
        else if (twos == 5){
            return 2;
        }
        else if (threes == 5){
            return 3;
        }
        else if (fours == 5){
            return 4;
        }
        else if (fives == 5){
            return 5;
        }
        else if (sixes == 5){
            return 6;
        }
        else return 0;
    }

    public int checkForSix(){
        //if any of the sides were rolled 6 times it will return which side it was
        if (ones == 6){
            return 1;
        }
        else if (twos == 6){
            return 2;
        }
        else if (threes == 6){
            return 3;
        }
        else if (fours == 6){
            return 4;
        }
        else if (fives == 6){
            return 5;
        }
        else if (sixes == 6){
            return 6;
        }
        else return 0;
    }

    public void setTurns(){
        //assign the players playing array list to the player turns array list which then gets shuffled to be random
        playerTurns = playersPlaying;
        Collections.shuffle(playerTurns);
        ConsoleIO.printPlayerTurns(playerTurns);
    }

    //is no longer needed as names get out in the Menu class kept it around for just in case
    public void setPlayersNames(){
        //asks the user for the names of all the players playing
        for (int i = 0; i < playersPlaying.size(); i++) {

            String name = ConsoleIO.promptForString("Please enter the name for player #" + (i + 1) + ": ", false);
            playersPlaying.get(i).setPlayerName(name);
        }
    }

    public void getPlayersPlaying(ArrayList<Human> playersPlaying){
        this.playersPlaying = playersPlaying;
    }

    public void setDice(){
        //makes 6 new dices
        for (int i = 0; i < 6; i++) {
            Die die = new Die();
            dice.add(die);
        }
    }
}
