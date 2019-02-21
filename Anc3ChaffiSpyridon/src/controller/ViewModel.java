package controller;

import java.io.FileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import model.Question;
import model.Tournament;
import model.TournamentFacade;
import model.RESULTS;
import view.PopUpDelete;
import view.ViewInitGame;

/**
 *
 * @author Spy
 */
public class ViewModel {

    TournamentFacade facade;
    private ListProperty<Player> subscribeList;
    private ObservableList<Player> oppList = FXCollections.observableArrayList();
    public IntegerProperty indexTournament = new SimpleIntegerProperty();
    private final ObjectProperty<Player> actualPlayer = new SimpleObjectProperty<Player>();
    private final ObjectProperty<Player> cb1 = new SimpleObjectProperty<>();
    private final ObjectProperty<Player> cb2 = new SimpleObjectProperty<>();
    private StringProperty cb3 = new SimpleStringProperty();
    private IntegerProperty indexMatch = new SimpleIntegerProperty();
    public ObjectProperty<Match> matchSelected = new SimpleObjectProperty<>();
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
   
    private IntegerProperty cptPoint = new SimpleIntegerProperty();
    private final int MAX_POINT = 10;
    private StringProperty questionName = new SimpleStringProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    public StringProperty res1 = new SimpleStringProperty();
    public StringProperty res2 = new SimpleStringProperty();
    public StringProperty res3 = new SimpleStringProperty();
    public StringProperty res4 = new SimpleStringProperty();
    private BooleanProperty disable = new SimpleBooleanProperty();
    public ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    public IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    public IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private String resuls;
    public BooleanProperty gameOver = new SimpleBooleanProperty();
    public final BooleanProperty bl = new SimpleBooleanProperty(true);
    public final BooleanProperty deselectedRadioButon = new SimpleBooleanProperty(true);

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;
        
        bl.setValue(Boolean.TRUE);
    }

    public StringProperty getRes1() {
        return res1;
    }

    public StringProperty getRes2() {
        return res2;
    }

    public StringProperty getRes3() {
        return res3;
    }

    public StringProperty getRes4() {
        return res4;
    }

   
    public ObjectProperty<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public StringProperty questionNameProperty() {
        return questionName;
    }

    public IntegerProperty questionPointProperty() {
        return questionPoint;
    }

    public ObservableList<Question> selectedQuestionList() {
        return selectedQuestionList;
    }

    public void setAttributQuetion(Question q) {
        if (null != q) {
            this.questionName.set(q.getName().get());
            this.questionPoint.set(q.pointsProperty().getValue());
            setReponse(q);
            this.currentQuestion.set(q);
        }
    }

    public void setReponse(Question q) {
        res1.set(q.getResponses().get(0));
        res2.set(q.getResponses().get(1));
        res3.set(q.getResponses().get(2));
        res4.set(q.getResponses().get(3));
    }

   


    public SimpleListProperty<Question> quetionsProperty() {
        return new SimpleListProperty<>(facade.getQuestion());
    }

    public void setTournois() {
        facade.indexTournamentProperty().set(indexTournament.get());
    }

    public void clearOppList() {
        this.oppList().clear();
    }

    public ObservableList<Player> oppList() {
        return this.oppList;
    }

    public SimpleListProperty<Player> subscribesListProperty() {
        return new SimpleListProperty<>(facade.getTournamentSubsList());
    }

    public ObjectProperty<Player> combobox1Property() {
        return cb1;
    }

    public ObjectProperty<Player> combobox2Property() {
        return cb2;
    }

    public StringProperty combobox3Property() {
        return cb3;
    }

    public SimpleIntegerProperty indexMatchProperty() {
        return new SimpleIntegerProperty(indexMatch.get());
    }

    public ObjectProperty<Match> matchSelectedProperty() {
        return matchSelected;
    }

    public SimpleListProperty<Player> opponentsListProperty() {
        return new SimpleListProperty<Player>(this.oppList);
    }

    public SimpleListProperty<Match> matchsProperty() {
        return new SimpleListProperty<Match>(facade.getMatchList());
    }

    public SimpleListProperty<Tournament> tournamantProperty() {
        return new SimpleListProperty<Tournament>(facade.getTournamentList());
    }

    public TournamentFacade getFacade() {
        return facade;
    }

    public ObjectProperty<Player> actualProperty() {
        return actualPlayer;
    }

    public ObservableList<Match> getAllMatch() {
        return facade.getTournament().getMatchList();
    }

    public IntegerProperty indexTournamentProperty() {
        return this.indexTournament;
    }

    public void setTournamant(int index) {
        this.indexTournamentProperty().set(index);
    }

    public Tournament getTournament() {
        return facade.getTournament();
    }

    public void launchPopUp() throws FileNotFoundException {
        new PopUpDelete(matchSelected.get(), this);
    }

    public void launchGame(Player p1, Player p2) throws Exception {
        bl.setValue(true);
        VMInitGame vm1 = new VMInitGame(this);
        new ViewInitGame(vm1, cb1.get(), cb2.get());
    }

    public void createMatch(String t) {
        Match m = new Match(new Player(cb1.getValue().toString()),
                new Player(cb2.getValue().toString()),
                results(t));
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

    

    public void emptyselectedList() {
        selectedQuestionList.clear();
    }

    public void clearComboBox() {
        cb1.set(new Player(""));
        cb2.set(new Player(""));
        cb3.set(" ");
    }
}
