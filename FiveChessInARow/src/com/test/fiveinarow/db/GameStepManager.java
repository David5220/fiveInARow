package com.test.fiveinarow.db;

import java.util.Arrays;
import java.util.List;

import com.test.fiveinarow.pojo.GameStep;

public class GameStepManager {
    
    public List<GameStep> getAllGameSteps(int gameIndex) {
        String sql = "SELECT * FROM game_step WHERE gameIndex = ? ORDER BY gameIndex, round ASC";/////
        return DBHelper.executeQuery(sql, Arrays.asList(gameIndex), GameStep.class);      /////////
    }
    
    public boolean recordStep(GameStep gameStep) {
        String sql = "INSERT INTO game_step (color, row, col, round, gameIndex) VALUES (?, ?, ?, ?, ?)";
        return DBHelper.excuteUpdate(sql, Arrays.asList(gameStep.getColor(), gameStep.getRow(), gameStep.getCol(), 
                gameStep.getRound(), gameStep.getGameIndex()));
    }
    
    public boolean deleteAllSteps() {
        String sql = "DELETE FROM game_step";
        return DBHelper.excuteUpdate(sql, null);
    }
    
    public GameStep getLastStep(int gameIndex) {
        int lastRound = getMaxRound(gameIndex);
        String sql = "SELECT * FROM game_step WHERE gameIndex = ? AND round = ?";
        List<GameStep> lastSteps = DBHelper.executeQuery(sql, Arrays.asList(gameIndex, lastRound), GameStep.class);////
        if (lastSteps.size() > 0) {
            return lastSteps.get(0);
        }
        
        return null;
    }
    
    public boolean deleteStep(int gameIndex, int round) {
        String sql = "DELETE FROM game_step WHERE gameIndex = ? AND round = ?";
        return DBHelper.excuteUpdate(sql, Arrays.asList(gameIndex, round));
    }
    
    public int getMaxRound(int gameIndex) {
        String sql = "SELECT MAX(round) round FROM game_step WHERE gameIndex = ?";
        return DBHelper.executeQuery(sql, Arrays.asList(gameIndex), GameStep.class).get(0).getRound();
    }
    
    public int getMaxGameIndex() {
        String sql = "SELECT MAX(gameIndex) gameIndex FROM game_step";
        return DBHelper.executeQuery(sql, null, GameStep.class).get(0).getGameIndex();
    }
    
    public void createTable() {
        String createGameTableSql = "CREATE TABLE game_step (color INT NOT NULL, row INT NOT NULL, col INT NOT NULL, "
                + "round INT NOT NULL, gameIndex INT NOT NULL, PRIMARY KEY (row, col, gameIndex))";
        DBHelper.createTable(createGameTableSql);
    }
    
}
