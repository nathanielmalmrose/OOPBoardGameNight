package controllers;

import models.Computer;
import models.Human;
import models.LeaderBoard;
import models.Player;
import views.ConsoleIO;

import java.util.ArrayList;

public class Menu {
    private Checkers checkers;
    private final BlackJack blackJack = new BlackJack();
    private LeaderBoard leaderBoard = new LeaderBoard();
    private Player playerOne;
    private Player playerTwo;


    public void menuMain() {
        leaderBoard.setLeaderBoardList(FileIO.loadList());
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
        FileIO.saveList(leaderBoard.getLeaderBoardList());
    }

    public void menuChooseGame() {
        int playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Checkers", "Farkle", "Blackjack"}, true);
            switch (playerChoice) {
                case 1:
                    menuSetPlayers();
                    checkers = new Checkers(playerOne, playerTwo);
                    checkers.startGame();
                    checkers.play();
                    break;
                case 2:
                    Farkle play = new Farkle();
                    play.startFarkle(setFarklePlayers());
                    break;
                case 3:
                    menuSetPlayersBJ();
                    break;
            }
    }

    public ArrayList<Human> setFarklePlayers(){
        ArrayList<Human> playersPlaying = new ArrayList<>();
        int players = ConsoleIO.promptForInt("\nHow many players are going to play? ", 2, 10);
        for (int i = 0; i < players; i++) {
            ConsoleIO.printText("\nChoose an option regarding player: #" + (i + 1));
            Human player = (Human)menuSelectPlayer();
            playersPlaying.add(player);
        }
        return playersPlaying;
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
        ConsoleIO.showLeaderboard(leaderBoard.getLeaderBoardList());
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
                    ConsoleIO.printText("User " + p.getPlayerName() + " already exists.");
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

    public void menuSetPlayersBJ() {
        int playerChoice = ConsoleIO.promptForMenuSelection(new String[] {"Player vs Player", "Player vs Computer"}, true);
        switch (playerChoice) {
            case 1:
                System.out.println("Player1 turn to choose: ");
                playerOne = menuSelectPlayer();
                playerOne.setPlayerNum(1);
                System.out.println("Player2 turn to choose: ");
                playerTwo = menuSelectPlayer();
                playerTwo.setPlayerNum(2);
                blackJack.playGamePVP(playerOne,playerTwo);
                break;
            case 2:
                playerOne = menuSelectPlayer();
                playerOne.setPlayerNum(1);
                blackJack.playGamePVCP(playerOne);
                break;
        }
    }
}
