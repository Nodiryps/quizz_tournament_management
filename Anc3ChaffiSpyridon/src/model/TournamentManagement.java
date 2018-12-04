/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Spy
 */
public class TournamentManagement {
    List<Tournament> tournamentList = new ArrayList<>();
    
    public TournamentManagement(Tournament t){
        tournamentList.add(t);   
    }
    
    public List<Tournament> getTournamentList(){
        return tournamentList;
    }
}
