/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import element.Elem;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 2707chshyaka
 */
public class Category implements Composant{
    
    private final StringProperty name;
    private final IntegerProperty numCorrectResponse;
    private final IntegerProperty points;
    private  ObservableList<String> responses;
    public  ObservableList<Elem> subElem;
    
    public Category(Elem elem) {
        this.name = new SimpleStringProperty(elem.name);
        this.numCorrectResponse = new SimpleIntegerProperty(elem.numCorrectResponse);
        this.points = new SimpleIntegerProperty(elem.points);
        if(elem.responses != null){
        this.responses = FXCollections.observableArrayList(elem.responses);
        }
        this.subElem=FXCollections.observableArrayList(elem.subElems);
    }

    Category() {
        this.name=new SimpleStringProperty("Tous");
    this.numCorrectResponse=null;
    this.points=null;
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

    @Override
    public ObservableList<String> getResponses() {
        return responses;
    }
    
    @Override
    public String toString(){
      return this.getName().get();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

   

}
