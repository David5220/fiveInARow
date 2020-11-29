/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.fiveinarow.gui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author 23577
 */
public class ChessBoard extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -7968948036571580611L;
    
    public static final int BOARDSIZE = 15;
    public static final int BOARDSPACE = 40; // every space from the chessBoard
    public static final int MARGINSPACE = BOARDSPACE / 2; // space from chessBoard to the panel edge
    public static int[][] board;
    
    public ChessBoard() {
        board = new int[BOARDSIZE][BOARDSIZE];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(200, 100, 50)); // set orange color
        g.fillRect(0, 0, BOARDSPACE * (BOARDSIZE + 1), BOARDSPACE * (BOARDSIZE + 1)); // fill orange color for the whole panel

        g.setColor(Color.black); // set black color for drawing chess board
        for (int i = 0; i < BOARDSIZE; i++) { // draw colum line
            g.drawLine(MARGINSPACE + BOARDSPACE * i, MARGINSPACE, MARGINSPACE + BOARDSPACE * i, //
                    MARGINSPACE + BOARDSPACE * (BOARDSIZE - 1));
        }
        for (int i = 0; i < BOARDSIZE; i++) { // draw row line
            g.drawLine(MARGINSPACE, MARGINSPACE + BOARDSPACE * i, MARGINSPACE + BOARDSPACE * (BOARDSIZE - 1), MARGINSPACE + BOARDSPACE * i);
        }
        // 画点
        g.fillOval(MARGINSPACE + 3 * BOARDSPACE - 3, MARGINSPACE + 3 * BOARDSPACE - 3, 7, 7);
        g.fillOval(MARGINSPACE + 11 * BOARDSPACE - 3, MARGINSPACE + 3 * BOARDSPACE - 3, 7, 7);
        g.fillOval(MARGINSPACE + 3 * BOARDSPACE - 3, MARGINSPACE + 11 * BOARDSPACE - 3, 7, 7);
        g.fillOval(MARGINSPACE + 7 * BOARDSPACE - 3, MARGINSPACE + 7 * BOARDSPACE - 3, 7, 7);
        g.fillOval(MARGINSPACE + 11 * BOARDSPACE - 3, MARGINSPACE + 11 * BOARDSPACE - 3, 7, 7);


        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j] == 1) { // paint blackChess
                    g.setColor(Color.black);
                    g.fillOval(MARGINSPACE + i * BOARDSPACE - BOARDSPACE / 2, MARGINSPACE + j * BOARDSPACE - BOARDSPACE / 2, BOARDSPACE, BOARDSPACE);
                }
                if (board[i][j] == 2) { // paint whiteChess
                    g.setColor(Color.white);
                    g.fillOval(MARGINSPACE + i * BOARDSPACE - BOARDSPACE / 2, MARGINSPACE + j * BOARDSPACE - BOARDSPACE / 2, BOARDSPACE, BOARDSPACE);
                }

            }
        }
    }
}
