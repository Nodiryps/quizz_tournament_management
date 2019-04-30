/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.VMInitGame.cpt;
import java.util.Random;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.CareTaker;
import model.MementoBuilding;
import model.Player;
import model.Question;
import model.RESULTS;

/**
 *
 * @author 2707chshyaka
 */
public class VMGame {

    private VMInitGame vm;

    private static IntegerProperty MAX_POINTS_GAME = new SimpleIntegerProperty();
    private BooleanProperty reponse1 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse2 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse3 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse4 = new SimpleBooleanProperty(true);
    private StringProperty questionName = new SimpleStringProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private IntegerProperty cptPoint = new SimpleIntegerProperty();
    private IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private IntegerProperty totalPoints = new SimpleIntegerProperty();
    private final ObjectProperty<Player> cb1 = new SimpleObjectProperty<>();
    private final ObjectProperty<Player> cb2 = new SimpleObjectProperty<>();
    private StringProperty cb3 = new SimpleStringProperty();
    private final BooleanProperty boolSelectRadioBtn1 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn2 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn3 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn4 = new SimpleBooleanProperty();
    private final BooleanProperty disableRadioBtn = new SimpleBooleanProperty();
    public final BooleanProperty selectRadioBtn = new SimpleBooleanProperty();
    private int totalPointsRestant;
    static int cpt;
    private static CareTaker careTaker;
    private MementoBuilding mementoBuilding;
    private boolean boolLastQuestRight = false;
    private boolean boolRandom = false;
    private boolean isUndo = false;
    public BooleanProperty bntHint = new SimpleBooleanProperty();
    public StringProperty hint = new SimpleStringProperty();
    private boolean hintClicked = false;
    private BooleanProperty btnValidateQuestion = new SimpleBooleanProperty();

    public VMGame(VMInitGame vm,ObservableList<Question> list) {
        this.vm = vm;
        selectedQuestionList=vm.getSelectedQuestionList();
        initData();
    }

    private void initData() {
        careTaker = new CareTaker();
        cpt = 0;
        disablebtnValidateQuestion();
    }

    public void disablebtnValidateQuestion() {
        btnValidateQuestion.set(true);
    }

    public void nextQuestion(String response, Stage stage, ToggleGroup g) {
        if (stage != null && g != null) {
            g.selectToggle(null);
            disablebtnValidateQuestion();
            boolRandom = randomValue();
            if (isGameOn()) {
                vm.displayTheQuestion();
                nextQuestionManagmnt(response);
                ++cpt;
                if (isTheLastQuestion()) {
                    lastQuestion(response);
                }
            }
            if ((noMoreQuestion() || noMorePoints()) && !boolRandom) {
                endOfGameManagmnt(stage);
            }
        }
    }

    private void nextQuestionManagmnt(String response) {
        System.out.println("is1" + isUndo);
        if (hasNextQuestion()) {
            Question q = getQuestionFromIndex();
            totalPointsRestant -= q.getPoints();
            if (!isUndo) {
                System.out.println("is2" + isUndo);
                if (isResponseRight(response)) {
                    boolLastQuestRight = true;
                    incrementPoints(q);
                } else {
                    mementoBuilding = new MementoBuilding(q, response, careTaker);
                }
                incrementQuestion();
            } else {
                System.out.println("is3" + isUndo);
                if (isRightRespUndo(response)) {

                    boolLastQuestRight = true;
                    incrementPoints(mementoBuilding.question);
                    System.out.println("test2");
                    getIndexQuestion().set(cpt + 1);
                    Question p = vm.getSelectedQuestionList().get(cpt);
                    vm.setAttributQuetion(p);
                    incrementQuestion();
                }

            }
            disableRadioBtn.set(true);
            hint.set("");
        }

    }

    public void enablebtnValidateQuestion() {
        btnValidateQuestion.set(false);
    }

    public BooleanProperty getGameOver() {
        return gameOver;
    }

    private boolean noMoreQuestion() {
        return cpt > selectedQuestionList.size();
    }

    private boolean noMorePoints() {
        return totalPointsRestant + vm.cptPointProperty().get() < (MAX_POINTS_GAME.get() / 2);
    }

    private void lastQuestion(String response) {
        if (isResponseRight(response)) {
            Question q = getQuestionFromIndex();
            incrementPoints(q);
        }
        ++cpt;
    }

    private boolean isRightRespUndo(String res) {
        Question q = mementoBuilding.question;
        int x = -1;
        x = q.getResponses().indexOf(res);
        System.out.println(x);
        return q.getNumCorrectResponse().get() == x;
    }

    private boolean isResponseRight(String s) {
        String resp = getResponseFromIndex(getRightResponseIndex());
        return resp.equals(s);
    }

    private int getRightResponseIndex() {
        return vm.getSelectedQuestionList().get(vm.getIndexQuestion().get() - 1).getNumCorrectResponse().get();
    }

