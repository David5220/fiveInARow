/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.fiveinarow.gui;

import static com.test.fiveinarow.gui.ChessBoard.BOARDSPACE;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.test.fiveinarow.GameController;
import com.test.fiveinarow.pojo.Players;
import javax.swing.JScrollPane;

/**
 *
 * @author 23577
 */
public class GameGUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    /**
     *
     */
    private static final long serialVersionUID = -5342674973167807585L;

    private ChessBoard chessBoard;
    private Players players;
    private JMenuBar menuBar;

    private JMenu gameMenu;
    private JMenu playerMenu;

    private JMenuItem restart;
    private JMenuItem exit;
    private JMenuItem replay;
    private JMenuItem withdrawStep;
    private JMenuItem playerScoreRank;
    private JMenuItem closeScoreRank;

    private JLabel scoreLabel;
    private JLabel currentPlayer;

    private JList<String> playerScoreList;
    private GameController gameController;
    private JScrollPane scroll;
    public GameGUI() {

        chessBoard = new ChessBoard();
        chessBoard.setPreferredSize(new Dimension(BOARDSPACE * (ChessBoard.BOARDSIZE - 1) + ChessBoard.MARGINSPACE
                * 2, BOARDSPACE * (ChessBoard.BOARDSIZE - 1) + ChessBoard.MARGINSPACE * 2));
        chessBoard.addMouseListener(new MouseHandler());

        this.setResizable(false);   /////////////
        
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        playerMenu = new JMenu("Player");

        restart = new JMenuItem("Restart");
        exit = new JMenuItem("Exit");
        replay = new JMenuItem("Replay Last Game");
        withdrawStep = new JMenuItem("Withdraw Last Step");
        playerScoreRank = new JMenuItem("Score Rank");
        closeScoreRank = new JMenuItem("Close score rank");

        playerScoreList = new JList<>();
        playerScoreList.setBorder(BorderFactory.createTitledBorder("Player Score Ranking"));
        chessBoard.add(playerScoreList);   /////////////
        scroll = new JScrollPane(playerScoreList);     
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        scoreLabel = new JLabel("");
        chessBoard.add(scoreLabel);  ///////////

        currentPlayer = new JLabel("");
        chessBoard.add(currentPlayer);

        gameMenu.add(restart);
        gameMenu.add(exit);
        gameMenu.add(replay);
        gameMenu.add(withdrawStep);

        playerMenu.add(playerScoreRank);
        playerMenu.add(closeScoreRank);
        
        restart.addActionListener(this);
        exit.addActionListener(this);
        replay.addActionListener(this);
        withdrawStep.addActionListener(this);

        playerScoreRank.addActionListener(this);
        closeScoreRank.addActionListener(this);
        
        menuBar.add(gameMenu);
        menuBar.add(playerMenu);
        setJMenuBar(menuBar);

        this.add(chessBoard);    ///////////

        gameController = new GameController(chessBoard);  ////////
        setGameLabels();
    }

    private void setGameLabels() {
        String player1 = JOptionPane.showInputDialog("Please input name of player 1:black");
        String player2 = JOptionPane.showInputDialog("Please input name of player 2:white");
        if (!gameController.recordPlayers(player1, player2)) {
            System.err.println("Invaild Player Name!");       ////
            System.exit(0);
        }

        players = new Players(player1, player2);
        scoreLabel.setText(gameController.getPlayerScoresLabel(player1, player2));
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == restart) {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to restart game", "Restart",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gameController.restartGame();
                chessBoard.repaint();

            }
        } else if (source == exit) {
            if (JOptionPane.showConfirmDialog(null, "Would you like to leave for the game ", "Exit",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (source == replay) {
            if (JOptionPane.showConfirmDialog(null, "Would you like to replay last game", "Replay",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gameController.replayLastGame();
            }
        } else if (source == withdrawStep) {
            if (JOptionPane.showConfirmDialog(null, "Would you like to withdraw last game", "Withdraw",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gameController.withdrawLastStep();
            }
        } else if (source == playerScoreRank) {
            if (JOptionPane.showConfirmDialog(null, "Would you like to open the score rank", "playerScoreRank",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                playerScoreList.setListData(gameController.getPlayerScoreRankList());   //Check for JList API to setListData
                chessBoard.add(scroll);
                chessBoard.revalidate();  ////??
                chessBoard.repaint();

            }
        }else if (source == closeScoreRank) {
            if (JOptionPane.showConfirmDialog(null, "Would you like to close the score rank", "closeScoreRank",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                
                chessBoard.remove(scroll);
                chessBoard.revalidate();
                chessBoard.repaint();

            }
        }
    }

    private class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            gameController.setCurrentRow((int) (e.getX() / ChessBoard.BOARDSPACE));
            gameController.setCurrentCol((int) (e.getY() / ChessBoard.BOARDSPACE));
            if (ChessBoard.board[gameController.getCurrentRow()][gameController.getCurrentCol()] == 0) {
                int color = gameController.getRound() % 2 == 0 ? 1 : 2;
                ChessBoard.board[gameController.getCurrentRow()][gameController.getCurrentCol()] = color;  /////////
                chessBoard.repaint();// place chess to board

                gameController.recordOneStep(color);

                if (gameController.checkWin()) {                                                   // if any player win
                    players.winner = gameController.getRound() % 2 == 0 ? players.blackPlayerName : players.whitePlayerName;
                    gameController.addScoreForWinner(players.winner);

                    if (gameController.getRound() % 2 == 0) {
                        players.winner = players.blackPlayerName;
                        JOptionPane.showMessageDialog(null, players.winner + " win !  Your current score will + 1", "Game over",
                                JOptionPane.PLAIN_MESSAGE);                             // display name and socres for the black winner.
                    } else if (gameController.getRound() % 2 == 1) {
                        players.winner = players.whitePlayerName;
                        JOptionPane.showMessageDialog(null, players.winner + " win !  Your current score will + 1", "Game over",
                                JOptionPane.PLAIN_MESSAGE);                                     // display name and socres for the white winner.
                    }

//                    playerScoreList.setListData(gameController.getPlayerScoreRankList());

                    gameController.restartGame();
                    chessBoard.repaint();                                           // update chessBoard 
//                    setGameLabels();
                }
                gameController.nextRound();

            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

}
