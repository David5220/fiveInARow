package com.test.fiveinarow;

import javax.swing.JFrame;

import com.test.fiveinarow.gui.GameGUI;

public class GameStart {
    public static void main(String[] args) {
        JFrame frame = new GameGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
