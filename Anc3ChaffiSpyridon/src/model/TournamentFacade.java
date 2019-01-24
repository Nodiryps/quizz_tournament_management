/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;

/**
 *
 * @author Spy
 */
public class TournamentFacade {
    public enum TypeNotif {
        INIT, TOURNAMENT_SELECTED, PLAYER_ONE_SELECTED, PLAYER_TWO_SELECTED, ADD_MATCH, REMOVE_MATCH
    }

    private ObservableList<Tournament> tournamentList = FXCollections.observableArrayList();
    private IntegerProperty indexTournament=new SimpleIntegerProperty();
    private StringProperty actualPlayer=new SimpleStringProperty();
    private Match selectedMatch;
    private IntegerProperty indexMatch;

    public TournamentFacade() {
        initData();
    }

    public Tournament getTournament() {
        return tournamentList.get(indexTournament.get());
    }
    
    public int getIndexTournament() {
        return indexTournament.get();
    }
    
    public IntegerProperty indexTournamentProperty() {
        return indexTournament;
    }
    
    public StringProperty actualPlayerProperty() {
        return actualPlayer;
    }
    
    public ObservableList<Tournament> getTournamentList() {
        return tournamentList;
    }

    public ObservableList<Player> getTournamentSubsList() {
        return getTournament().getSubscribersList();
    }

    public ObservableList<Match> getMatchList() {
        return getTournament().getMatchList();
    }
    
    public ObservableList<RESULTS> getResults() {
        ObservableList<RESULTS> list = FXCollections.observableArrayList();
        list.addAll(RESULTS.values());
        return list;
    }

    public void setIndexTournament(int indexTournaments) {
        this.indexTournament.set(indexTournaments);
    }

//    public void setPlayer(Player player) {
//        this.actualPlayer = player;
//    }

    public void setIndexSelectedMatch(Match m, int index) {
        this.indexMatch.set(index); 
        this.selectedMatch = m;
    }

    public Match selectedMatchProperty() {
        return selectedMatch;
    }

    public int indexMatchProperty() {
        return indexMatch.get();
    }

    public void removeMatch() {
        if (indexMatch.get() == 0) {
            this.getTournament().getMatchList().remove(selectedMatch);
        } else {
            this.getTournament().getMatchList().remove(selectedMatch);
        }
    }
    
    public void createNewMatch(Player p1, Player p2, RESULTS res) {
        Match m = new Match(p1, p2, res);
        this.getTournament().addMatch(m);
    }

    // ajouter les match deja jouer par le player p dans matchPlayed.
    private ObservableList<Match> addMatchPlayed() {
        ObservableList<Match> matchPlayed = FXCollections.observableArrayList();
        for (Match m : this.getTournament().getMatchList()) {
            if (m.getPlayer1().getFirstName().equals(actualPlayer.get()) 
                    || m.getPlayer2().getFirstName().equals(actualPlayer.get())) {
                matchPlayed.add(m);
            }
        }
        return matchPlayed;
    }
    
    private boolean isTheOpponent(String p) {
        return !p.equals(actualPlayer.get());
    }

     //ajouter les adversaire de player p dans la liste opponentsListInvalid(les joueur qui n'ont deja jouer contre player p et recois aussi la liste des matchs deja jouer par player p)
    private ObservableList<Player> addOpponentInvalidList() {
        ObservableList<Player> playerInvalid = FXCollections.observableArrayList();
        for (Match m : addMatchPlayed()) {
            if (isTheOpponent(m.getPlayer1().getFirstName())) {
                playerInvalid.add(m.getPlayer1());
            }
            if (isTheOpponent(m.getPlayer2().getFirstName())) {
                playerInvalid.add(m.getPlayer2());
            }
        }
        return playerInvalid;
    }

    public ObservableList<Player> oppValidList() {
        ObservableList<Player> playerValid = FXCollections.observableArrayList();
        ObservableList<Player> list2 = addOpponentInvalidList();
        for (Player s : getTournamentSubsList()) {
            if (!list2.contains(s) && !s.getFirstName().equals(actualPlayer.get())) {
                playerValid.add(s);
            }
        }
        return playerValid;
    }

    public void initData() {
        Tournament t1 = new Tournament("E-Sport");
        Tournament t2 = new Tournament("T2");

        Player p1 = new Player("Philippe");
        Player p2 = new Player("Khadija");
        Player p3 = new Player("spyridon");
        Player p4 = new Player("chaffi");
        Player p5 = new Player("lindsay");
        Player p6 = new Player("rodolphe");
        t1.addPlayer(p1);
        t1.addPlayer(p2);
        t1.addPlayer(p3);
        t1.addPlayer(p4);
        t1.addPlayer(p5);
        t1.addPlayer(p6);
        t2.addPlayer(p1);
        t2.addPlayer(p2);
        t2.addPlayer(p3);
        Match m = new Match(p1, p2, RESULTS.EX_AEQUO);
        Match m2 = new Match(p2, p1, RESULTS.EX_AEQUO);
        Match m3 = new Match(p1, p4, RESULTS.EX_AEQUO);
        Match m4 = new Match(p4, p1, RESULTS.EX_AEQUO);
        Match m5 = new Match(p6, p2, RESULTS.EX_AEQUO);
        Match m6 = new Match(p3, p6, RESULTS.EX_AEQUO);

        t1.addMatch(m);
        t1.addMatch(m2);
        t1.addMatch(m3);
        t1.addMatch(m4);
        t1.addMatch(m5);
        t1.addMatch(m6);
        t2.addMatch(m);
        t2.addMatch(m2);
        t2.addMatch(m3);
        t2.addMatch(m4);
        tournamentList.add(t1);
        tournamentList.add(t2);
    }
    
  

}
