/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public Controller() {
       this.facade=addTournois(name);
    }
    
    
 
    
    public TournamentFacade addTournois(String name){
   
          return new TournamentFacade(name);
    }
    
    public static void main(String[] args) {
        new Controller();
    }
 
}
