/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Spy
 */
public class Tournament {

    private final String name;
    private  List<Player> subscribersList = new ArrayList<>();// modifier en hashSet peut etre.
    private Set<Match> matchList = new TreeSet<>();
    

   
   
   private List<Player> testAdvert=new ArrayList<>();
    public Tournament(String s) {
        name = s;
    }

   
    public String getName() {
        return name;
    }

    
     public List<Player> getSubscribersList() {
        return subscribersList;
    }

    public Set<Match> getMatchList() {
        return matchList;
    }

    
    public boolean existMatch(Match t) {
        return matchList.contains(t);
    }

    public void addMatch(Match m) {
        this.matchList.add(m);
    }

    public void addPlayer(Player p) {
        this.subscribersList.add(p);
       
    }

  
    @Override
    public String toString() {
        return getName();
    }


}
