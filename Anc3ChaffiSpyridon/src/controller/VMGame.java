/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private final BooleanProperty disableRadioBtn = new SimpleBooleanProperty();
    private final BooleanProperty selectRadioBtn = new SimpleBooleanProperty();
    private int totalPointsRestant;
    private int cpt;
    private static CareTaker careTaker;
    private MementoBuilding mementoBuilding;
    private boolean boolLastQuestRight = false;
    private boolean boolRandom = false;
    private boolean isUndo = false;
    private BooleanProperty btnHint;
    private StringProperty hint;
    private boolean hintClicked;
    private BooleanProperty btnValidateQuestion = new SimpleBooleanProperty();
    private IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private StringProperty questionName = new SimpleStringProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private BooleanProperty boolSelectRadioBtn1;
    private BooleanProperty boolSelectRadioBtn2;
    private BooleanProperty boolSelectRadioBtn3;
    private BooleanProperty boolSelectRadioBtn4;
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();

    public VMGame(VMInitGame vm) {
        this.vm = vm;
        initData();
    }
    
    private void initData() {
        selectedQuestionList = vm.getSelectedQuestionList();
        careTaker = new CareTaker();
        cpt = VMInitGame.getCpt();
        disablebtnValidateQuestion();
        initBoolSelectRadioBtn();
        initHint();
    }
    
    private void initHint(){
        btnHint = vm.getBtnHint();
        hint = vm.getHint();
        hintClicked = false;
    }
    
    private void initBoolSelectRadioBtn(){
        boolSelectRadioBtn1 = vm.getBoolSelectRadioBtn1();
        boolSelectRadioBtn2 = vm.getBoolSelectRadioBtn2();
        boolSelectRadioBtn3 = vm.getBoolSelectRadioBtn3();
        boolSelectRadioBtn4 = vm.getBoolSelectRadioBtn4();
    }

    private void disablebtnValidateQuestion() {
        btnValidateQuestion.set(true);
    }
    
    private void selectFalseRespRadioBtn(String res) {
        switch (getIndexWrongResponse(res)) {
            case 0:
                boolSelectRadioBtn1.setValue(Boolean.TRUE);
                break;
            case 1:
                boolSelectRadioBtn2.setValue(Boolean.TRUE);
                break;
            case 2:
                boolSelectRadioBtn3.setValue(Boolean.TRUE);
                break;
            case 3:
                boolSelectRadioBtn4.setValue(Boolean.TRUE);
                break;
        }
    }

    private int getIndexWrongResponse(String res) {
        int wrongRes = 0;
        if (selectedQuestion.get() != null) {
            for (String r : selectedQuestion.get().getResponses()) {
                if (res.equals(r)) {
                    wrongRes = selectedQuestion.get().getResponses().indexOf(r);
                }
            }
        }
        return wrongRes;
    }

    public void nextQuestion(String response, Stage stage, ToggleGroup g) {
        if (stage != null && g != null) {
            System.out.println("points restants: " + totalPointsRestant);
            g.selectToggle(null);
            disablebtnValidateQuestion();
            randMemento();
            if (hasNextQuestion()) {
                vm.displayTheQuestion();
                nextQuestionManagmnt(response);
                ++cpt;
                if (isTheLastQuestion()) 
                    lastQuestion(response);
            }
            if (isTheEnd()) 
                endOfGameManagmnt(stage);
        }
    }
    
    private void randMemento(){
        if (!isTheLastQuestion() || indexQuestion.get() > 0) 
            boolRandom = randomValue();
    }
    
    private boolean isTheEnd(){
        return (noMoreQuestion() || noMorePoints()) && !boolRandom;
    }

    private void nextQuestionManagmnt(String response) {
//        System.out.println("nextQuestionManagmnt 1 + undo: " + isUndo);
        Question q = getQuestionFromIndex();
        totalPointsRestant -= q.getPoints();
        if (!isUndo) {
//                System.out.println("nextQuestionManagmnt 2 + undo: " + isUndo);
            if (isResponseRight(response)) {
                boolLastQuestRight = true;
                incrementPoints(q);
            } else {
                mementoBuilding = new MementoBuilding(q, response, careTaker);
            }
            incrementQuestion();
        } else {
//                System.out.println("nextQuestionManagmnt 3 + undo: " + isUndo);
            if (isRightRespUndo(response)) {

                boolLastQuestRight = true;
                incrementPoints(mementoBuilding.question);
//                    System.out.println("nextQuestionManagmnt 4");
                getIndexQuestion().set(cpt + 1);
                Question p = vm.getSelectedQuestionList().get(cpt);
                vm.setAttributQuetion(p);
                incrementQuestion();
            }
        }
        disableRadioBtn.set(true);
        hint.set("");
    }

    public void enablebtnValidateQuestion() {
        btnValidateQuestion.set(false);
    }

    private boolean noMoreQuestion() {
        return cpt > selectedQuestionList.size();
    }

    private boolean noMorePoints() {
        return totalPointsRestant + vm.cptPointProperty().get() < (vm.getMAX_POINTS_GAME().get() / 2);
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
        int indexResp = -1;
        indexResp = q.getResponses().indexOf(res);
        System.out.println("indexResp: " + indexResp);
        return q.getNumCorrectResponse().get() == indexResp;
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
        System.out.println("hasnextQ list size:  " + selectedQuestionList.size());
        System.out.println("hasnextQ indexQ: " + vm.getIndexQuestion().get());

        return vm.getIndexQuestion().get() <= selectedQuestionList.size();
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
        if (score < (vm.getMAX_POINTS_GAME().get() / 2)) {
            winner = RESULTS.VAINQUEUR_J1.name();
        }
        if (score > (vm.getMAX_POINTS_GAME().get() / 2)) {
            winner = RESULTS.VAINQUEUR_J2.name();
        }
        if (score == (vm.getMAX_POINTS_GAME().get() / 2)) {
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
        btnHint.set(false);

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
        System.out.println("rand: " + value);
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

    public BooleanProperty getBtnHint() {
        return btnHint;
    }

    public StringProperty getHint() {
        return hint;
    }
    
}
