package com.test.fiveinarow;

import java.util.List;

import com.test.fiveinarow.db.GameStepManager;
import com.test.fiveinarow.db.PlayerScoreManager;
import com.test.fiveinarow.gui.ChessBoard;
import com.test.fiveinarow.pojo.GameStep;
import com.test.fiveinarow.pojo.PlayerScore;

public class GameController {
    
    private PlayerScoreManager playerScoreManager;
    private GameStepManager gameStepManager;
    
    public ChessBoard chessBoard;
    
    private int currentRow;
    private int currentCol;
    private int round;
    private int gameIndex;
    
    public GameController(ChessBoard chessBoard) {
        playerScoreManager = new PlayerScoreManager();
        gameStepManager = new GameStepManager();
        this.chessBoard = chessBoard;
        restartGame();
    }
    
    public GameController() {
       playerScoreManager = new PlayerScoreManager();
        gameStepManager = new GameStepManager();
    }
    
    public boolean recordPlayers(String player1, String player2) {
        if (null == player1 || null == player2 || player1.equals("") || player2.equals("")) return false;
        
        if (!playerScoreManager.checkPlayerExists(player1)) {
            playerScoreManager.createNewPlayer(player1);
        }
        
        if (!playerScoreManager.checkPlayerExists(player2)) {
            playerScoreManager.createNewPlayer(player2);
        }
        
        return true;   /////////
    }
    
    public String getPlayerScoresLabel(String playerName1, String playerName2) {
        PlayerScore playerScore1 = playerScoreManager.getPlayerScore(playerName1);
        PlayerScore playerScore2 = playerScoreManager.getPlayerScore(playerName2);
        
        return playerScore1.getPlayerName() + "(black, score:" + playerScore1.getPlayerScore() + ")    VS    " +  
            playerScore2.getPlayerName() + "(white, score:" + playerScore2.getPlayerScore() + ")";
    }
    
    public void addScoreForWinner(String winnerName) {
        PlayerScore original = playerScoreManager.getPlayerScore(winnerName);    //如果玩家分数空的话 跳过
        if (null == original) return;
        
        playerScoreManager.updateScore(winnerName, original.getPlayerScore() + 1); //如果不是， 跟新玩家得分
    }
    
    public boolean recordOneStep(int color) {
        GameStep gameStep = new GameStep(color, this.currentRow, this.currentCol, this.round, this.gameIndex);
        return gameStepManager.recordStep(gameStep);
    }
    
    public void replayLastGame() {
        clearChess();
        List<GameStep> steps = gameStepManager.getAllGameSteps(gameIndex - 1);
        for (GameStep step : steps) {
            ChessBoard.board[step.getRow()][step.getCol()] = step.getColor();
        }
        
        chessBoard.repaint();
    }
    
    public void withdrawLastStep() {
        if (round == 0) return;
        GameStep lastStep = gameStepManager.getLastStep(this.gameIndex);
        if (null != lastStep) {
            ChessBoard.board[lastStep.getRow()][lastStep.getCol()] = 0;
            chessBoard.repaint();
        }
        gameStepManager.deleteStep(this.gameIndex, lastStep.getRound());
        this.round--;
    }
    
    public String[] getPlayerScoreRankList() {   ///。。。。。。。。。。。。。。
        List<PlayerScore> playerScores = playerScoreManager.getAllPlayerScore();
        String[] playerScoreStrs = new String[playerScores.size()];     //。。。。。。。。。。。
        for (int i = 0; i < playerScores.size(); i++) {
            playerScoreStrs[i] = playerScores.get(i).getPlayerName() + "     Score: " + playerScores.get(i).getPlayerScore();
        }
        return playerScoreStrs;
    }
    
    public boolean checkWin() {
        boolean flag = false;
        int count = 1;
        int color = ChessBoard.board[currentRow][currentCol];
        count = this.checkCount(1, 0, color);//check horizontal
        if (count >= 5) {
            flag = true;
        } else {
            //check Vertical
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
                //check upper right and lower left  
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                    //check lower right and upper left 
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }

    public int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (currentRow + xChange >= 0 && currentRow + xChange <= (ChessBoard.BOARDSIZE - 1)
                && currentCol + yChange >= 0 && currentCol + yChange <= (ChessBoard.BOARDSIZE - 1)
                && color == ChessBoard.board[currentRow + xChange][currentCol + yChange]) 
        {  //Going up and going right  
            count++;
            if (xChange != 0) {
                xChange++;
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (currentRow - xChange >= 0 && currentRow - xChange <= (ChessBoard.BOARDSIZE - 1)
                && currentCol - yChange >= 0 && currentCol - yChange <= (ChessBoard.BOARDSIZE - 1)
                && color == ChessBoard.board[currentRow - xChange][currentCol - yChange]) // Going down  and  left
        {
            count++;
            if (xChange != 0) {
                xChange++;
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        return count;
    }

    public void clearChess() {
        for (int i = 0; i < ChessBoard.BOARDSIZE; i++) {
            for (int j = 0; j < ChessBoard.BOARDSIZE; j++) {
                ChessBoard.board[i][j] = 0;
            }
        }
    }
    
    public void restartGame() {
        gameStepManager.createTable();
        playerScoreManager.createTable();
        clearChess();
        this.gameIndex = gameStepManager.getMaxGameIndex() + 1;
        this.round = 0;
    }
    
    public int getCurrentRow() {
        return currentRow;
    }
    
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
    
    public int getCurrentCol() {
        return currentCol;
    }
    
    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }
    
    public int getRound() {
        return round;
    }
    
    public void nextRound() {
        this.round++;
    }
}
