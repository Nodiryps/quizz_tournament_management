/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;

/**
 *
 * @author chaff
 */
public class PopUpview extends VBox implements Observer {

    private final Label message = new Label("Etez-vous sûre de vouloir supprimer");

    private final Text matchToDelete = new Text("le Match a supprimer");
    Match match;
    private VBox PopView = new VBox();
    private final HBox Top = new HBox();
    private final HBox Middle = new HBox();
    private final HBox Bottom = new HBox();
    private final Button btnValider = new Button("Valider");
    private final Button btnCancel = new Button("Annuler");
    private final GridPane grid = new GridPane();

    
    
   
    public PopUpview(Stage primaryStage) throws Exception {
        initData();
        Scene scene = new Scene(PopView, 400, 300);
        primaryStage.setTitle("Attention");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initData() {
        message.setTextAlignment(TextAlignment.CENTER);
        message.setTextFill(Color.DARKCYAN);
        message.setFont(new Font("Arial", 20));
        Top.setAlignment(Pos.CENTER);
        Top.setPadding(new Insets(10, 0, 0, 10));
        Top.setSpacing(50);
        Top.getChildren().addAll(message);
        Middle.getChildren().addAll(matchToDelete);
        Middle.setPadding(new Insets(10, 0, 0, 10));

        grid.add(btnValider,1,1);
        grid.add(btnCancel,3,1);
        //Bottom.getChildren().addAll(btnValider, btnCancel);
        PopView.getChildren().addAll(Top, Middle, grid);

    }

   

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
