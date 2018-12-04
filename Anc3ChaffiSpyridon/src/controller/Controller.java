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
      this.facade=facade;
    }
    
    
   
 
 
     
   
 
    
    public void setIndex(int index){
       facade.setIndex(index);
    }
}
