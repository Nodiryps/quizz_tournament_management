/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import model.Tournament;
import model.TournamentFacade;
import model.RESULTS;

/**
 *
 * @author Spy
 */
public final class Controller {

    private String name;
    TournamentFacade facade;

    public Controller(TournamentFacade facade) {
        this.facade = facade;
    }

    public TournamentFacade getFacade() {
        return facade;
    }
    public Tournament getTournament(){
        return facade.getTournois();
    }

    public void createMatch(Player p1, Player p2, RESULTS res) {
        facade.createNewMatch(p1, p2, res);
    }

    public void setIndex(int index) {
        facade.setIndex(index);
    }

    public void DelMatch(Match m) {
        facade.removeMatch();
    }

    public void setMatchSelected(Match m,int index) {
        System.out.println("controller"+m);
        facade.setSelectedMatch(m,index);

    }

    public Match getSelectedMatch() {
        return facade.getSelectedMatch();
    }
    public Set<Match> getAllMAtch(){
       return  facade.getTournois().getMatchList();
    }
}
