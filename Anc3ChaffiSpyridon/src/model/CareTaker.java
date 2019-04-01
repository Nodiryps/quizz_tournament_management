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

    private ArrayList<Memento> mementosList = new ArrayList<>();

    public CareTaker(Memento m) {
        mementosList.add(m);
    }

    public void keepMemento(Memento m) {
        mementosList.add(m);
    }

    public Memento getMemento() {
        Memento m = null;
        System.out.println("CareTAkerLIst "+ mementosList.size());
        System.out.println(mementosList);
        if (mementosList.size() > 0) {
            m = mementosList.get(0);
            mementosList.remove(0);
            System.out.println("je suis dans le if");
        }
        return m;
    }
}
