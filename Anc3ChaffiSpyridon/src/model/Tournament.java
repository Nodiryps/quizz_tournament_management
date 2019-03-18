/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import element.Elem;
import element.Elements;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
    private final ObservableList<Category> cat = FXCollections.observableArrayList();
    private final ObservableList<Composant> composant = FXCollections.observableArrayList();

    public ObservableList<Question> getQuestions() {
        return questions;
    }

    public ObservableList<Category> getCat() {
        return cat;
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
        cat.add(new Category());
        for (Elem e : listElem) {
            if (e.subElems != null) {
                Composant c = new Category(e);
                composant.add(c);
                Category cate=(Category)c;
                cat.add(cate);
                addCategory(c);
//           
            }

        }
        System.out.println(composant);
    }

    public void addCategory(Composant c) {
        if (c instanceof Category) {
            Category cats = (Category) c;
            for (Elem e :  cats.subElem) {
                if (e.subElems != null) {
                    cat.add(new Category(e));
                    addCategory(new Category(e));
                }
            }
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

}
