/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.stage.Stage;
import model.Question;
import model.RESULTS;

/**
 *
 * @author 2707chshyaka
 */
public class VMGame extends VMInitGame{

    public VMGame(ViewModel vm) {
        super(vm);
    }
    
    public void nextQuestion(String t, Stage stage) {
        if (getIndexQuestion().get() <= getSelectedQuestionList().size() && !getGameOver().get()) {
            afficheQuestion();
            if (rightResponse(t)) {
                AjouterPointQuestion();
            }
            getCptFillQuestions().set(getCptFillQuestions().get() + 1);
            getIndexQuestion().set(getIndexQuestion().get() + 1);
//            deselectedRadioButon.set(true);
        }
        if (getIndexQuestion().get() > getSelectedQuestionList().size()) {
            String score = analyseScore();
            createMatch(score);
            emptyselectedList();
            clearOppList();
            getGameOver().set(true);
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
