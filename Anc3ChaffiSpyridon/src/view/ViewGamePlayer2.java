/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import java.awt.GridBagConstraints;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
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
    private final VBox boutonVB = new VBox();
    private final VBox displayQuestion = new VBox();
    private final GridPane display = new GridPane();
    private final Label Playerdispplay = new Label();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final StringProperty displayPlayer = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
    private Label Title = new Label("Reponse a la question");
    private Label Match = new Label("le match");
    private Label cptq = new Label("le compteur/nrb de question");
    private IntegerProperty cptQ = new SimpleIntegerProperty();
    private Label pointGagner = new Label("les points");
    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    private Text attrQName = new Text("Enoncer de la Question");
    private Text attrQPoint = new Text("Point de la Question");
    private final IntegerProperty cptQuestion = new SimpleIntegerProperty();
    private Label response = new Label("Reponse");
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private IntegerProperty nextQuestion = new SimpleIntegerProperty();

    public ViewGamePlayer2(ViewModel vm, ObservableList<Question> list) {
        stage = new Stage();
        this.selectedQuestionList = list;
        this.vm = vm;
        initData();
        stage = new Stage();
        stage.setTitle("Choix de questions");
        stage.setScene(new Scene(display, 500, 800));
        stage.show();
    }

    public void initData() {
        configView();
        configRadioButton();
        configBindRadioBtn();
        configListerner();
    }

    public void configView() {
        display.add(Title, 0, 0);
        display.add(Match, 0, 1);
        display.add(cptq, 0, 5);
        displayQuestion.getChildren().addAll(attrQName, attrQPoint, response, reponse1, reponse2, reponse3, reponse4);

        display.add(displayQuestion, 0, 8);
        display.add(pointGagner, 0, 9);
        display.add(valider, 0, 10);
        display.add(annuler, 0, 11);

    }

    private void configRadioButton() {
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);
        cptq.textProperty().bind(cptQ.asString());
        vm.cptFillQuestions.bindBidirectional(cptQ);
        vm.nextQuestion.bindBidirectional(nextQuestion);
        vm.questionNameProperty().bindBidirectional(attrQName.textProperty());
        vm.questionPointProperty().bindBidirectional(attrQPoint.textProperty());

    }

    private void configBindRadioBtn() {
        reponse1.textProperty().bind(res1);
        reponse2.textProperty().bind(res2);
        reponse3.textProperty().bind(res3);
        reponse4.textProperty().bind(res4);
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);

    }

    private void configListerner() {

        valider.setOnAction((ActionEvent event) -> {

         String t=((RadioButton)group.getSelectedToggle()).getText();
            
            vm.nextQuestion(t);// on vas lui passer les infos des radiobouton et cree une methode dans la ViewModel qui seras appeller dans la methode nextQuestion.

        });
//       group.selectedToggleProperty().addListener((observable, oldVal, newVal) -> 
//               System.out.println(newVal + " was selected")
//       ); 

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle){

                if (group.getSelectedToggle() != null) {
                
                    
                }

            }
        });

  
}
      public void configQuetion() {

    }
}