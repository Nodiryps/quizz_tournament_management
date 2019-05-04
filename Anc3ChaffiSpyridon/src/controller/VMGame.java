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
import Memento.CareTaker;
import Memento.Memento;
import javafx.event.EventHandler;
import javafx.scene.control.DialogEvent;
import javafx.stage.WindowEvent;
import model.Question;
import model.RESULTS;

/**
 *
 * @author 2707chshyaka
 */
public class VMGame {

    private final int POINT_FAKE_HINT = 2;
    private final int POINT_HINT = 1;
    private VMInitGame vm;
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private final BooleanProperty disableRadioBtn = new SimpleBooleanProperty();
    private final BooleanProperty selectRadioBtn = new SimpleBooleanProperty();
    private int pointsLeft;
    private int cpt;
    private boolean boolPreviousQuestRight = false;
    private boolean boolRandomMem = false;
    private boolean isUndo = false;
    private BooleanProperty btnHint;
    private StringProperty hint;
    private boolean hintClicked;
    private BooleanProperty btnValidateQuestion = new SimpleBooleanProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private Question mementoQuestion;
    private int mementoRespIndex;
    private CareTaker careTaker;

    private static class MementoImpl implements Memento {

        Question question;
        int response;

        MementoImpl(Question q, int r) {
            question = new Question(q);
            response = r;
        }

        @Override
        public String toString() {
            return "MementoImpl{question=" + question + "\n\t\tresponse=" + response + '}';
        }
    }

    private void undo() {
        setMemento(careTaker.getMemento());
    }

    private void setMemento(Memento m) {
        MementoImpl memImpl = (MementoImpl) m;
        mementoQuestion = memImpl.question;
        mementoRespIndex = memImpl.response;
    }

    private MementoImpl createMemento(String resp) {
        return new MementoImpl(mementoQuestion, mementoQuestion.getResponses().indexOf(resp));
    }

