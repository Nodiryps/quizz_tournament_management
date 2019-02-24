/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VMInitGame;
import controller.ViewModel;
import javafx.beans.Observable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class LVQuestions extends ListView<Question> {

    private VMInitGame vm;

    public LVQuestions(VMInitGame vm, RadioButton reponse1, RadioButton reponse2, RadioButton reponse3, RadioButton reponse4) {
        this.vm = vm;
        configbinding();
        configListener();
    }

    private void configListener() {
        this.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    vm.setAttributQuetion(this.getSelectionModel().getSelectedItem());
                });
    }

    private void configbinding() {
        this.itemsProperty().bind(vm.questionsProperty());
    }

}
