/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;
import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
    private final BorderPane borderPane = new BorderPane();
    private final VBox middleVbox = new VBox();
    private final GridPane detailsQuestion = new GridPane();
    private final GridPane gpTop = new GridPane();
    private final GridPane gpBottom = new GridPane();
    private Text attrQName = new Text("Enoncer de la Questions");
    private Text attrQPoint = new Text("Point de la Question");
    private Label response = new Label("Reponse");
    private Text dispoPoints = new Text("Point disponible");
    private Text questionPoints = new Text("Points Questionnaires");
    private Button addQuestion = new Button("=>");
    private Button delQuestion = new Button("<=");
    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
    private final GridPane gpButtons = new GridPane();//gere les boutons
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private IntegerProperty IndexQuestion = new SimpleIntegerProperty();
    private List<Question> reponses = new ArrayList<Question>();

    private String text = "text";

    ViewModel vm;
    Stage stage;

    public ViewGame(Stage stage, ViewModel facade) throws Exception {
        this.vm = facade;
        this.stage = stage;

        initGrid();
        configBinding();
        configListener();
        Scene scene = new Scene(borderPane, 1235, 500);
        stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Choix de questions");
        stage.setScene(scene);
    }

    public void initGrid() {
        configView();
        configRadioButton();
        configBindCheckBox();
    }

    public void configView() {
        borderPane.setLeft(subsList);
        borderPane.setCenter(middleVbox);
        borderPane.setRight(fillQuestion);
        borderPane.setTop(gpTop);
        borderPane.setBottom(gpBottom);
        borderPane.setPadding(new Insets(25));
        detailsQuestion.add(attrQName, 0, 0);
        detailsQuestion.add(attrQPoint, 0, 1);
        detailsQuestion.add(response, 0, 2);
        middleVbox.getChildren().addAll(detailsQuestion, reponse1, reponse2, reponse3, reponse4, gpButtons);
        middleVbox.setPadding(new Insets(0, 50, 0, 50));
        middleVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.gpButtons.add(addQuestion, 0, 0);
        gpButtons.add(delQuestion, 1, 0);
        gpBottom.add(valider, 1, 1);
        gpBottom.add(annuler, 2, 1);
    }

    public void configRadioButton() {
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);

    }

    public void configBindCheckBox() {
        reponse1.textProperty().bind(res1);
        reponse2.textProperty().bind(res2);
        reponse3.textProperty().bind(res3);
        reponse4.textProperty().bind(res4);

    }

    public void configBinding() {
        subsList.itemsProperty().bind(vm.quetionsProperty());
        vm.questionNameProperty().bindBidirectional(attrQName.textProperty());
        vm.questionPointProperty().bindBidirectional(attrQPoint.textProperty());
        fillQuestion.itemsProperty().bind(vm.selectedQuestionProperty());
        vm.getSelectedQuestion().bindBidirectional(this.selectedQuestion);
        vm.getIndexQuestion().bindBidirectional(this.IndexQuestion);
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);
    }

    public void configListener() {
        subsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    vm.setAttributQuetion(subsList.getSelectionModel().getSelectedItem());
                    System.out.println(res1.get());
                });

        addQuestion.setOnAction((ActionEvent e) -> {
            if (subsList.getSelectionModel().getSelectedItem() != null) {
                vm.addQuestionforOpp(subsList.getSelectionModel().getSelectedItem());
            }

        });
        delQuestion.setOnAction((ActionEvent e) -> {
            if (fillQuestion.getSelectionModel().getSelectedItem() != null) {
                vm.deleteQuestionForOpp(fillQuestion.getSelectionModel().getSelectedIndex());
            }
        });
    }

}
