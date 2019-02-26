/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import element.Elem;
import element.Elements;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Spy
 */
public class Tournament {

    private final String name;
    private ObservableList<Player> subscribersList = FXCollections.observableArrayList();// modifier en hashList peut etre.
    private ObservableList<Match> matchList = FXCollections.observableArrayList();
    private final ObservableList<Question> questions = FXCollections.observableArrayList();

    public ObservableList<Question> getQuestions() {
        return questions;
    }

    public Tournament(String s) {
        name = s;
        fillListQuestions();
    }

    public String getName() {
        return name;
    }

    public void fillListQuestions() {
        List<Elem> listElem = Elements.loadElemsFromFile("Questions.JSON");
        for (Elem list : listElem) {
        
        }
    }
    
    

    public ObservableList<Player> getSubscribersList() {
        return subscribersList;
    }

    public ObservableList<Match> getMatchList() {
        return matchList;
    }

    public boolean existMatch(Match t) {
        return matchList.contains(t);
    }

    public void addMatch(Match m) {
        if (!this.matchList.contains(m)) {
            this.matchList.add(m);
        }
    }

    public void addPlayer(Player p) {
        this.subscribersList.add(p);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public static void main(String[] args) {
        Tournament t=new Tournament("test");
        for(Question p:t.questions){
            System.out.println(p.getName().get());
        }
        

    }
}
