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

/**
 *
 * @author chaff
 */
public class Main extends Application {

    TournamentFacade facade;
    Controller ctrl;
    View view;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        facade = new TournamentFacade();
        ctrl = new Controller(facade);
        view = new View(primaryStage,ctrl);
        facade.addObserver(view);
        facade.notif(TournamentFacade.TypeNotif.INIT);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
