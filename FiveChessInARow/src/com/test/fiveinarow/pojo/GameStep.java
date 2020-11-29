package com.test.fiveinarow.pojo;

public class GameStep {
    private int color; //1 for black, 2 for white
    private int row;
    private int col;
    private int round;
    private int gameIndex;
    
    public GameStep() {
    }
    
    public GameStep(int color, int row, int col, int round, int gameIndex) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.round = round;
        this.gameIndex = gameIndex;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public int getCol() {
        return col;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(int round) {
        this.round = round;
    }
    
    public int getGameIndex() {
        return gameIndex;
    }
    
    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }
}
