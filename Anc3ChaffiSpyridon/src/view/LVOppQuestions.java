/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VMInitGame;
import controller.ViewModel;
import java.io.FileNotFoundException;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class LVOppQuestions extends ListView<Question> {

    private VMInitGame vm;

    public LVOppQuestions(VMInitGame vm) {
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

    public void configbinding() {
        this.itemsProperty().bind(vm.selectedQuestionProperty());
    }

}
