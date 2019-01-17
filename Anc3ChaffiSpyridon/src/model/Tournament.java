/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 *
 * @author Spy
 */
public class Tournament {

    private final String name;
    private ObservableList<Player> subscribersList = FXCollections.observableArrayList();// modifier en hashSet peut etre.
    private ObservableSet<Match> matchList = FXCollections.observableSet();

    public Tournament(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public ObservableList<Player> getSubscribersList() {
        return subscribersList;
    }

    public ObservableSet<Match> getMatchList() {
        return matchList;

    }

    
    public boolean existMatch(Match t) {
        return matchList.contains(t);
    }

    public void addMatch(Match m) {
        this.matchList.add(m);
    }

    public void addPlayer(Player p) {
        this.subscribersList.add(p);

    }

    @Override
    public String toString() {
        return getName();
    }
}