    private String getResponseFromIndex(int rightRespIndex) {
        return vm.getSelectedQuestionList().get(vm.getIndexQuestion().get() - 1).getResponses().get(rightRespIndex - 1);
    }

    private Question getQuestionFromIndex() {
        return vm.getSelectedQuestionList().get(vm.getIndexQuestion().get() - 1);
    }

    private void incrementQuestion() {
      
        if (boolLastQuestRight && boolRandom && mementoBuilding != null) {
            mementoBuilding.undo();
            Question mem = mementoBuilding.question;
            vm.setAttributQuetion(mem);
            selectedQuestion.set(mem);
            isUndo = true;
        }
        if (vm.getCptFillQuestions().get() < selectedQuestionList.size()) {
            vm.getCptFillQuestions().set(vm.getCptFillQuestions().get() + 1);
            vm.getIndexQuestion().set(vm.getIndexQuestion().get() + 1);
        }
    }

    private boolean isTheLastQuestion() {
        return vm.getIndexQuestion().get() == selectedQuestionList.size();
    }

    private boolean hasNextQuestion() {
        System.out.println("selectedQuestionList.size():  "+selectedQuestionList.size());
        System.out.println("vm.getIndexQuestion().get()"+vm.getIndexQuestion().get());
        
        return vm.getIndexQuestion().get() <= selectedQuestionList.size();
    }

    private boolean isGameOn() {
        
        System.out.println("hasNextQuestion: "+hasNextQuestion());
        return hasNextQuestion();
    }

    private void endOfGameManagmnt(Stage stage) {
        String score = getScore();
        vm.createMatch(score);
        vm.emptySelectedList();
        vm.clearOppList();
        popupEnd(score);
        stage.close();
    }

    private String getScore() {
        String score = "";
        score = analyseScore();
        return score;
    }

    private void popupEnd(String score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, score, ButtonType.FINISH);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.FINISH) {
            alert.close();
        }
    }

    private String analyseScore() {
        int score = vm.getCptPoint().get();
        String winner = "";
        if (score < (MAX_POINTS_GAME.get() / 2)) {
            winner = RESULTS.VAINQUEUR_J1.name();
        }
        if (score > (MAX_POINTS_GAME.get() / 2)) {
            winner = RESULTS.VAINQUEUR_J2.name();
        }
        if (score == (MAX_POINTS_GAME.get() / 2)) {
            winner = RESULTS.EX_AEQUO.name();
        }
        return winner;
    }

    public void incrementPoints(Question q) {
        if (hintClicked && q.getFakeHint() != null && q.getHint() != null) {
            if (q.getFakeHint().get().equals(hint.get())) {
                vm.cptPointProperty().set(vm.cptPointProperty().get() + 2);
            } else {
                vm.cptPointProperty().set(vm.cptPointProperty().get() + 1);
            }
        } else {
            vm.cptPointProperty().set(vm.cptPointProperty().get() + q.getPoints());
        }
    }

    public void giveUpGame(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Êtes-vous sûr.e de vouloir quitter la partie ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            alert.close();
            endOfGameManagmnt(stage);
        } else {
            alert.close();
        }

    }

    public BooleanProperty btnValidateQuestionProperty() {
        return btnValidateQuestion;
    }

    public void displayHint() {
        hintClicked = true;
        Question q = getQuestionFromIndex();
        hint.set(randomHint(q));
        bntHint.set(false);

    }

    public String randomHint(Question q) {
        if (randomValue()) {
            return q.getFakeHint().get();
        } else {
            return q.getHint().get();
        }
    }

    public String random(Question q) {
        if (randomValue()) {
            return q.getFakeHint().get();
        } else {
            return q.getHint().get();
        }
    }

    public boolean randomValue() {
        Random rand = new Random();
        int value = rand.nextInt(5);
        System.out.println(value);
        return value == 3;
    }

    public BooleanProperty getDisableRadioBtn() {
        return vm.getDisableRadioBtn();
    }

    public IntegerProperty getCptFillQuestions() {
        return vm.getCptFillQuestions();
    }

    public StringProperty getRes1() {
        return vm.getRes1();
    }

    public StringProperty getRes2() {
        return vm.getRes2();
    }

    public StringProperty getRes3() {
        return vm.getRes3();
    }

    public StringProperty getRes4() {
        return vm.getRes4();
    }

    public IntegerProperty getMAX_POINTS_GAME() {
        return vm.getMAX_POINTS_GAME();
    }

    public IntegerProperty cptPointProperty() {
        return vm.cptPointProperty();
    }

    public SimpleListProperty<Question> selectedQuestionProperty() {
        return vm.selectedQuestionProperty();
    }

    public IntegerProperty getIndexQuestion() {
        return vm.getIndexQuestion();
    }

    public StringProperty getQuestionName() {
        return vm.getQuestionName();
    }

    public IntegerProperty getQuestionPoint() {
        return vm.getQuestionPoint();
    }

}
