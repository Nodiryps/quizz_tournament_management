/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author 2707chshyaka
 */
public interface Composant {
 
    StringProperty getName();
     IntegerProperty getNumCorrectResponse();
     int getPoints();
     IntegerProperty pointsProperty();
     ObservableList<String> getResponses() ;
}
