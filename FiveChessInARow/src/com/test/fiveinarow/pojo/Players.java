/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.fiveinarow.pojo;

/**
 *
 * @author 23577
 */
public class Players {
    public String blackPlayerName = null;
    public String whitePlayerName = null;
    public String winner = null;

    public Players(String black, String white) {
        this.blackPlayerName = black;
        this.whitePlayerName = white;

    }
}
