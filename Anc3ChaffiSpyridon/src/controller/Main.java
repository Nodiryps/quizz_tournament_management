/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.*;
import element.Elements;
import element.Elem;

import java.util.List;

/**
 *
 * @author chaff
 */
public class Main extends Application {

    TournamentFacade facade;
    ViewModel viewmodel;
    ViewGamePlayer1 viewgame;
    ViewTournManagmt view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        facade = new TournamentFacade();
        viewmodel = new ViewModel(facade);
        view = new ViewTournManagmt(primaryStage, viewmodel);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }

}
