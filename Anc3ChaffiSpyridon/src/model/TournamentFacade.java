/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Spy
 */
public class TournamentFacade extends Observable {
    
    public enum TypeNotif {
        INIT, TOURNAMENT_SELECTED, LINE_UNSELECTED, LINE_UPDATED, LINE_ADDED
    }

    public List<Tournament> tournamentList = new ArrayList<>();
    
    
    Tournament t1=new Tournament("E-Sport");
    Tournament t2=new Tournament("T2");
    public int index=0;

    Player p1 = new Player("Philippe");
    Player p2 = new Player("Khadija");
    Player p3 = new Player("spyridon");
    Player p4 = new Player("chaffi");
    Player p5 = new Player("lindsay");
    Player p6 = new Player("rodolphe");
    Match m = new Match(p1, p2, RESULTS.DRAW);
    Match m2 = new Match(p2, p1, RESULTS.DRAW);
    Match m3 = new Match(p1, p4, RESULTS.DRAW);
    Match m4 = new Match(p4, p1, RESULTS.DRAW);
    Match m5 = new Match(p6, p2, RESULTS.DRAW);
    Match m6 = new Match(p3, p6, RESULTS.DRAW);
    public Player p = p1;

    public TournamentFacade() {
        
        addPlayersT1(t1);
        addPlayerst2(t2);
        addMatch1(t1);
        addMatch2(t2);
        tournamentList.add(t1);
        tournamentList.add(t2);
    }

    public void add(Tournament tournois) {
        tournamentList.add(tournois);
    }

    public Tournament getTournamantList(String name) {
        Tournament value = null;
        for (int i = 0; i <= tournamentList.size() - 1; ++i) {
            if (tournamentList.get(i).equals(name)) {
                value = tournamentList.get(i);
            }
        }
        return value;
//        if (tournamentList.contains(t)) {
//            return t;
//        }
//        return null;

    }

    public Tournament getTournois() {
        return tournamentList.get(index);

    }
    
    public void setIndex(int indes){
        this.index=indes;
        notif(TypeNotif.TOURNAMENT_SELECTED);
    }

    public void addPlayersT1(Tournament tournois) {
        tournois.addPlayer(p1);
        tournois.addPlayer(p2);
        tournois.addPlayer(p3);
        tournois.addPlayer(p4);
        tournois.addPlayer(p5);
        tournois.addPlayer(p6);

    }
     public void addPlayerst2(Tournament tournois) {
        tournois.addPlayer(p1);
        tournois.addPlayer(p2);
        tournois.addPlayer(p3);
       

    }

    public void addMatch1(Tournament tournois) {
        tournois.addMatch(m);
        tournois.addMatch(m2);
        tournois.addMatch(m3);
        tournois.addMatch(m4);
        tournois.addMatch(m5);
        tournois.addMatch(m6);
    }
      public void addMatch2(Tournament tournois) {
        tournois.addMatch(m);
        tournois.addMatch(m2);
        tournois.addMatch(m3);
      
    }


    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void removeActualPlayer(Player p,Tournament tournois ){
        tournois.soutOpponentsListWithoutThisPlayer(p);

    }

    public List<String> getConvertMAtchList(Tournament tournois) {
        return tournois.getListMatchString();
    }

    public List<Player> getSubscrib(Tournament tournois) {
        return tournois.getSubscribersList();
    }

    public Set<Match> getMatchList(Tournament tournois) {

        return tournois.getMatchList();

    }

    public Set<String> getopponentInvalidList(Tournament tournois) {
        return tournois.getOpponentInvalidList();

    }

//    public Set<String> getopponentValidList() {
//        return tournois.getopponentsValidList();
//
//    }
//
//    public Set<String> getTestJouerValid() {
//        return tournois.getTestJouerValide();
//
//    }
//
//    public List<String> getPlayerList() {
//        return tournois.getPlayerList();
//    }

    public Player createPlayer(String name) {
        return new Player(name);
    }

    public Match createMatch(Player p, Player p1, RESULTS res) {
        return new Match(p, p1, res);
    }

    public Tournament createTournament(String name) {
        return new Tournament(name);

    }
    
    public void notif(TypeNotif typeNotif) {
        setChanged();// cet methode renvoi un boullean et permet de faire des notif uniquement quand un changement a eux lieux.
        notifyObservers(typeNotif);
    }

//
//    public List<String> getConvertMatchList() {
//        return tournois.getListMatchString();
//    }
//
//    public String getTournamentName() {
//        return tournois.getName();
//    }
//
//    public List<Tournament> getTournois() {
//        return tournamentList;
//    }
//
//    public List<String> getOpponentList() {
//        return tournois.getOpponentsList();
//    }
//
//    public List<Player> getAdvertList(Player p) {
//        return tournois.advertList(p);
//    }

    public static void main(String[] args) {
        int index = 0;
        Tournament c = new Tournament("test");
        Tournament c2 = new Tournament("tournois2");
        Tournament c3 = new Tournament("tournois3");
        Tournament c4 = new Tournament("tournois3");

        TournamentFacade facade = new TournamentFacade();
        facade.add(c);
        facade.add(c2);
        facade.add(c3);
        facade.add(c4);
        Tournament t=facade.getTournois();
        System.out.println(facade.t1.getName());
       
        
      

    }

}
