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
import model.RESULTS;

/**
 *
 * @author Spy
 */
public final class Controller {

    List<Tournament> tournois = new ArrayList<>();

    public Tournament t = new Tournament("Game of thrones");

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
    public Player p = p4;

    public Controller() {
        tournois.add(t);
        addPlayer();
        addMatch();
        removeActualPlayer();
        getConvertMAtchList();
        
        
    }

    public void addPlayer() {
        t.addPlayer(p1);
        t.addPlayer(p2);
        t.addPlayer(p3);
        t.addPlayer(p4);
        t.addPlayer(p5);
        t.addPlayer(p6);

    }

    public void addMatch() {
        t.addMatch(m);
        t.addMatch(m2);
        t.addMatch(m3);
        t.addMatch(m4);
        t.addMatch(m5);
        t.addMatch(m6);
    }

    public void removeActualPlayer() {
        t.soutOpponentsListWithoutThisPlayer(p);

    }
 public List<String> getConvertMAtchList(){
    return t.getListMatchString();
 }
    

    public List<Player> getSubscrib() {
        return t.getSubscribersList();
    }

    public Set<Match> getMatchList() {
        return t.getMatchList();

    }

    public Set<String> getopponentInvalidList() {
        return t.getOpponentInvalidList();

    }

    public Set<String> getopponentValidList() {
        return t.getopponentsValidList();

    }

    public Set<String> getTestJouerValid() {
        return t.getTestJouerValide();

    }
    
    public List<String> getPlayerList(){
      return t.getPlayerList();
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
    public List<String> getConvertList(){
      return t.getListMatchString();
    }

    public String getTournamentName() {
        return t.getName();
    }
     public List<Tournament> getTournois(){
       return tournois;
     }
     
     public List<String> getOpponentList(){
        return t.getOpponentsList();
     }
    public static void main(String[] args) {
        Controller c=new Controller();
//        System.out.println(c.getTestJouerValid());
//        System.out.println(c.getTournamentName());
//        System.out.println(c.getOpponentList());
        System.out.println(c.getConvertList());
    }

//    public static void main(String[] args) {
//
//        System.out.println("liste des inscrit au tournoi " + t.getName());
//        System.out.println(t.getSubscribersList() + "\n");
//
//        System.out.println("********************************");
//        System.out.println("Liste de tous les matchs");
//        System.out.println(t.getMatchList());
//        //System.out.println(t.getPlayerList());
//        System.out.println("********************************");
//
//        System.out.println("retourne la liste des adversaires sauf le jouer selectionner");
//        t.soutOpponentsListWithoutThisPlayer(p);
//        System.out.println(t.getopponentsValidList());
//
//        System.out.println("*********************************");
//
//        t.addMatchPlayed(p);
//
//        System.out.println("liste des matched deja jouer");
//        System.out.println(t.getMatchPlayed());
//        System.out.println("liste des jouer qui ont deja jouer avec player :" + p.getFirstName());
//        System.out.println(t.getOpponentInvalidList());
//        System.out.println("liste des personnes qui on deja jouer avec avec le jouer///// " + p.getFirstName() + " :" + t.getOpponentInvalidList());
////         System.out.println(t.getOpponentInvalidList());
////
////    }
//        //System.out.println("la liste des jouer valide qui n'ont pas encore jouer contre: " + t.getopponentsValidList());
//
//        System.out.println("liste des personnes qui peuvent encore jouer contre:////////////// " + p.getFirstName());
//        System.out.println(t.getopponentsValidList());
//
////         System.out.println("*******************teste*************************");
////         t.addOppponentValidList(p);
//        System.out.println("****************************************************");
//        System.out.println("liste des joueur qui peuvent encore jouer contre " + p.getFirstName());
//
//        System.out.println(t.getTestJouerValide());
//    }
}