    private boolean isEmptyMementoList() {
        return careTaker.getMemeto().size() == 0;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public VMGame(VMInitGame vm) {
        this.vm = vm;

        initData();
    }

    private void initData() {
        vm.setBtnPlayClicked(true);
        selectedQuestionList = vm.getSelectedQuestionList();
        careTaker = new CareTaker();
        mementoQuestion = null;
        cpt = VMInitGame.getCpt();
        initPointsLeft();
        vm.getIndexQuestion().set(0);
        disablebtnValidateQuestion();
        initHint();
    }

    private void initPointsLeft() {
        pointsLeft = getMAX_POINTS_GAME().get();
    }

    private void initHint() {
        btnHint = vm.getBtnHint();
        hint = vm.getHint();
        hintClicked = false;
    }

    private void disablebtnValidateQuestion() {
        btnValidateQuestion.set(true);
    }

    public void nextQuestion(String response, Stage stage, ToggleGroup g) {
        if (stage != null && g != null) {
            g.selectToggle(null);
            disablebtnValidateQuestion();
            randMemento();
            if (hasNextQuestion()) {
                vm.displayTheQuestion();
                if (isUndo) {
                    selectFalseRespRadioBtn();
                }
                nextQuestionManagmnt(response);
                if (!isUndo) {
                    ++cpt;
                }
            }

            if (isTheLastQuestion()) {
                lastQuestion(response);
            }
            if (isTheEnd()) {
                endOfGameManagmnt(stage);
            }
        }
    }

    private void randMemento() {
        if (getIndexQuestion().get() > 0) {
            if (!isTheLastQuestion()) {
                boolRandomMem = randomValueBool();
                boolRandomMem = true;
            }
        }
    }

    private void nextQuestionManagmnt(String response) {
        Question q = getQuestionFromIndex();
        int indexOfActualQuestion = vm.getSelectedQuestionList().get().indexOf(q);
        if (!isUndo) {
            if (isResponseRight(response)) {
                boolPreviousQuestRight = true;
                incrementPoints(q);
            } else {
                decrementPointsLeft(q.getPoints());
                mementoQuestion = q;
                mementoRespIndex = q.getResponses().indexOf(response);
                careTaker.keepMemento(createMemento(response));
                boolPreviousQuestRight = false;

            }
            incrementQuestion();
            disableRadioBtn.set(true);
            hint.set("");
            hintClicked = false;
        } else {

            if (isResponseRightUndo(response)) {
                boolPreviousQuestRight = true;
                incrementPoints(mementoQuestion);
                isUndo = false;
                mementoQuestion = null;

            } else {
                careTaker.keepMemento(createMemento(response));
                boolPreviousQuestRight = false;
            }
            incrementQuestion();

        }

    }

    public void enablebtnValidateQuestion() {
        btnValidateQuestion.set(false);
    }

    private boolean isTheEnd() {
        return alreadyWon() || ((noMorePoints() && !boolRandomMem) || noMoreQuestion());
    }

    private boolean alreadyWon() {
        return cptPointProperty().get() > (getMAX_POINTS_GAME().get() / 2);
    }

    private boolean noMoreQuestion() {
        return cpt > selectedQuestionList.size();
    }

    private boolean noMorePoints() {
        return (pointsLeft + cptPointProperty().get()) < (getMAX_POINTS_GAME().get() / 2);
    }

    private void lastQuestion(String response) {
        if (isResponseRight(response)) {
            incrementPoints(getQuestionFromIndex());
        }
        ++cpt;
    }

    private void selectFalseRespRadioBtn() {
        switch (mementoRespIndex) {
            case 0:
                vm.setBoolSelectRadioBtn1(true);
                break;
            case 1:
                vm.setBoolSelectRadioBtn2(true);
                break;
            case 2:
                vm.setBoolSelectRadioBtn3(true);
                break;
            case 3:
                vm.setBoolSelectRadioBtn4(true);
                break;
        }
    }

    private boolean isResponseRightUndo(String res) {
        int indexCorrectResp = mementoQuestion.getNumCorrectResponse().get();
        int indexResp = mementoQuestion.getResponses().indexOf(res);

        return indexCorrectResp == indexResp + 1;
    }

    private boolean isResponseRight(String s) {
        String resp = getResponseFromIndex(getRightResponseIndex());
        return resp.equals(s);
    }

    private int getRightResponseIndex() {
        return vm.getSelectedQuestionList().get(getIndexQuestion().get() - 1).getNumCorrectResponse().get();
    }

    private String getResponseFromIndex(int rightRespIndex) {
        return vm.getSelectedQuestionList().get(getIndexQuestion().get() - 1).getResponses().get(rightRespIndex - 1);
    }

    private Question getQuestionFromIndex() {
        return vm.getSelectedQuestionList().get(getIndexQuestion().get() - 1);
    }

    private void incrementQuestion() {

        if (boolPreviousQuestRight && boolRandomMem && check() && !isEmptyMementoList()) {
            undo();
            vm.setAttributQuetion(mementoQuestion);
            vm.getSelectedQuestion().set(mementoQuestion);
            isUndo = true;

        } else if (vm.getCptFillQuestions().get() < selectedQuestionList.size()) {
            vm.getCptFillQuestions().set(vm.getCptFillQuestions().get() + 1);
            getIndexQuestion().set(getIndexQuestion().get() + 1);
        }
    }

    private boolean check() {
        return mementoQuestion != null;
    }

    private boolean isTheLastQuestion() {
        return getIndexQuestion().get() == selectedQuestionList.size();
    }

    private boolean hasNextQuestion() {
        return getIndexQuestion().get() < selectedQuestionList.size();
    }

    private void endOfGameManagmnt(Stage stage) {
        String score = getScore();
        vm.createMatch(score);
        vm.emptySelectedList();
        vm.clearOppList();
        popupEnd(score);
        stage.close();
        vm.setBtnPlayClicked(false);
    }

    private String getScore() {
        String score = "";
        score = analyseScore();
        return score;
    }

    private void popupEnd(String score) {
        String msg = msgPopupEnd(score);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.FINISH);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.FINISH) {
            alert.close();
        }
    }

    private String msgPopupEnd(String score) {
        String msg = "";
        if (score.equals(RESULTS.EX_AEQUO.toString())) {
            msg = "Match nul... ¯\\_(ツ)_/¯";
        } else if (score.equals(RESULTS.VAINQUEUR_J1.toString())) {
            msg = "Vous avez perdu... :/";
        } else {
            msg = "!!! VOUS AVEZ GAGNÉÉÉÉÉÉÉÉ !!!";
        }
        return msg;
    }

    private String analyseScore() {
        int score = vm.getCptPoint().get();
        String winner = "";
        if (score < (getMAX_POINTS_GAME().get() / 2)) {
            winner = RESULTS.VAINQUEUR_J1.name();
        }
        if (score > (getMAX_POINTS_GAME().get() / 2)) {
            winner = RESULTS.VAINQUEUR_J2.name();
        }
        if (score == (getMAX_POINTS_GAME().get() / 2)) {
            winner = RESULTS.EX_AEQUO.name();
        }
        return winner;
    }

    public void incrementPoints(Question q) {
        if (hintClicked && isHintNotEmpty(q)) {
            if (q.getFakeHint().get().equals(hint.get())) {
                cptPointProperty().set(cptPointProperty().get() + POINT_FAKE_HINT);
                decrementPointsLeft(2);
            } else {
                cptPointProperty().set(cptPointProperty().get() + POINT_HINT);
                decrementPointsLeft(1);
            }
        } else {
            cptPointProperty().set(cptPointProperty().get() + q.getPoints());
            decrementPointsLeft(q.getPoints());
        }
    }

    public void decrementPointsLeft(int x) {
        pointsLeft -= x;
    }

    private boolean isHintNotEmpty(Question q) {
        return q.getHint() != null || q.getFakeHint() != null
                && q.getFakeHint().get().equals("") && q.getHint().get().equals("");
    }

    public void giveUpGame(Stage stage, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
      
        if (alert.getResult() == ButtonType.YES) {
            cptPointProperty().set(0);
            alert.close();
            endOfGameManagmnt(stage);
        } if (alert.getResult() == ButtonType.NO) {
          
        }
    }

    public BooleanProperty btnValidateQuestionProperty() {
        return btnValidateQuestion;
    }

    public void displayHint() {
        Question q = null;
        if (isUndo) {
            q = mementoQuestion;
        } else {
            q = getQuestionFromIndex();
        }
        hintClicked = true;
        hint.set(randomHint(q));
        btnHint.set(false);

    }

    public String randomHint(Question q) {
        if (randomValueBool()) {
            return q.getFakeHint().get();
        } else {
            return q.getHint().get();
        }
    }

    public boolean randomValueBool() {
        Random rand = new Random();
        int value = rand.nextInt(5);
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
        return VMInitGame.getMAX_POINTS_GAME();
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

    public int getPointLeft() {
        return pointsLeft;
    }
}
