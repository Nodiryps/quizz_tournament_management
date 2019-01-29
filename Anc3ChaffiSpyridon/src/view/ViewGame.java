/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;

/**
 *
 * @author 2707chshyaka
 */
public class ViewGame extends VBox {

       private final ListView<Player> subsList = new ListView<>();
       ViewModel vm;
       Stage stage;
       
    
    public ViewGame(Stage stage,ViewModel facade) throws Exception {
        this.vm = facade;
        this.stage = stage;
      subsList.itemsProperty().bind(vm.QuetionsProperty());
       
        Scene scene = new Scene(subsList, 1235, 500);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Gestion de  Tournois");
        stage.setScene(scene);
    }

   
  

}
