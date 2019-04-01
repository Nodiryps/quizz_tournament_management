/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import element.Elem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 2707chshyaka
 */
public class Question implements Composant{

    private final StringProperty fakeHint;
    private final StringProperty hint;
    private final StringProperty name;
    private final IntegerProperty numCorrectResponse;
    private final IntegerProperty points;
    private  ObservableList<String> responses;
    public final ObservableList<Elem> subElem=null;

    public Question(Elem elem) {
        this.name = new SimpleStringProperty(elem.name);
        this.numCorrectResponse = new SimpleIntegerProperty(elem.numCorrectResponse);
        this.points = new SimpleIntegerProperty(elem.points);
        if(elem.responses != null){
        this.responses = FXCollections.observableArrayList(elem.responses);
        }
        this.fakeHint=new SimpleStringProperty(elem.fakeHint);
        this.hint=new SimpleStringProperty(elem.hint);
    }
    
    public Question(Question q) {
        name = q.name;
        numCorrectResponse = q.numCorrectResponse;
        points = q.points;
        responses = q.responses;
        fakeHint = q.fakeHint;
        hint = q.hint;
    }

    @Override
    public StringProperty getName() {
        return name;
    }

    @Override
    public IntegerProperty getNumCorrectResponse() {
        return numCorrectResponse;
    }

    @Override
    public int getPoints() {
        return points.get();
    }
    
    @Override
    public IntegerProperty pointsProperty() {
        return points;
    }

    /**
     *
     * @return
     */
    @Override
    public ObservableList<String> getResponses() {
        return responses;
    }
    
    @Override
    public String toString(){
      return this.getName().get();
    }
    
    public StringProperty getFakeHint() {
        return fakeHint;
    }

    public StringProperty getHint() {
        return hint;
    }
}
