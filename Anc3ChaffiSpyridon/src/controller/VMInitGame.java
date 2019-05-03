/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import element.Elem;
import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.Stage;
import model.Player;
import model.Question;
import view.ViewGame;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import Memento.CareTaker;
import model.Category;
import Memento.MementoBuilding;
import model.RESULTS;

/**
 *
 * @author 2707chshyaka
 */
public final class VMInitGame {

    private ViewModel vm;
    private final int MINIMUM_POINTS = 5;
    private final int MAXIMUM_POINTS = 10;
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
    private static int cpt;
    private BooleanProperty btnHint = new SimpleBooleanProperty();
    private StringProperty hint = new SimpleStringProperty();
    private BooleanProperty btnValidateQuestion = new SimpleBooleanProperty();

    public VMInitGame(ViewModel vm) {
        this.vm = vm;
        disableRadioBtn.set(true);
        selectRadioBtn.set(false);
        cpt = 0;
        questionsProperty().clear();
        addAllQuestions();
        vm.setBtnPlayClicked(true);
    }

    public void enablebtnValidateQuestion() {
        btnValidateQuestion.set(false);
    }

    public void launchPlay(Player p1, Player p2, Stage stage) throws Exception {
        if (isPartyValid()) {
            launchAttributes();
            VMGame vmg = new VMGame(this);
            new ViewGame(vmg, selectedQuestionList, p1, p2, stage);
            if (cptFillQuestions.get() <= selectedQuestionList.size()) {
                displayTheQuestion();
                indexQuestion.set(indexQuestion.get() + 1);
            }
        }
    }

    private boolean isPartyValid() {
        return cptPointProperty().get() >= MINIMUM_POINTS;
    }

    public void displayTheQuestion() {
        disableRadioBtn.set(true);
        if (indexQuestion.get() < 0) {
            indexQuestion.set(0);
            setAttributQuetion(selectedQuestionList.get(indexQuestion.get()));
        } else if (indexQuestion.get() < selectedQuestionList.size()) {
            setAttributQuetion(selectedQuestionList.get(indexQuestion.get()));
        }
    }

    public void setAttributQuetion(Question q) {
        if (q != null) {
            btnHint.set(false);
            selectedQuestion.set(q);
            selectRightRespRadioBtn();
            this.questionName.set(q.getName().get());
            this.questionPoint.set(q.getPoints());
            setResponse(q);
            this.currentQuestion.set(q);
            selectedQuestion.set(null);
            if (q.getPoints() == 3) {
                btnHint.set(true);
            }
        }
    }

    private void launchAttributes() {
        cptPointProperty().set(0);
        cptFillQuestions.set(1);
    }

    public void setResponse(Question q) {
        if (q != null) {
            res1.set(q.getResponses().get(0));
            res2.set(q.getResponses().get(1));
            res3.set(q.getResponses().get(2));
            res4.set(q.getResponses().get(3));
        }
    }

    public void clearComboBox() {
        cb1.set(new Player(""));
        cb2.set(new Player(""));
        cb3.set(" ");
    }

    private void selectRightRespRadioBtn() {
        unselectAllRadioBouton();
        int rightResponse = selectedQuestion.get().getNumCorrectResponse().get();
        switch (rightResponse) {
            case 1:
                boolSelectRadioBtn1.setValue(Boolean.TRUE);
                break;
            case 2:
                boolSelectRadioBtn2.setValue(Boolean.TRUE);
                break;
            case 3:
                boolSelectRadioBtn3.setValue(Boolean.TRUE);
                break;
            case 4:
                boolSelectRadioBtn4.setValue(Boolean.TRUE);
                break;
        }
    }

    private void unselectAllRadioBouton() {
        boolSelectRadioBtn1.set(false);
        boolSelectRadioBtn2.set(false);
        boolSelectRadioBtn3.set(false);
        boolSelectRadioBtn4.set(false);
    }

    public void addQuestionforOpp(Question q) {
        if (q != null) {
            if (cptPoint.get() + q.getPoints() <= MAXIMUM_POINTS && !selectedQuestionList.contains(q)) {
                selectedQuestion.set(q);
                if (!selectedQuestionList.contains(getSelectedQuestion().get()) && getSelectedQuestion().get() != null) {
                    this.selectedQuestionList.add(getSelectedQuestion().get());
                    cptPoint.set(cptPoint.get() + q.getPoints());
                    MAX_POINTS_GAME.set(cptPointProperty().get());
                    questionsProperty().remove(q);
                    cptFillQuestions.set(selectedQuestionList.size());
                }
                totalPoints.set(totalPoints.get() - q.getPoints());
                setResponse(selectedQuestion.get());
            }
        }

    }

    public void deleteQuestionForOpp(Question q, Category c) {
        if (q != null) {
            if (selectedQuestionList.contains(q)) {
                selectedQuestion.set(q);
                MAX_POINTS_GAME.set(cptPointProperty().get() - q.getPoints());
                cptPoint.set(cptPoint.get() - q.getPoints());
                selectedQuestionList.remove(q);
                totalPoints.set(totalPoints.get() + q.getPoints());
                cptFillQuestions.set(selectedQuestionList.size());
                questionsProperty().clear();
                addQuestions(c);
            }
        }
    }

