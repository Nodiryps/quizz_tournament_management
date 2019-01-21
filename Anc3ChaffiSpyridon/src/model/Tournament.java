/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;

/**
 *
 * @author Spy
 */
public class Tournament {

    private final String name;
    private ObservableList<Player> subscribersList = FXCollections.observableArrayList();// modifier en hashList peut etre.
    private ObservableList<Match> matchList = FXCollections.observableArrayList();

    public Tournament(String s) {
        name = s;
    }

    public String getName() {
        return name;
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
        if(!matchList.contains(m)){
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
