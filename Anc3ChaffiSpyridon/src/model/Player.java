/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Spy
 */
public class Player {

    private final StringProperty firstName=new SimpleStringProperty();

    public Player() {
        this("");
    }

    public Player(String s) {
        firstName.set(s);
    }

    public StringProperty getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return firstName.get();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return this.getFirstName().equals(((Player) obj).getFirstName());
        }
        return false;
    }

}
