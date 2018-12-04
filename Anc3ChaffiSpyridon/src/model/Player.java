/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;


/**
 *
 * @author Spy
 */
public class Player {
    private final String firstName;
    
    public Player(){
        this("");
    }
    
    public Player(String s){
        firstName = s;
    }
    
    public String getFirstName(){
        return firstName;
    }

    @Override
    public String toString() {
        return firstName;
    }
    
  
    //ajouter un Equals et un hashcode.

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
     if( obj instanceof Player){
         Player p=(Player)obj;
         return this.getFirstName().equals(((Player) obj).getFirstName());
     }
     return false;
    }
    
    
}
