package models;

import java.util.ArrayList;

public class LeaderBoard {
    private ArrayList<Player> leaderBoardList = new ArrayList<>();

    public ArrayList<Player> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(ArrayList<Player> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    public String[] leaderBoardToArray() {
        String[] leaderboard = new String[leaderBoardList.size()];
        for (int i = 0; i < leaderBoardList.size(); i++) {
            leaderboard[i] = leaderBoardList.get(i).getPlayerName();
        }
        return leaderboard;
    }

    public void addToLeaderboard(Player player){
        leaderBoardList.add(player);
    }
}
