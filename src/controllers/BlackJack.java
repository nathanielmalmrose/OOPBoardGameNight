package controllers;

import models.BJCardDeck;
import models.Human;
import views.ConsoleIO;

public class BlackJack {
    static public String Name = ConsoleIO.promptForString("Input Player 1 Name: ", false);
    static Human Dealer = new Human("DEALER");
    static public boolean gameOver = false;
    static BJCardDeck deck1 = new BJCardDeck();
   static  Human newPlayer = new Human(Name);


    public static void playGamePVCP() {
        deck1.shuffle();
        newPlayer.addCard(deck1.draw());
        newPlayer.addCard(deck1.draw());

        System.out.println(newPlayer.getHandAsString(false));
        Dealer.addCard(deck1.draw());
        Dealer.addCard(deck1.draw());

        System.out.println(Dealer.getHandAsString(true));

        playerTime(newPlayer, deck1);
        //Time for Dealer to play
        DealerTurn(deck1);
        //results after Dealer

    }

    public static void playGamePVP() {
        deck1.shuffle();
        //Gives player1 cards
        newPlayer.addCard(deck1.draw());
        newPlayer.addCard(deck1.draw());

        System.out.println(newPlayer.getHandAsString(false));

        String name = ConsoleIO.promptForString("What is player 2's Name?:\n", false);
        //Player2 starts to exist
        Human Player2 = new Human(name);
        Player2.addCard(deck1.draw());
        Player2.addCard(deck1.draw());

        System.out.println(Dealer.getHandAsString(false));

        playerTime(newPlayer, deck1);
        //Time for Player2 to play
        playerTime(Player2, deck1);
        //results after Dealer
        checkforWinner(newPlayer, Player2);
    }


    public static void playerTime(Human mainPlayer, BJCardDeck deck1) {
        String userInput;
        System.out.println(mainPlayer.getPlayerName() + " Card value=" + mainPlayer.sumOfHand());
        do {

            userInput = ConsoleIO.promptForString("Would you like to hit or stay\n", false);
            if (userInput.equalsIgnoreCase("hit")) {
                mainPlayer.addCard(deck1.draw());
                System.out.println(mainPlayer.getPlayerName() + " drew a card.");
                System.out.println();
                System.out.println(mainPlayer.sumOfHand());

                if (mainPlayer.sumOfHand() > 21) {
                    System.out.println("You busted and got a total of " + mainPlayer.sumOfHand() + ". Dealer wins this time! ");

                    gameOver = true;
                } else if (mainPlayer.sumOfHand() == 21) {
                    System.out.println("BlackJack, YOu got a perfect 21!");
                    gameOver = true;
                }
            } else if (userInput.equalsIgnoreCase("stay")) {
                System.out.println("You have chosen to stay. Your hand: " + mainPlayer.sumOfHand());
                gameOver = true;
            }

        } while (!gameOver);

    }

    public static void DealerTurn(BJCardDeck deck1) {
        System.out.println("Dealer turn!!");
        System.out.println();
        System.out.println(Dealer.getHandAsString(false));
        gameOver = true;

        while (gameOver != false) {
            if (Dealer.sumOfHand() <= 15) {
                // DRAW CARD
                Dealer.addCard(deck1.draw());
                System.out.println(Dealer.getPlayerName() + " drew another card");
                System.out.println();
                System.out.println(Dealer.getHandAsString(false));
                if (Dealer.sumOfHand() > 21) {
                    System.out.println("Dealer busted and got a total of " + Dealer.sumOfHand() + ". "
                            + newPlayer.getPlayerName() + " wins this time!");
                    gameOver = true;
                }

            } else {
                // STAY
                System.out.println("Dealer has chosen to stay!");
                System.out.println();
                int totalDealerSum = Dealer.sumOfHand();
                int totalPlayerSum1 = newPlayer.sumOfHand();
                if (totalDealerSum > totalPlayerSum1) {
                    System.out.println("Both players has decided to stay. The winner is " + Dealer.getPlayerName()
                            + " with a total of " + totalDealerSum + ".");
                } else {
                    System.out.println("Both players has decided to stay. The winner is " + newPlayer.getPlayerName()
                            + " with a total of " + totalPlayerSum1 + ".");
                }
                gameOver = false;
            }

        }
    }

    public static  void checkforWinner(Human player1, Human player2) {

        int totalPlayer1Sum = player1.sumOfHand();
        int totalPlayer2Sum = player2.sumOfHand();
        if (totalPlayer1Sum > totalPlayer2Sum) {
            System.out.println("Both players has decided to stay. The winner is " + player1.getPlayerName()
                    + " with a total of " + totalPlayer1Sum + ".");
        } else {
            System.out.println("Both players has decided to stay. The winner is " + player2.getPlayerName()
                    + " with a total of " + totalPlayer2Sum + ".");
        }
        gameOver = false;
    }

}


