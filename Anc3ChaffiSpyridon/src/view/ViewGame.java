/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class ViewGame extends GridPane {

    private final ListView<Question> subsList = new ListView<>();
    private final ListView<Question> fillQuestion = new ListView<>();
    private final BorderPane grid = new BorderPane();
    private final VBox middleVbox = new VBox();
    private final GridPane detailsQuestion = new GridPane();
    private Text attrQName = new Text("Enoncer de la Questions");
    private Text attrQPoint = new Text("Point de la Question");
    private Label response = new Label("Reponse");
    private Text dispoPoints = new Text("Point disponible");
    private Text questionPoints = new Text("Points Questionnaires");
    private Button addQuestion = new Button("=>");
    private Button delQuestion = new Button("<=");
    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    private final GridPane gpButtons = new GridPane();//gere les boutons

    ViewModel vm;
    Stage stage;

    public ViewGame(Stage stage, ViewModel facade) throws Exception {
        this.vm = facade;
        this.stage = stage;

        initGrid();
        configBinding();
        configListener();
        Scene scene = new Scene(grid, 1235, 500);
        stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Choix de questions");
        stage.setScene(scene);
    }

    public void initGrid() {
//        grid.set(50);

        grid.setLeft(subsList);
        grid.setCenter(middleVbox);
        grid.setRight(fillQuestion);
        grid.setPadding(new Insets(25));
        detailsQuestion.add(attrQName, 0, 0);
        detailsQuestion.add(attrQPoint, 0, 1);
        detailsQuestion.add(response, 0, 2);
        middleVbox.getChildren().addAll(detailsQuestion, gpButtons);
        middleVbox.setPadding(new Insets(0, 50, 0, 50));
        middleVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.gpButtons.add(addQuestion, 0, 0);
        gpButtons.add(delQuestion, 1, 0);
        gpButtons.add(valider, 0, 1);
        gpButtons.add(annuler, 1, 1);

    }

    public void configBinding() {
        subsList.itemsProperty().bind(vm.quetionsProperty());
        vm.questionNameProperty().bindBidirectional(attrQName.textProperty());
        vm.questionPointProperty().bindBidirectional(attrQPoint.textProperty());
        fillQuestion.itemsProperty().bind(vm.selectedQuestionProperty());
    }

    public void configListener() {
        subsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    vm.setAttributQuetion(subsList.getSelectionModel().getSelectedItem());
                });

        addQuestion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                System.out.println(subsList.getSelectionModel().getSelectedItem());
                vm.addQuestionforOpp(subsList.getSelectionModel().getSelectedItem());
                System.out.println(vm.selectedQuestionProperty());
                 
                
            }
        });
    }

}
