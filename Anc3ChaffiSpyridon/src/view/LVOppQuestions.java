/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class LVOppQuestions extends ListView<Question> {
    private ViewModel vm;
    public LVOppQuestions(ViewModel vm) {
        this.vm=vm;
         configbinding();
        configListener();
        
    }
    
    private void configListener() {
          this.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    
                });
    }

    public void configbinding() {
        this.itemsProperty().bind(vm.selectedQuestionProperty());
    }
    
    
}
