package controllers;

import models.BJCardDeck;
import models.Human;
import models.Player;
import views.ConsoleIO;

public class BlackJack {
    Human Dealer = new Human("DEALER");
    public boolean gameOver = false;
    boolean playerNotLost;
    boolean wishToContinue;

    public void playGamePVCP(Player player1) {
        BJCardDeck playerDeck = new BJCardDeck();
        BJCardDeck deck2 = new BJCardDeck();
        do {
            playerDeck.shuffle();
            playerDeck.shuffle();
            player1.addCard(playerDeck.draw(playerDeck));
            player1.addCard(playerDeck.draw(playerDeck));

            System.out.println(player1.getHandAsString(false));
            deck2.shuffle();
            deck2.shuffle();
            Dealer.addCard(deck2.draw(deck2));
            Dealer.addCard(deck2.draw(deck2));
            System.out.println(Dealer.getHandAsString(true));

            playerTime(player1, playerDeck);
            //Time for Dealer to play
            if (playerNotLost != true) {
                DealerTurn(deck2, player1);
            }
            //results after Dealer

            wishToContinue = ConsoleIO.promptForBoolean("Would you like to continue playing BlackJack: ", "yes", "no");
            player1.MovetoDeck(playerDeck);
            Dealer.MovetoDeck(deck2);
        } while (wishToContinue);
    }

    public void playGamePVP(Player player1, Player player2) {
        BJCardDeck deck1 = new BJCardDeck();
        BJCardDeck deck2 = new BJCardDeck();
        do {
            //Gives player1 cards
            deck1.shuffle();
            deck1.shuffle();
            player1.addCard(deck1.draw(deck1));
            player1.addCard(deck1.draw(deck1));

            System.out.println(player1.getHandAsString(false));

            //Player2 starts to exist
            deck2.shuffle();
            deck2.shuffle();
            player2.addCard(deck2.draw(deck2));
            player2.addCard(deck2.draw(deck2));

            System.out.println(player2.getHandAsString(false));

            playerTime(player1, deck1);

            //Time for Player2 to play
            if (playerNotLost != true) {
                playerTime(player2, deck2);
            System.out.println(checkForWinner(player1, player2));
            }
            //results after


            wishToContinue = ConsoleIO.promptForBoolean("Would you like to continue (yes, no): ", "yes", "no");
            player1.MovetoDeck(deck1);
            player2.MovetoDeck(deck2);


        } while (wishToContinue);
    }

    public void playerTime(Player mainPlayer, BJCardDeck deck1) {
        String userInput;
        System.out.println(mainPlayer.getPlayerName() + " Card value=" + mainPlayer.sumOfHand());
        do {

            userInput = ConsoleIO.promptForString("Would you like to hit or stay\n", false);
            if (userInput.equalsIgnoreCase("hit")) {
                mainPlayer.addCard(deck1.draw(deck1));
                System.out.println(mainPlayer.getPlayerName() + " drew a card.");
                System.out.println();
                System.out.println(mainPlayer.sumOfHand() + " :" + mainPlayer.getPlayerName());
                gameOver = false;
                if (mainPlayer.sumOfHand() > 21) {
                    System.out.println("You busted and got a total of " + mainPlayer.sumOfHand() + ". Other player wins this time! ");
                    playerNotLost = true;
                    gameOver = true;
                } else if (mainPlayer.sumOfHand() == 21) {
                    System.out.println("BlackJack, You got a perfect 21!");
                    playerNotLost = true;
                    gameOver = true;
                    mainPlayer.addPlayersWin(1);
                } else {
                    gameOver = false;
                }
            } else if (userInput.equalsIgnoreCase("stay")) {

                System.out.println("You have chosen to stay. Your hand: " + mainPlayer.sumOfHand());
                playerNotLost = false;
                gameOver = true;
            }else{
                gameOver=false;
            }

        } while (gameOver != true);

    }

    public String checkForWinner(Player player1, Player player2) {
        int totalPlayer1Sum = player1.sumOfHand();
        String winnerPhrase = "";
        int totalPlayer2Sum = player2.sumOfHand();
        if (totalPlayer1Sum <= 21&& totalPlayer2Sum<=21) {
            if (totalPlayer1Sum > totalPlayer2Sum) {
                winnerPhrase=("Both players have decided to stay. The winner is " + player1.getPlayerName()
                        + " with a total of " + totalPlayer1Sum + ".");

                player1.addPlayersWin(1);

            } else if (totalPlayer2Sum > totalPlayer1Sum) {
                winnerPhrase=("Both players have decided to stay. The winner is " + player2.getPlayerName()
                        + " with a total of " + totalPlayer2Sum + ".");

                player2.addPlayersWin(1);

            } else
                gameOver = false;
        }else if (totalPlayer1Sum >21){
            winnerPhrase=("Player 1 went over, The winner is " + player2.getPlayerName()
                    + " with a total of " + totalPlayer2Sum + ".");
            player2.addPlayersWin(1);

        }else if (totalPlayer2Sum > 21){
            winnerPhrase=("Player 2 went over, The winner is " + player1.getPlayerName()
                    + " with a total of " + totalPlayer1Sum + ".");

            player1.addPlayersWin(1);
        }else{
            winnerPhrase = "how did you get here";
        }
        return winnerPhrase;
    }

    public void DealerTurn(BJCardDeck deck1, Player thisPlayer) {
        System.out.println("Dealer turn!!");
        System.out.println();
        System.out.println(Dealer.getHandAsString(false));
        gameOver = true;

        while (gameOver != false) {
            if (Dealer.sumOfHand() <= 15) {
                // DRAW CARD
                Dealer.addCard(deck1.draw(deck1));
                System.out.println(Dealer.getPlayerName() + " drew another card");
                System.out.println();
                System.out.println(Dealer.getHandAsString(false));


            } else {

                System.out.println("Dealer has chosen to stay!");
                System.out.println();
                //checkWinnerfor cpu
                int totalDealerSum = Dealer.sumOfHand();
                int totalPlayerSum1 = thisPlayer.sumOfHand();

                if (totalDealerSum > totalPlayerSum1 && totalDealerSum <= 21) {
                    System.out.println("Both players have decided to stay. The winner is " + Dealer.getPlayerName()
                            + " with a total of " + totalDealerSum + ".");
                } else if (totalPlayerSum1 < totalDealerSum && totalDealerSum > 21 || totalPlayerSum1 > totalDealerSum) {
                    System.out.println("Both players have decided to stay. The winner is " + thisPlayer.getPlayerName()
                            + " with a total of " + totalPlayerSum1 + ".");
                    thisPlayer.addPlayersWin(1);
                } else {
                    System.out.println("The dealer went over 21 " + thisPlayer.getPlayerName() + "is the winner with "
                            + totalPlayerSum1);
                    thisPlayer.addPlayersWin(1);
                }
                gameOver = false;
            }

        }
    }

}


