package controller;

import java.io.FileNotFoundException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Match;
import model.Player;
import model.Tournament;
import model.TournamentFacade;
import model.RESULTS;
import view.PopUpDelete;

/**
 *
 * @author Spy
 */
public final class ViewModel {

    TournamentFacade facade;
    private ListProperty<Player> subscribeList;
    private ObservableList<Player> oppList = FXCollections.observableArrayList();
//    private ListProperty<Tournament> tournamentList;
    public SimpleIntegerProperty indexTournament = new SimpleIntegerProperty();
    public ObjectProperty actualPlayer = new SimpleObjectProperty();
    public ObjectProperty cb1 = new SimpleObjectProperty();
    public ObjectProperty cb2 = new SimpleObjectProperty();
    public ObjectProperty cb3 = new SimpleObjectProperty();
    public IntegerProperty indexMatch = new SimpleIntegerProperty();
    public ObjectProperty<Match> matchSelected=new SimpleObjectProperty();

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;
        //configBinding();
    }

    public ViewModel() {
    }
    
     public  void setTournois(){
      facade.setIndexTournament(this.indexTournament.get());
    }


    public SimpleListProperty<Player> subscribesListProperty() {
        return new SimpleListProperty<>(facade.getTournamentSubsList());
    }

    public SimpleIntegerProperty IndexMatchProperty() {
        return new SimpleIntegerProperty(indexMatch.get());
    }
public SimpleObjectProperty<Match> matchSelectedProperty(){
  return new SimpleObjectProperty(matchSelected);
}
    public SimpleListProperty<Player> opponentsListProperty() {
        return new SimpleListProperty<>(this.oppList);
    }

    public SimpleListProperty<RESULTS> resultsListProperty() {
        return new SimpleListProperty<>(facade.getResults());
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

    private ObservableList<Match> macthByPlayer() {
        ObservableList<Match> list = FXCollections.observableArrayList();
        for (Match m : this.matchsProperty()) {
            if (m.getPlayer1().getFirstName()
                    .equals(this.actualPlayer.getValue())
                    || m.getPlayer2().getFirstName().equals(this.actualPlayer.getValue().toString())) {
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
    
    public void launchPopUp() throws FileNotFoundException{
      new PopUpDelete(matchSelected.get(), this);
    }

    public void newOppList() {
        ObservableList<Player> list = FXCollections.observableArrayList();
        ObservableList<String> list2 = pastOppList();
        oppList.clear();
        if (this.actualPlayer.equals(" ")) {
            oppList = this.subscribesListProperty();
        } else {
            for (Player p : this.subscribesListProperty()) {
                if (!list2.contains(p.getFirstName()) && !p.getFirstName().equals(this.actualPlayer.getValue().toString())) {
                    oppList.add(p);
                }
            }
        }
        System.out.println(oppList);
    }

    public void createMatch() {
        System.out.println(cb1.getValue().toString());
        Match m = new Match(new Player(cb1.getValue().toString()),
                new Player(cb2.getValue().toString()),
                results(cb3.getValue().toString()));
        System.out.println(m);
        if (!matchsProperty().contains(m)) {
            facade.getTournament().addMatch(m);
        }
    }
     public void removeMatch() {
        if (this.indexMatch.get() == 0) {
            this.getTournament().getMatchList().remove(this.matchSelected.get());
        } else {
            this.getTournament().getMatchList().remove(this.matchSelected.get());
        }
    }

    private RESULTS results(String res) {
        System.out.println(RESULTS.VAINQUEUR_J1.name());
        if (res.equals(RESULTS.VAINQUEUR_J1.name())) {
            return RESULTS.VAINQUEUR_J1;
        }
        if (res.equals(RESULTS.VAINQUEUR_J2.name())) {
            return RESULTS.VAINQUEUR_J2;
        }
        if (res.equals(RESULTS.EX_AEQUO.name())) {
            return RESULTS.EX_AEQUO;
        }
        return null;
    }

//    public void configBinding() {
//        this.indexTournamentProperty().bindBidirectional(facade.indexTournamentProperty());
//        this.actualProperty().bindBidirectional(facade.actualPlayerProperty());
//    }
 

    public void setMatchSelected(int index) {
       indexMatch.set(index);
        System.out.println(indexMatch.get());
    }

    public Match getSelectedMatch() {
        return facade.selectedMatchProperty();
    }

    public ObjectProperty actualProperty() {
        return actualPlayer;
    }

    public ObservableList<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }

//    public void setPlayer(Player p) {
//        this.actualPlayer=new SimpleStringProperty(p.getFirstName());
//        facade.actualPlayerProperty();
//    }
//    public SetProperty<Match> matchListProperty() {
//        return matchList;
//    }
    public ListProperty<Tournament> tournamentListProperty() {
        return new SimpleListProperty<>(facade.getTournamentList());
    }

//    public ListProperty<Player> subscribeListProperty() {
//        return subscribeList;
//    }
    public SimpleIntegerProperty indexTournamentProperty() {
        return new SimpleIntegerProperty(this.indexTournament.get());
    }
    public void setTournamant(int index){
      this.indexTournamentProperty().set(index);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // ajouter les match deja jouer par le player p dans matchPlayed.
    public ObservableList<Match> addMatchPlayed() {
        ObservableList<Match> matchPlayed = FXCollections.observableArrayList();
        for (Match m : matchsProperty()) {
            if (m.getPlayer1().getFirstName().equals(actualPlayer.get().toString())
                    || m.getPlayer2().getFirstName().equals(actualPlayer.get().toString())) {
                matchPlayed.add(m);
            }
        }
        return matchPlayed;
    }

    private boolean isTheOpponent(String p) {
        return !p.equals(actualPlayer.getValue().toString());
    }

    //ajouter les adversaire de player p dans la liste opponentsListInvalid(les joueur qui n'ont deja jouer contre player p et recois aussi la liste des matchs deja jouer par player p)
    public ObservableList<Player> addOpponentInvalidList() {
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

    public void oppValidList() {
        ObservableList<Player> playerValid = FXCollections.observableArrayList();
        ObservableList<Player> list2 = addOpponentInvalidList();
        oppList.clear();
        for (Player s : subscribesListProperty()) {
            if (!list2.contains(s) && !s.getFirstName().equals(actualPlayer.getValue().toString())) {
                this.oppList.add(s);
            }

        }
    }
    
    public void clearOppList(){
     this.oppList.clear();
    }

    public static void main(String[] args) {
        TournamentFacade t = new TournamentFacade();
        ViewModel m = new ViewModel(t);

        m.actualPlayer.set("lindsay");
        System.out.println(m.actualPlayer.get());
        m.oppValidList();
//        System.out.println(m.matchsProperty());
//        System.out.println(m.addMatchPlayed());
        System.out.println(m.oppList);
    }

}
