/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
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
    private Player actualPlayer;
    private Match selectedMatch;
    private Tournament tournois;
    private IntegerProperty indexMatch;

    public TournamentFacade() {
        initData();
    }

    public Tournament getTournament() {
        
        return tournamentList.get(indexTournament.get());
    }

    public IntegerProperty getIndexTournament() {
        return indexTournament;
    }

    public Player getActual() {
        return actualPlayer;
    }

    public void setIndexTournament(int indexTournaments) {
        this.indexTournament.set(indexTournaments);
    }

    public void setPlayer(Player player) {
        this.actualPlayer = player;
    }

    public void setIndexSelectedMatch(Match m, int index) {
        this.indexMatch.set(index); 
        this.selectedMatch = m;
    }

   

    public void createNewMatch(Player p1, Player p2, RESULTS res) {
        Match m = new Match(p1, p2, res);
        this.getTournament().addMatch(m);
    }

    public Match selectedMatchProperty() {
        return selectedMatch;
    }

    public Player actualPlayerProperty() {
        return actualPlayer;
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

    // ajouter les match deja jouer par le player p dans matchPlayed.
    public List<Match> addMatchPlayed() {
        List<Match> matchPlayed = new ArrayList<>();
        for (Match m : this.getTournament().getMatchList()) {
            if (m.getPlayer1() == actualPlayer || m.getPlayer2() == actualPlayer) {
                matchPlayed.add(m);
            }
        }
        return matchPlayed;
    }

    // ajouter les adversaire de player p dans la liste opponentsListInvalid(les joueur qui n'ont deja jouer contre player p et recois aussi la liste des matchs deja jouer par player p)
    private List<Player> addOpponentInvalidList() {
        List<Player> playerInvalid = new ArrayList<>();
        for (Match m : addMatchPlayed()) {
            if (!m.getPlayer1().equals(actualPlayer)) {
                playerInvalid.add(m.getPlayer1());
            }
            if (!m.getPlayer2().equals(actualPlayer)) {
                playerInvalid.add(m.getPlayer2());
            }
        }
        return playerInvalid;
    }

    public ObservableList<Player> addOppponentValidList() {
        ObservableList<Player> playerValid = FXCollections.observableArrayList();
        for (Player s : this.getTournament().getSubscribersList()) {
            if (!addOpponentInvalidList().contains(s) && !s.equals(actualPlayer)) {
                playerValid.add(s);
            }
        }
        return playerValid;
    }

    public ObservableList<Tournament> getTournamentList() {
        return tournamentList;
    }

    public ObservableList<Player> getSubscrib() {
        return this.getTournament().getSubscribersList();
    }

    public ObservableList<Match> getMatchList() {
        return getTournament().getMatchList();
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
