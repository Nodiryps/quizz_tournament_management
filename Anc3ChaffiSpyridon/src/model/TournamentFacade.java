/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Spy
 */
public class TournamentFacade {

   public  List<Tournament> tournamentList = new ArrayList<>();
    public static Tournament tournois;

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

    public TournamentFacade(String name) {

        tournamentList.add(new Tournament(name));
        this.tournois=new Tournament(name);
        addPlayer();
        addMatch();
        removeActualPlayer(p);
    }

    public Tournament getTournamantList(String name) {
          Tournament value=null;
        for (int i = 0; i <= tournamentList.size() - 1; ++i) {
            if (tournamentList.get(i).equals(name)) {
               value=tournamentList.get(i);
            }
        }
        return value;
//        if (tournamentList.contains(t)) {
//            return t;
//        }
//        return null;

    }
   

    public void addPlayer() {
        tournois.addPlayer(p1);
        tournois.addPlayer(p2);
        tournois.addPlayer(p3);
        tournois.addPlayer(p4);
        tournois.addPlayer(p5);
        tournois.addPlayer(p6);

    }

    public void addMatch() {
        tournois.addMatch(m);
        tournois.addMatch(m2);
        tournois.addMatch(m3);
        tournois.addMatch(m4);
        tournois.addMatch(m5);
        tournois.addMatch(m6);
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void removeActualPlayer(Player p) {
        tournois.soutOpponentsListWithoutThisPlayer(p);

    }

    public List<String> getConvertMAtchList() {
        return tournois.getListMatchString();
    }

    public  List<Player> getSubscrib() {
        return tournois.getSubscribersList();
    }

    public Set<Match> getMatchList() {

        return tournois.getMatchList();

    }

    public Set<String> getopponentInvalidList() {
        return tournois.getOpponentInvalidList();

    }

    public Set<String> getopponentValidList() {
        return tournois.getopponentsValidList();

    }

    public Set<String> getTestJouerValid() {
        return tournois.getTestJouerValide();

    }

    public List<String> getPlayerList() {
        return tournois.getPlayerList();
    }

    public Player createPlayer(String name) {
        return new Player(name);
    }

    public Match createMatch(Player p, Player p1, RESULTS res) {
        return new Match(p, p1, res);
    }

    public Tournament createTournament(String name) {
        return new Tournament(name);

    }

    public List<String> getConvertMatchList() {
        return tournois.getListMatchString();
    }

    public String getTournamentName() {
        return tournois.getName();
    }

    public List<Tournament> getTournois() {
        return tournamentList;
    }

    public List<String> getOpponentList() {
        return tournois.getOpponentsList();
    }

    public static void main(String[] args) {
        TournamentFacade c = new TournamentFacade("test");
        System.out.println(c.getSubscrib());
        System.out.println("*********************************************");
        System.out.println(c.getMatchList());
        System.out.println("*********************************************");
        System.out.println(tournois.getMatchPlayed());
        System.out.println("*********************************************");
        System.out.println(c.getTestJouerValid());
        System.out.println("*********************************************");
        System.out.println(c.getOpponentList());
        System.out.println("*********************************************");
        System.out.println(c.getConvertMatchList());
        System.out.println("*********************************************");
        System.out.println(tournois.getName());
    }
    
  
}
