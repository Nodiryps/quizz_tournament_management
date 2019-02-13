/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import model.Question;

/**
 *
 * @author Spy
 */
public class ViewGamePlayer2 extends VBox {

    Stage stage;
    ViewModel vm;
    private final VBox vbox = new VBox();

    private final VBox displayQuestion = new VBox();

    private final Label Playerdispplay = new Label();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final StringProperty displayPlayer = new SimpleStringProperty();
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();

    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    private Text attrQName = new Text();
    private Text attrQPoint = new Text();
    private final IntegerProperty cptQuestion=new SimpleIntegerProperty();
    

    public ViewGamePlayer2(ViewModel vm) {
        stage = new Stage();
        this.vm=vm;

        stage = new Stage();
        stage.setTitle("Choix de questions");
        stage.setScene(new Scene(vbox, 1145, 500));
        stage.show();
      
    }
    
    public void initData(){
    
    }
    
    public void configView(){
    
    }

}
