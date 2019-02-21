/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import model.TournamentFacade;
import view.ViewGame;
import java.io.FileNotFoundException;
import model.RESULTS;

/**
 *
 * @author 2707chshyaka
 */
public class VMInitGame {

    private ViewModel vm;
    private BooleanProperty reponse1 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse2 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse3 = new SimpleBooleanProperty(true);
    private BooleanProperty reponse4 = new SimpleBooleanProperty(true);
    private StringProperty questionName = new SimpleStringProperty();
    private IntegerProperty questionPoint = new SimpleIntegerProperty();
    private final int MAX_POINT = 10;
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    public  ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private IntegerProperty cptPoint = new SimpleIntegerProperty();
    private final ObjectProperty<Player> cb1 = new SimpleObjectProperty<>();
    private final ObjectProperty<Player> cb2 = new SimpleObjectProperty<>();
    private StringProperty cb3 = new SimpleStringProperty();
    private IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private IntegerProperty pointTotaux = new SimpleIntegerProperty();
    private final BooleanProperty boolSelectRadioBtn1 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn2 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn3 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn4 = new SimpleBooleanProperty();
      public final BooleanProperty disableRadioBtn = new SimpleBooleanProperty();
    
    //////////////////////////////
    public VMInitGame(ViewModel vm) {
        this.vm = vm;
        addPoint();
        disableRadioBtn.set(true);

    }

    public void launchPlay(Player p1, Player p2, Stage stage) throws Exception {
        if (cptPointProperty().get() == MAX_POINT) {
            launchAttributes();
            new ViewGame(this, selectedQuestionList, p1, p2, stage);
            if (cptFillQuestions.get() <= selectedQuestionList.size()) {
                afficheQuestion();
                indexQuestion.set(indexQuestion.get() + 1);
            }
            System.out.println("ksdfjlkjsdf");
            //stage.close();
        }
    }

    public void afficheQuestion() {
        if (indexQuestion.get() < 0) {
            indexQuestion.set(0);
            setAttributQuetion(selectedQuestionList.get(indexQuestion.get()));
        } else if (indexQuestion.get() < selectedQuestionList.size()) {
            setAttributQuetion(selectedQuestionList.get(indexQuestion.get()));
        }

    }

    public void setAttributQuetion(Question q) {
        if (null != q) {
            selectedQuestion.set(q);
            setBoolAllRadioBtnTrue();
            this.questionName.set(q.getName().get());
            this.questionPoint.set(q.pointsProperty().getValue());
            setReponse(q);
            this.currentQuestion.set(q);
            selectedQuestion.set(null);
        }
    }

    public IntegerProperty cptPointProperty() {
        return cptPoint;
    }

    private void launchAttributes() {
        cptPointProperty().set(0);
        cptFillQuestions.set(1);
        gameOver.set(false);
    }

    public void setReponse(Question q) {
        res1.set(q.getResponses().get(0));
        res2.set(q.getResponses().get(1));
        res3.set(q.getResponses().get(2));
        res4.set(q.getResponses().get(3));
    }

    public void clearComboBox() {
        cb1.set(new Player(""));
        cb2.set(new Player(""));
        cb3.set(" ");
    }

    private void setBoolAllRadioBtnTrue() {
        disableAllRadioBouton();
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

    private void disableAllRadioBouton() {

        boolSelectRadioBtn1.setValue(Boolean.FALSE);
        boolSelectRadioBtn2.setValue(Boolean.FALSE);
        boolSelectRadioBtn3.setValue(Boolean.FALSE);
        boolSelectRadioBtn4.setValue(Boolean.FALSE);
    }

    public void addQuestionforOpp(Question q) {
        if (cptPoint.get() + q.pointsProperty().get() <= MAX_POINT && !selectedQuestionList.contains(q)) {
            selectedQuestion.set(q);
            if (!selectedQuestionList.contains(getSelectedQuestion().get()) && getSelectedQuestion().get() != null) {
                this.selectedQuestionList.add(getSelectedQuestion().get());
                cptPoint.set(cptPoint.get() + q.pointsProperty().get());
                questionsProperty().remove(q);
                cptFillQuestions.set(selectedQuestionList.size());
            }
            pointTotaux.set(pointTotaux.get() - q.pointsProperty().get());
            setReponse(selectedQuestion.get());
        }

    }

    public void deleteQuestionForOpp(Question q) {
        if (selectedQuestionList.contains(q)) {
            selectedQuestion.set(q);
            cptPoint.set(cptPoint.get() - selectedQuestion.get().pointsProperty().get());
            this.selectedQuestionList.remove(q);
            pointTotaux.set(pointTotaux.get() + q.pointsProperty().get());
            cptFillQuestions.set(selectedQuestionList.size());
        }
    }

    public void addPoint() {
        for (Question x : questionsProperty()) {
            pointTotaux.set(pointTotaux.get() + x.pointsProperty().get());
        }
    }

    public IntegerProperty getPointTotaux() {
        return pointTotaux;
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

    public int getMAX_POINT() {
        return MAX_POINT;
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

    public BooleanProperty getGameOver() {
        return gameOver;
    }

    public IntegerProperty getCptFillQuestions() {
        return cptFillQuestions;
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

    public void emptyselectedList() {
        vm.emptyselectedList();
    }

    public void createMatch(String res) {
        vm.createMatch(res);
    }

    public IntegerProperty pointTotauxProperty() {
        return pointTotaux;
    }
    
    public void nextQuestion(String t, Stage stage) {
        System.out.println("nextQ");
        if (getIndexQuestion().get() <= selectedQuestionList.size() && !getGameOver().get()) {
            afficheQuestion();
            System.out.println("if 1");
            if (rightResponse(t)) {
                AjouterPointQuestion();
                System.out.println("if 2");
            }
            if(cptFillQuestions.get() < selectedQuestionList.size()){
                System.out.println("if 3");
              getCptFillQuestions().set(getCptFillQuestions().get() + 1);
              getIndexQuestion().set(getIndexQuestion().get() + 1);
            }

            if(getIndexQuestion().get() == selectedQuestionList.size())
                gameOver.setValue(Boolean.TRUE);
        }
        if(getGameOver().get())  {
            System.out.println("elssss");
            String score = analyseScore();
            System.out.println(score);
            createMatch(score);
            emptyselectedList();
            clearOppList();
            getGameOver().set(true);
            System.out.println("test");
            System.out.println(stage.toString());
            stage.close();
        }
    }
    
    public boolean rightResponse(String s) {
        int r = getSelectedQuestionList().get(getIndexQuestion().get() - 1).getNumCorrectResponse().get();
        String st = getSelectedQuestionList().get(getIndexQuestion().get() - 1).getResponses().get(r - 1);
        return st.equals(s);
    }
    
    public String analyseScore() {
        int score = getCptPoint().get();
        if (score < 5) {
            return RESULTS.VAINQUEUR_J1.name();
        } else if (score == 5) {
            return RESULTS.EX_AEQUO.name();
        } else {
            return RESULTS.VAINQUEUR_J2.name();
        }
    }
    
    public void AjouterPointQuestion() {
        Question q = getSelectedQuestionList().get(getIndexQuestion().get() - 1);
        cptPointProperty().set(cptPointProperty().get() + q.getPoints());
    }

}