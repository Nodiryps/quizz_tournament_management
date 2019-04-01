/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 2208sptheodorou
 */
public abstract class UndoableQuestion extends Question{
    
    public UndoableQuestion(Question q) {
        super(q);
    }
    
    public abstract void undo();
}
