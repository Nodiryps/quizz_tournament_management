/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class LVQuestions extends ListView<Question> {
 private ViewModel vm;
    public LVQuestions(ViewModel vm,RadioButton reponse1, RadioButton reponse2, RadioButton reponse3, RadioButton reponse4) {
        this.vm = vm;
        configbinding();
        configListener();
    }

    private void configListener() {
        this.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    System.out.println("this.getSelectionModel().getSelectedItem() est " +
                            this.getSelectionModel().getSelectedItem());
                    vm.setAttributQuetion(this.getSelectionModel().getSelectedItem());
                });
    }

    public void configbinding() {
        this.itemsProperty().bind(vm.quetionsProperty());
    }
    
    
}