    public void addQuestions(Category q) {
        if (q.getName().get().equals("Tous")) {
            addAllQuestions();
        } else {
            for (Elem e : q.subElem) {
                if (e.subElems == null) {
                    questionsProperty().add(new Question(e));
                }
                removeIfSameQuestion();
                if (e.subElems != null) {
                    Category c = new Category(e);
                    addQuestions(c);
                }
            }
            pointTotauxProperty().set(0);
            addPointsToTotal();
        }
    }

    public void addAllQuestions() {
        questionsProperty().clear();
        for (Category c : getCat()) {
            if (!c.getName().get().equals("Tous")) {
                for (Elem e : c.subElem) {
                    if (e.subElems == null) {
                        removeIfSameQuestion();
                        questionsProperty().add(new Question(e));
                    }
                }
            }
        }
        pointTotauxProperty().set(0);
        addPointsToTotal();
    }

    public void addPointsToTotal() {
        for (Question q : questionsProperty()) {
            totalPoints.set(totalPoints.get() + q.getPoints());
        }
    }

    public ObservableList<Category> getCat() {
        return vm.getFacade().getCategory();
    }

    private void removeIfSameQuestion() {
        for (int x = 0; x <= selectedQuestionList.size() - 1; ++x) {
            for (int y = 0; y <= questionsProperty().size() - 1; ++y) {
                if (getQuestionName(selectedQuestionList, x).equals(getQuestionName(questionsProperty(), y))) {
                    questionsProperty().remove(questionsProperty().get(y));
                }
            }
        }
    }
    
    public void windowClosed(){
        clearComboBox();
        setBtnPlayClicked(false);
    }

    private String getQuestionName(ObservableList<Question> list, int index) {
        return list.get(index).getName().get();
    }

    public IntegerProperty cptPointProperty() {
        return cptPoint;
    }

    public BooleanProperty getDisableRadioBtn() {
        return disableRadioBtn;
    }

    public IntegerProperty getTotalPoints() {
        return totalPoints;
    }

    public BooleanProperty getBoolSelectRadioBtn1() {
        return boolSelectRadioBtn1;
    }

    public BooleanProperty getBoolSelectRadioBtn2() {
        return boolSelectRadioBtn2;
    }

    public BooleanProperty getBoolSelectRadioBtn3() {
        return boolSelectRadioBtn3;
    }

    public BooleanProperty getBoolSelectRadioBtn4() {
        return boolSelectRadioBtn4;
    }
    
    void setBoolSelectRadioBtn1(boolean bool) {
        boolSelectRadioBtn1.set(bool);
    }
    
    void setBoolSelectRadioBtn2(boolean bool) {
        boolSelectRadioBtn2.set(bool);
    }
    
    void setBoolSelectRadioBtn3(boolean bool) {
        boolSelectRadioBtn3.set(bool);
    }
    
    void setBoolSelectRadioBtn4(boolean bool) {
        boolSelectRadioBtn4.set(bool);
    }

    public BooleanProperty getReponse1() {
        return reponse1;
    }

    public BooleanProperty getReponse2() {
        return reponse2;
    }

    public BooleanProperty getReponse3() {
        return reponse3;
    }

    public BooleanProperty getReponse4() {
        return reponse4;
    }

    public StringProperty getQuestionName() {
        return questionName;
    }

    public IntegerProperty getQuestionPoint() {
        return questionPoint;
    }

    public int getMINIMUM_POINTS() {
        return MINIMUM_POINTS;
    }

    public int getMAXIMUM_POINTS() {
        return MAXIMUM_POINTS;
    }

    public static IntegerProperty getMAX_POINTS_GAME() {
        return MAX_POINTS_GAME;
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

    public SimpleListProperty<Question> getSelectedQuestionList() {
        return new SimpleListProperty<>(selectedQuestionList);
    }

    public ObjectProperty<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public ObjectProperty<Question> getCurrentQuestion() {
        return currentQuestion;
    }

    public IntegerProperty getCptPoint() {
        return cptPoint;
    }

    public ObjectProperty<Player> getCb1() {
        return cb1;
    }

    public ObjectProperty<Player> getCb2() {
        return cb2;
    }

    public StringProperty getCb3() {
        return cb3;
    }

    public IntegerProperty getIndexQuestion() {
        return indexQuestion;
    }

    public ViewModel getVm() {
        return vm;
    }

    public SimpleListProperty<Question> questionsProperty() {
        return vm.quetionsProperty();
    }

    public SimpleListProperty<Question> selectedQuestionProperty() {
        return new SimpleListProperty<>(selectedQuestionList);
    }

    public void clearOppList() {
        vm.clearOppList();
    }

    public void emptySelectedList() {
        vm.emptyselectedList();
        vm.setBtnPlayClicked(false);
    }

    public void createMatch(String res) {
        vm.createMatch(res);
    }

    public IntegerProperty pointTotauxProperty() {
        return totalPoints;
    }

    public SimpleListProperty<Category> getCategory() {
        return new SimpleListProperty<>(vm.facade.getCategory());
    }

    public void setCategory(Category q) {
        vm.facade.getQuestions().clear();
        addQuestions(q);
    }

    public IntegerProperty getCptFillQuestions() {
        return cptFillQuestions;
    }

    public BooleanProperty getBtnHint() {
        return btnHint;
    }

    public StringProperty getHint() {
        return hint;
    }

    public static int getCpt() {
        return cpt;
    }
    
    BooleanProperty getBtnPlayClicked(){
        return vm.getBtnPlayClicked();
    }
    
    void setBtnPlayClicked(boolean b){
        vm.setBtnPlayClicked(b);
    }
}
