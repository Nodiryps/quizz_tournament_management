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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class ViewGame extends Popup {

    private final ListView<Question> questionList = new ListView<>();
    private final ListView<Question> fillQuestion = new ListView<>();
    private Label pointLeft = new Label();
    private Label pointRight = new Label();
    private Label fillNummber= new Label();
    private final BorderPane borderPane = new BorderPane();
    private final VBox middleVbox = new VBox();
    private final GridPane detailsQuestion = new GridPane();
    private final GridPane gpTop = new GridPane();
    private final GridPane gpBottom = new GridPane();
    //Zone question display
    private Text attrQName = new Text("Enoncer de la Questions");
    private Text attrQPoint = new Text("Point de la Question");
    private Label response = new Label("Reponse");
    private Text dispoPoints = new Text("Point disponible");
    private Text questionPoints = new Text("Points Questionnaires");
    private Button addQuestion = new Button("=>");
    private Button delQuestion = new Button("<=");
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
    /////////////////////////////////////////////////////////
    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    ///////element binding
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private final GridPane gpButtons = new GridPane();//gere les boutons
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private IntegerProperty IndexQuestion = new SimpleIntegerProperty();
    private List<Question> reponses = new ArrayList<Question>();
    private IntegerProperty pointTotaux = new SimpleIntegerProperty();
    private IntegerProperty cptPoint = new SimpleIntegerProperty();
    private ObjectProperty<Question> d = new SimpleObjectProperty<>();
       private IntegerProperty fil = new SimpleIntegerProperty();

    private String text = "text";

    ViewModel vm;
    Stage stage;
    Player p1;
     Player p2;

    public ViewGame(Stage stage, ViewModel facade,Player c1, Player c2) throws Exception {
        this.vm = facade;
        this.stage =stage;
        p1=c1;
        p2=c2;
        initGrid();
        configBinding();
        configListener();
        Scene scene = new Scene(borderPane, 1235, 500);
       // stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        this.stage.setTitle("Choix de questions");
        this.stage.setScene(scene);
    }

    public void initGrid() {
        configView();
        configRadioButton();
        configBindCheckBox();
        disableRadioButtons();
        preselectAnswer();
    }

    public void configView() {
        borderPane.setLeft(questionList);
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
        
       gpTop.add(new Label(p1.getFirstName()+" contre"+p2.getFirstName()), 0, 1);
       gpTop.add(new Label("Construction Questionnaire"), 1, 0);
       gpTop.add(fillNummber, 2, 0);
        this.gpButtons.add(addQuestion, 0, 0);
        gpButtons.add(delQuestion, 1, 0);
        gpBottom.add(pointLeft, 0, 0);
        gpBottom.add(valider, 1, 1);
        gpBottom.add(annuler, 2, 1);
        gpBottom.add(pointRight, 3, 1);
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
        configBindingVm();
        configBindingVg();
    }

    private void configBindingVg() {
        questionList.itemsProperty().bind(vm.quetionsProperty());
        fillQuestion.itemsProperty().bind(vm.selectedQuestionProperty());
        pointTotaux.bindBidirectional(vm.pointTotauxProperty());
        pointLeft.textProperty().bind(pointTotaux.asString());
        pointRight.textProperty().bind(cptPoint.asString());
        cptPoint.bind(vm.cptPointProperty());
        fillNummber.textProperty().bind(fil.asString());

    }

    private void configBindingVm() {
        vm.questionNameProperty().bindBidirectional(attrQName.textProperty());
        vm.questionPointProperty().bindBidirectional(attrQPoint.textProperty());
        vm.getSelectedQuestion().bindBidirectional(this.selectedQuestion);
        vm.getIndexQuestion().bindBidirectional(this.IndexQuestion);
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);
//        vm.cptPointProperty().bindBidirectional(this.cptPoint);
        vm.thisQuestion.bindBidirectional(d);
        vm.fillNumber.bindBidirectional(fil);
    }

    public void configListener() {
        questionList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    vm.setAttributQuetion(questionList.getSelectionModel().getSelectedItem());
                    preselectAnswer();
                    System.out.println(d.get());
                });

        addQuestion.setOnAction((ActionEvent e) -> {
            if (questionList.getSelectionModel().getSelectedItem() != null) {
                vm.addQuestionforOpp(questionList.getSelectionModel().getSelectedItem());
            }
            System.out.println(pointTotaux.get());

        });
        delQuestion.setOnAction((ActionEvent e) -> {
            if (fillQuestion.getSelectionModel().getSelectedItem() != null) {
                vm.deleteQuestionForOpp(fillQuestion.getSelectionModel().getSelectedIndex());
            }
        });
    }

    public void disableRadioButtons() {
        reponse1.setDisable(true);
        reponse2.setDisable(true);
        reponse3.setDisable(true);
        reponse4.setDisable(true);
    }

    public void preselectAnswer() {
        if (d.get() != null) {
            int indice = d.get().getNumCorrectResponse().get();

            switch (indice) {
                case 1:
                    reponse1.setSelected(true);
                    break;
                case 2:
                    reponse2.setSelected(true);
                    break;
                case 3:
                    reponse3.setSelected(true);
                    break;
                case 4:
                    reponse4.setSelected(true);
                    break;
            }
        }
    }

}
