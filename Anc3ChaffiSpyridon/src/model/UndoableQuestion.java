/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import element.Elem;

/**
 *
 * @author 2208sptheodorou
 */
public abstract class UndoableQuestion extends Question{

    public UndoableQuestion(Question elem) {
        super(elem);
    }
  
    public abstract void undo();
}
