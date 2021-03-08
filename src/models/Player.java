package models;

public abstract class Player {

        private int playerWinCount = 0;
        private int playerNum;
        private String playerName;

    public Player() {
    }

    public Player(String playerName, int playerNum) {
        setPlayerName(playerName);
        setPlayerNum(playerNum);
    }

    public Player(String playerName) {
        setPlayerName(playerName);
    }

    public void addPlayersWin(int numberOfWins){
        this.playerWinCount += numberOfWins;
    }

    public int getPlayerWinCount(){
        return playerWinCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    @Override
    public String toString() {
        return getPlayerName();
    }
}
