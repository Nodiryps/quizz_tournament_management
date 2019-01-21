package controller;

import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
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
    private IntegerProperty indexTournament=new SimpleIntegerProperty(0);
    private StringProperty actualPlayer=new SimpleStringProperty();
    private Match selectedMatch;
    private Tournament tournois;
    private int indexMatch;

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;
    }


    public SimpleListProperty<Player> subscribesProperty() {
        return new SimpleListProperty<>(facade.getSubscrib());
    }
     public SimpleListProperty<Player> OppValidProperty() {
        return new SimpleListProperty<>(facade.addOppponentValidList());
    }
    
 
    public SimpleListProperty<Match> matchsProperty() {
        return new SimpleListProperty<>(facade.getMatchList());
    }

    public SimpleListProperty tournamantProperty() {
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
        this.indexTournament.setValue(index);
        System.out.println(indexTournament.getValue());
        facade.setIndexTournament(indexTournament.getValue());
    }

    public void DelMatch(Match m) {
        facade.removeMatch();
    }

    public void setMatchSelected(Match m, int index) {
        facade.setIndexSelectedMatch(m, index);
    }

    public Match getSelectedMatch() {
        return facade.selectedMatchProperty();
    }
 public StringProperty actualProperty() {
        return actualPlayer;
    }
    public ObservableList<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }
    
    

    public void setPlayer(Player p) {
        Player p2=new Player(p.getFirstName());
        this.actualPlayer=new SimpleStringProperty(p2.getFirstName());
        facade.actualPlayerProperty();
    }

//    public SetProperty<Match> matchListProperty() {
//        return matchList;
//    }
    public ListProperty<Tournament> tournamentListProperty() {
        return new SimpleListProperty<>(facade.getTournamentList());
    }

    public ListProperty<Player> subscribeListProperty() {
        return subscribeList;
    }
}
