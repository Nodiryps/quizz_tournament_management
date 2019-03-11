/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
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
    private IntegerProperty indexTournament = new SimpleIntegerProperty();
    private StringProperty actualPlayer = new SimpleStringProperty();
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

    public ObservableList<Question> getQuestions() {
        return getTournament().getQuestions();
    }

    public IntegerProperty indexTournamentProperty() {
        return indexTournament;
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

    public Match selectedMatchProperty() {
        return selectedMatch;
    }
    
    public ObservableList<Category> getCategory(){
      return getTournament().getCat();
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
