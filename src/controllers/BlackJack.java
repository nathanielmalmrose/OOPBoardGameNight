package controllers;

import models.BJCardDeck;
import models.Human;
import models.Player;
import views.ConsoleIO;

public class BlackJack {
    Human Dealer = new Human("DEALER");
    public boolean gameOver = false;
    BJCardDeck deck1 = new BJCardDeck();
    boolean playerNotLost;


    public void playGamePVCP(Player player1) {
        deck1.shuffle();
        player1.addCard(deck1.draw());
        player1.addCard(deck1.draw());

        System.out.println(player1.getHandAsString(false));
        Dealer.addCard(deck1.draw());
        Dealer.addCard(deck1.draw());

        System.out.println(Dealer.getHandAsString(true));

        playerTime(player1, deck1);
        //Time for Dealer to play
        if (playerNotLost!=true){
        DealerTurn(deck1, player1);}
        //results after Dealer

    }

    public void playGamePVP(Player player1, Player Player2) {
        deck1.shuffle();
        //Gives player1 cards
        player1.addCard(deck1.draw());
        player1.addCard(deck1.draw());

        System.out.println(player1.getHandAsString(false));

        //Player2 starts to exist
        Player2.addCard(deck1.draw());
        Player2.addCard(deck1.draw());

        System.out.println(Player2.getHandAsString(false));

        playerTime(player1, deck1);
        //Time for Player2 to play
        if (playerNotLost){
        playerTime(Player2, deck1);}
        //results after Dealer
        checkForWinner(player1, Player2);
    }


    public void playerTime(Player mainPlayer, BJCardDeck deck1) {
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
                    playerNotLost = true;
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

    public void DealerTurn(BJCardDeck deck1,Player thisPlayer) {
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
                            + thisPlayer.getPlayerName() + " wins this time!");
                    gameOver = true;
                }

            } else {
                // STAY
                System.out.println("Dealer has chosen to stay!");
                System.out.println();
                int totalDealerSum = Dealer.sumOfHand();
                int totalPlayerSum1 = thisPlayer.sumOfHand();
                if (totalDealerSum > totalPlayerSum1) {
                    System.out.println("Both players has decided to stay. The winner is " + Dealer.getPlayerName()
                            + " with a total of " + totalDealerSum + ".");
                } else {
                    System.out.println("Both players has decided to stay. The winner is " + thisPlayer.getPlayerName()
                            + " with a total of " + totalPlayerSum1 + ".");
                }
                gameOver = false;
            }

        }
    }

    public  void checkForWinner(Player player1, Player  player2) {

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


