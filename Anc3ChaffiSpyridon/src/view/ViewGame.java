/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.FileNotFoundException;
import controller.VMInitGame;
import controller.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;
import model.Question;

/**
 *
 * @author Spy
 */
public class ViewGame extends VBox {

    Stage stage;
    VMInitGame vm;
    private final VBox displayQuestion = new VBox();
    private final GridPane display = new GridPane();
    private final IntegerProperty score = new SimpleIntegerProperty();
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
    private Label lbTitle = new Label("<REPONDEZ AUX QEUSTIONS>");
    private Label lbCptQ = new Label("Nb de question: ");
    private Label lbP2Points = new Label("Vos points: ");
    private Label lbAttrQPoints = new Label();
    private Label lbResponse = new Label("Réponse");
    private Text attrQName = new Text("Enoncer de la Question");
    private IntegerProperty cptQ = new SimpleIntegerProperty();
    private IntegerProperty attrQPoint = new SimpleIntegerProperty();
    private IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private Button validate = new Button("valider");
    private Button abandon = new Button("abandonner");
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private final Player p1;
    private final Player p2;
    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private final BooleanProperty boolUnselectRadioBtn = new SimpleBooleanProperty();

    public ViewGame(VMInitGame vm, ObservableList<Question> list, Player p1, Player p2, Stage s) {
        this.selectedQuestionList = list;
        this.vm = vm;
        this.p1 = p1;
        this.p2 = p2;
        initData();
        stage = s;
        stage.setTitle("Répondez aux questions");
        stage.setScene(new Scene(display, 650, 300));
        stage.show();
    }

    public void initData() {
        configView();
        configRadioButton();
        configBindRadioBtn();
        configListerner();
    }

    public void configView() {
        display.add(lbTitle, 0, 0);
        display.add(new Label(p1.getFirstName() + "  CONTRE  " + p2.getFirstName()), 0, 1);
        display.add(lbCptQ, 0, 5);
        displayQuestion.getChildren().addAll(attrQName, lbAttrQPoints, lbResponse, reponse1, reponse2, reponse3, reponse4);
        display.add(displayQuestion, 0, 8);
        display.add(lbP2Points, 0, 9);
        display.add(validate, 0, 10);
        display.add(abandon, 0, 11);
        displayQuestion.setStyle(css());
        display.alignmentProperty().set(Pos.CENTER);
    }

    private void configRadioButton() {
        setToggleGrp();
        configBindQuestionAttr();
        configBindPoints();
        configBindLabels();
        cptQ.bind(vm.getCptFillQuestions());
        gameOver.bindBidirectional(vm.getGameOver());
        boolUnselectRadioBtn.bindBidirectional(vm.getDisableRadioBtn());
    }

    private void configBindLabels() {
        lbCptQ.textProperty().bind(cptQ.asString("Question(s): %d/" + this.getSelectedQuestionList().size()));
        lbAttrQPoints.textProperty().bind(attrQPoint.asString("%d point(s)"));
    }

    private void configBindPoints() {
        score.bind(vm.cptPointProperty());
        lbP2Points.textProperty().bind(score.asString("Votre score: %d/" + vm.getMAX_POINTS_GAME().getValue()));
    }

    private void configBindQuestionAttr() {
        vm.getIndexQuestion().bindBidirectional(indexQuestion);
        vm.getQuestionName().bindBidirectional(attrQName.textProperty());
        vm.getQuestionPoint().bindBidirectional((attrQPoint));
    }

    private void setToggleGrp() {
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);
    }

    private void configBindRadioBtn() {
        configBindText();
        configBindRes();

        vm.getSelectedQuestionList().bind(this.getSelectedQuestionList());
    }

    private void configBindText() {
        reponse1.textProperty().bind(res1);
        reponse2.textProperty().bind(res2);
        reponse3.textProperty().bind(res3);
        reponse4.textProperty().bind(res4);
    }

    private void configBindRes() {
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);
    }

    private void configListerner() {
        validate.setOnAction((ActionEvent event) -> {
            vm.nextQuestion(((RadioButton) group.getSelectedToggle()).getText(), stage, group);
        });
        abandon.setOnAction((ActionEvent event) -> {
            vm.giveUpGame(stage);
        });
    }

    public String css() {
        return "-fx-border-color: black;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 1;\n"
                + "-fx-border-style: solid;\n"
                + "-fx-padding: 12;\n";
    }

    public SimpleListProperty<Question> getSelectedQuestionList() {
        return new SimpleListProperty<>(selectedQuestionList);
    }

}
