/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author 2208sptheodorou
 */
public class CareTaker {

    private  ArrayList<Memento> mementosList = new ArrayList<>();
    
    public CareTaker() {
        emptyList();
    }

    public void keepMemento(Memento m) {
        mementosList.add(m);
    }

    public Memento getMemento() {
        Memento m = null;
        if (mementosList.size() > 0) {
            m = mementosList.get(0);
           // mementosList.remove(0);
            System.out.println("Getmement(CareTaker) "+m);
        }
        return m;
    }
    
    public ArrayList<Memento> getMemeto(){
     return mementosList;
    }
    
    private void emptyList(){
   mementosList.clear();
     
     
    }
}

