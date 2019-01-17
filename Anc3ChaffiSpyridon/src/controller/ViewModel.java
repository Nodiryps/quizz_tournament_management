package controller;

import java.util.Set;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import model.Match;
import model.Player;
import model.Tournament;
import model.TournamentFacade;
import model.RESULTS;

/**
 *
 * @author Spy
 */
public final class ViewModel {

    TournamentFacade facade;
    private SetProperty<Match> matchList;
    private ListProperty<Player> subscribeList;

    private ListProperty<Tournament> tournamentList;
    public int indexTournament;
    public Player actualPlayer;
    public Match selectedMatch;
    public Tournament tournois;
    public int indexMatch;

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;
        matchList = new SimpleSetProperty<>(facade.getMatchList());
        tournamentList = new SimpleListProperty<>(facade.getTournamentList());
        subscribeList = new SimpleListProperty<>(facade.getSubscrib());
        testbind();
    }

    public void testbind() {
       tournois.bind(facade.tournois);
    }
    
    public SimpleListProperty<Player> subscribesProperty() {
        return new SimpleListProperty<>(facade.getSubscrib());
    }
    
    public SimpleSetProperty<Match> matchsProperty() {
        return new SimpleSetProperty<>(facade.getMatchList());
    }
    
    public SimpleListProperty tournamantProperty(){
      return new SimpleListProperty<>(facade.getTournamentList());
    }
    

    public TournamentFacade getFacade() {
        return facade;

    }

    public Tournament getTournament() {
        return facade.getTournament();
    }

    public void createMatch(Player p1, Player p2, RESULTS res) {
        facade.createNewMatch(p1, p2, res);
    }

    public void setIndex(int index) {
        facade.setIndexTournament(index);
    }

    public void DelMatch(Match m) {
        facade.removeMatch();
    }

    public void setMatchSelected(Match m, int index) {
        facade.setIndexSelectedMatch(m, index);
    }

    public Match getSelectedMatch() {
        return facade.getSelectedMatch();
    }

    public Set<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }

    public void setPlayer(Player p) {
        facade.setPlayer(p);
    }

//    public SetProperty<Match> matchListProperty() {
//        return matchList;
//    }

    public ListProperty<Tournament> tournamentListProperty() {
        return tournamentList;
    }

    public ListProperty<Player> subscribeListProperty() {
        return subscribeList;
    }
}
