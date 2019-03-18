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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private final int MAX_POINT = 10;
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
    public BooleanProperty btnValidate = new SimpleBooleanProperty();

    private IntegerProperty cptPoint = new SimpleIntegerProperty();

    private StringProperty questionName = new SimpleStringProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    public StringProperty res1 = new SimpleStringProperty();
    public StringProperty res2 = new SimpleStringProperty();
    public StringProperty res3 = new SimpleStringProperty();
    public StringProperty res4 = new SimpleStringProperty();
    public ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    public IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    public IntegerProperty indexQuestion = new SimpleIntegerProperty();
    public BooleanProperty gameOver = new SimpleBooleanProperty();
    public final BooleanProperty bl = new SimpleBooleanProperty(true);
    public final BooleanProperty deselectedRadioButon = new SimpleBooleanProperty(true);
    public ObjectProperty<Player> clearPlayerOne = new SimpleObjectProperty<>();
    public ObjectProperty<Player> clearPlayerTwo = new SimpleObjectProperty<>();
    public ObjectProperty<String> clearResult = new SimpleObjectProperty<>();

    public ViewModel(TournamentFacade facade) {
        this.facade = facade;

        bl.setValue(Boolean.TRUE);
        btnValidate.set(true);
    }

    public void setAttributQuetion(Question q) {
        if (null != q) {
            this.questionName.set(q.getName().get());
            this.questionPoint.set(q.getPoints());
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

    public void clearOppList() {
        this.oppList().clear();
    }

    public void launchPopUp(MouseEvent mouseEvent, TableView<Match> matchesList) throws FileNotFoundException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2 && matchesList.getSelectionModel().getSelectedItem() != null) {
                new PopUpDelete(matchSelected.get(), this);
                clearOppList();
                oppList();
            }
        }
        ClearComboBox();
    }

    public void launchGame(Player p1, Player p2) throws Exception {
        if (!cb1.get().getFirstName().equals("") && !cb2.get().getFirstName().equals("")) {
            bl.setValue(true);
            VMInitGame vm1 = new VMInitGame(this);
            new ViewInitGame(vm1, p1, p2);
        }
    }

    public void createMatch(String t) {
        if (!t.equals(null)) {
            Match m = new Match(new Player(cb1.getValue().toString()),
                    new Player(cb2.getValue().toString()),
                    results(t));
            if (!matchsProperty().contains(m)) {
                facade.getTournament().addMatch(m);
            }
        }
        ClearComboBox();
    }

    public void ClearComboBox() {
        clearPlayerOne.set(null);
        clearPlayerTwo.set(null);
        clearResult.set("");
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

    public void oppValidList() {
        if (actualProperty().get() != null) {
            ObservableList<Player> list2 = addOpponentInvalidList();
            oppList.clear();
            for (Player s : subscribesListProperty()) {
                if (!list2.contains(s) && !s.getFirstName().equals(actualPlayer.getValue().toString())) {
                    this.oppList.add(s);
                }
            }
        } 
        if(actualProperty().get() != null && cb2.get() != null && cb3.get() != null)
            btnValidate.set(false);

        }

    

    public void emptyselectedList() {
        selectedQuestionList.clear();
    }

//    public void clearComboBox() {
//        cb1.set(new Player(""));
//        cb2.set(new Player(""));
//        cb3.set(" ");
//    }
    public void setTournament() {
        facade.indexTournamentProperty().set(indexTournament.get());
        ClearComboBox();
    }

    public SimpleListProperty<Question> quetionsProperty() {
        return new SimpleListProperty<>(facade.getQuestions());
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

    public Tournament getTournament() {
        return facade.getTournament();
    }
}
