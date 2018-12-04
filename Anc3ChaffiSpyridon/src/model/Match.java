/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Spy
 */
public class Match implements Comparable<Match> {

  

    private final Player player1, player2;
    private final RESULTS results;
    //private static int index;

    public Match(Player p1, Player p2, RESULTS res) {
        player1 = p1;
        player2 = p2;
        results = res;

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public RESULTS getResults() {
        return results;
    }

    @Override
    public int compareTo(Match t) {
        if (    t.getPlayer1().getFirstName().compareTo(this.getPlayer1().getFirstName()) == 0
                && t.getPlayer2().getFirstName().compareTo(this.getPlayer2().getFirstName()) == 0
                || t.getPlayer1().getFirstName().compareTo(this.getPlayer2().getFirstName()) == 0
                && t.getPlayer2().getFirstName().compareTo(this.getPlayer1().getFirstName()) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.player1);
        hash = 89 * hash + Objects.hashCode(this.player2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Match){
            Match m = (Match) obj;
            return m.getPlayer1().getFirstName().equals(this.getPlayer1().getFirstName())
                   && m.getPlayer2().getFirstName().equals(this.getPlayer2().getFirstName())
                   || m.getPlayer1().getFirstName().equals(this.getPlayer2().getFirstName())
                   && m.getPlayer2().getFirstName().equals(this.getPlayer1().getFirstName());
        }return false;
    }

    @Override
    public String toString() {
        return "Match\n" + "\tplayer1: " + player1 + "\n\tplayer2: " + player2 + "\n\tresults: " + results;
    }

}
