package controllers;

import models.Computer;
import models.Human;
import models.LeaderBoard;
import models.Player;
import views.ConsoleIO;

public class Menu {
    private Checkers checkers;
    private Farkle farkle = new Farkle();
    private BlackJack blackJack = new BlackJack();
    private LeaderBoard leaderBoard = new LeaderBoard();
    private Player playerOne;
    private Player playerTwo;

    public void menuMain() {
        int playerChoice;

        do {
            playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Choose a Game", "Leaderboard"}, true );
            switch (playerChoice) {
                case 1:
                    menuChooseGame();
                    break;
                case 2:
                    leaderBoard();
                    break;
            }

        } while (playerChoice > 0);
    }

    public void menuChooseGame() {
        int playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Checkers", "Farkle", "Blackjack"}, true);
            switch (playerChoice) {
                case 1:
                    menuSetPlayers();
                    checkers = new Checkers(playerOne, playerTwo);
                    break;
                case 2:
                    //menuSetPlayers();
                    //starts the Farkle game
                    Farkle play = new Farkle();
                    play.startFarkle();
                    break;
                case 3:
                    menuSetPlayers();
                    blackJack.playGame();
                    break;
            }
    }

    public void menuSetPlayers() {
        int playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Player vs Player", "Player vs Computer"}, true);
        switch (playerChoice) {
            case 1:
                playerOne = menuSelectPlayer();
                playerOne.setPlayerNum(1);
                playerTwo = menuSelectPlayer();
                playerTwo.setPlayerNum(2);
                break;
            case 2:
                playerOne = menuSelectPlayer();
                playerOne.setPlayerNum(1);
                playerTwo = new Computer();
                playerTwo.setPlayerNum(2);
                break;
        }
    }

    public Player menuSelectPlayer() {
        int playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Add new Player", "Use existing player"}, true);
        Player player = null;
        switch (playerChoice) {
            case 1:
                player = newPlayer();
                break;
            case 2:
                player = menuPlayerFromLeaderboard();
                break;
        }
        return player;
    }

    public Player menuPlayerFromLeaderboard() {
        int playerChoice = ConsoleIO.promptForMenuSelection(leaderBoard.leaderBoardToArray(), false);
        return leaderBoard.getLeaderBoardList().get(playerChoice - 1);
    }

    public void leaderBoard() {
        leaderBoard.showLeaderboard();
    }

    public Player newPlayer() {
        int playerChoice;
        Player player = null;
        String newPlayer;

        do {
            playerChoice = 0;
            newPlayer = ConsoleIO.promptForString("Enter Player's Name: ", false);
            for (Player p : leaderBoard.getLeaderBoardList()) {
                if (p.getPlayerName().equalsIgnoreCase(newPlayer)) {
                    System.out.println("User " + p.getPlayerName() + " already exists.");
                    playerChoice = ConsoleIO.promptForMenuSelection(new String[]{"Use existing player", "Enter new player"}, true);
                    switch (playerChoice) {
                        case 1:
                            player = p;
                            break;
                        case 2:

                            break;
                    }
                    break;
                }
            }
        } while (playerChoice == 2);

        if (player == null) {
            player = new Human(newPlayer);
            leaderBoard.getLeaderBoardList().add(player);
        }

        return player;
    }

}
