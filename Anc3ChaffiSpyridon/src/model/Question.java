/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import element.Elements;
import element.Elem;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 2707chshyaka
 */
public class Question {

    private final StringProperty name;
    private final IntegerProperty numCorrectResponse;
    private final IntegerProperty points;
    private final ObservableList<String> responses;

    public Question(Elem elem) {
        this.name = new SimpleStringProperty(elem.name);
        this.numCorrectResponse = new SimpleIntegerProperty(elem.numCorrectResponse);
        this.points = new SimpleIntegerProperty(elem.points);
        this.responses = FXCollections.observableArrayList(elem.responses);
    }

    public StringProperty getName() {
        return name;
    }

    public IntegerProperty getNumCorrectResponse() {
        return numCorrectResponse;
    }

    public IntegerProperty getPoints() {
        return points;
    }

    public ObservableList<String> getResponses() {
        return responses;
    }
    
    @Override
    public String toString(){
      return this.getName().get();
    }

   
}
