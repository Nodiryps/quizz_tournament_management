/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chaff
 */
public class testTournamant {

    public static List<Tournament> tournois = new ArrayList<>();

    public static Tournament t;

    public testTournamant(String name) {
        tournois.add(new Tournament(name));
        t = getMotLIst(name);

    }

    public static Tournament getMotLIst(String find) {

        Tournament value = null;
        for (int i = 0; i <= tournois.size() - 1; ++i) {
            if (tournois.get(i).getName().equals(find)) {
                value = tournois.get(i);
            }
        }
        return value;
    }
    
    public static  String afficheT(){
        return t.getName();
}

    public static void main(String[] args) {
     testTournamant p=  new testTournamant("test1");
     testTournamant p2=  new testTournamant("test2");
  

        System.out.println(getMotLIst("test1"));
        System.out.println(afficheT());
    }
}
