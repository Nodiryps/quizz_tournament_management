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
import javafx.collections.FXCollections;
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
//    private SetProperty<Match> matchList;
    private ListProperty<Player> subscribeList;
//    private ListProperty<Tournament> tournamentList;
    private IntegerProperty indexTournament=new SimpleIntegerProperty(1);
    private StringProperty actualPlayer=new SimpleStringProperty();
//    private Match selectedMatch;
//    private Tournament tournois;
//    private int indexMatch;

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;
        configBinding();
    }

    public SimpleListProperty<Player> subscribesListProperty() {
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

    public SimpleListProperty opponentsListProperty() {
        return new SimpleListProperty<>(this.newOppList());
    }

    private ObservableList<Match> macthByPlayer() {
        ObservableList<Match> list = FXCollections.observableArrayList();
        for (Match m : this.matchsProperty()) {
            if (m.getPlayer1().getFirstName().equals(this.actualPlayer.getValue())
                    || m.getPlayer2().getFirstName().equals(this.actualPlayer.getValue())) {
                list.add(m);
            }
        }
        return list;
    }

    private ObservableList<String> pastOppList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Match m : this.macthByPlayer()) {
            list.add(m.getPlayer2().getFirstName());
//            if (m.getPlayer1().equals(this.actualPlayer.getValue())) {
//                list.add(m.getPlayer2().getFirstName());
//            } else {
//                list.add(m.getPlayer1().getFirstName());
//            }
        }
        return list;
    }

    private ObservableList<Player> newOppList() {
        ObservableList<Player> list = FXCollections.observableArrayList();
        if (this.actualPlayer.equals("")) {
            list = this.subscribesListProperty();
        } else {
            for (Player p : this.subscribesListProperty()) {
                if (!this.pastOppList().contains(p.getFirstName()) && !p.getFirstName().equals(this.actualPlayer.getValue())) {
                    list.add(p);
                }
            }
        }
        return list;
    }
    
    public void createMatch(Player p1, Player p2, RESULTS res) {
        facade.createNewMatch(p1, p2, res);
    }

//    public void setIndex(int index) {
//        this.indexTournament.set(index);
//        System.out.println(indexTournament);
//        facade.setIndexTournament(indexTournament.get());
//    }

    public void configBinding(){
     this.indexTournamentProperty().bindBidirectional(facade.getIndexTournament());
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

//    public ListProperty<Player> subscribeListProperty() {
//        return subscribeList;
//    }
    
    public IntegerProperty indexTournamentProperty(){
       return this.indexTournament;
    }
}
