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
    Presenter presenter;
    View view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        facade = new TournamentFacade();
        presenter = new Presenter(facade);
        view = new View(primaryStage,presenter);
        presenter.setView(view);
        presenter.init_view();
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
