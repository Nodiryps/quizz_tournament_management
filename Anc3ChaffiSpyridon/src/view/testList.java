/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chaff
 */
public class testList {

    List<String> list = new ArrayList<>();

    public testList() {
        list.add("poule");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");
        list.add("chien");

    }

    public String getList() {
        String res = "";
        for (String s : list) {
            res += " " + s;
        }
        return res;
    }

    public List<String> getListe() {
        return list;
    }
}
