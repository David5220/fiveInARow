package com.test.fiveinarow.db;

import java.util.Arrays;
import java.util.List;

import com.test.fiveinarow.pojo.PlayerScore;

public class PlayerScoreManager {
    
    public PlayerScore getPlayerScore(String playerName) {   //////////
        String sql = "SELECT * FROM player_score WHERE playerName = ?";
        List<PlayerScore> playerScores = DBHelper.executeQuery(sql, Arrays.asList(playerName), PlayerScore.class);/////////
        
        return playerScores.size() > 0 ? playerScores.get(0) : null;   ////
    }
    
    public boolean checkPlayerExists(String playerName) {
        String sql = "SELECT count(1) totalCount from player_score WHERE playerName = ?";
        int result = DBHelper.getRecordCount(sql, Arrays.asList(playerName));
        return result > 0;   //////。。。。。
    }
    
    public boolean createNewPlayer(String playerName) {
        String sql = "INSERT INTO player_score (playerName, playerScore) VALUES (?, ?)";  ///////
        return DBHelper.excuteUpdate(sql, Arrays.asList(playerName, 0));
    }
    
    public void updateScore(String playerName, int score) {
        String sql = "UPDATE player_score SET playerScore = ? WHERE playerName = ?";
        DBHelper.excuteUpdate(sql, Arrays.asList(score, playerName));
    }
    
    public List<PlayerScore> getAllPlayerScore() {  ///////////。。。。。。。
        String sql = "SELECT * FROM player_score ORDER BY playerScore DESC";
        return DBHelper.executeQuery(sql, null, PlayerScore.class);   ///////////。。。。
    }
    
    public void createTable() {
        String createPlayerTableSql = "CREATE TABLE player_score (id INT NOT NULL generated always as identity, playerName " 
                + "VARCHAR(45) NOT NULL, playerScore INT NOT NULL DEFAULT 0, PRIMARY KEY (id))";
        DBHelper.createTable(createPlayerTableSql);
    }
}
