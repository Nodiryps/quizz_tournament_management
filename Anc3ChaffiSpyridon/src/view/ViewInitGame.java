/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VMInitGame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.Player;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class ViewInitGame extends Popup {

    private final VMInitGame vm;
    private final Stage stage;
    private final Player p1;
    private final Player p2;
    private final LVQuestions questionList;
    private final LVOppQuestions fillQuestion;
    private final BorderPane borderPane = new BorderPane();
    private final VBox vbMiddle = new VBox();
    private final GridPane gpDetailsQuestion = new GridPane();
    private final GridPane gpBtnsValCan = new GridPane();
    private Label lbPointsQuestionList = new Label();
    private Label lbPointsOppList = new Label();
    private Label lbFillQuestions = new Label();
    
    private final GridPane gpLeft = new GridPane();
    private final GridPane gpCenter = new GridPane();
    private final GridPane gpRight = new GridPane();
    private final BorderPane bpBottom = new BorderPane();

    private Text attrQName = new Text("Enoncer de la Question");
    private IntegerProperty attrQPoint = new SimpleIntegerProperty();
    private Label lbAttrQPoints = new Label();
    private Label lbResponse = new Label("Réponses: ");
    private RadioButton radioBtn1 = new RadioButton();
    private RadioButton radioBtn2 = new RadioButton();
    private RadioButton radioBtn3 = new RadioButton();
    private RadioButton radioBtn4 = new RadioButton();
    private Button addQuestion = new Button("Ajouter");
    private Button delQuestion = new Button("Supprimer");

    private Button validate = new Button("Valider");
    private Button cancel = new Button("Annuler");

    private StringProperty resp1 = new SimpleStringProperty();
    private StringProperty resp2 = new SimpleStringProperty();
    private StringProperty resp3 = new SimpleStringProperty();
    private StringProperty resp4 = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private final GridPane gpBtnsAddDel = new GridPane();//gere les boutons
    private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<>();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private IntegerProperty IndexQuestion = new SimpleIntegerProperty();
    private IntegerProperty totalPoints = new SimpleIntegerProperty();
    private IntegerProperty cptPoints = new SimpleIntegerProperty();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private final BooleanProperty boolSelectRadioBtn1 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn2 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn3 = new SimpleBooleanProperty();
    private final BooleanProperty boolSelectRadioBtn4 = new SimpleBooleanProperty();
    private final BooleanProperty disableRadioBtn = new SimpleBooleanProperty();

    public ViewInitGame(VMInitGame vm, Player p1, Player p2) throws Exception {
        this.vm = vm;
        this.p1 = p1;
        this.p2 = p2;
        this.questionList = new LVQuestions(this.vm, radioBtn1, radioBtn2, radioBtn3, radioBtn4);
        this.fillQuestion = new LVOppQuestions(this.vm);
        init();
        stage = new Stage();
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Choix de questions");
        stage.setScene(new Scene(borderPane, 1145, 500));
        stage.show();
    }

    private void init() {
        configView();
        setToggleGroup();
        configBinding();
        configListener();
    }
    
    private void configView() {
        configMainGridPanes();
        configBorderPane();
        configGridPaneQuestDetails();
        configVBoxMiddle();
        configGPButtons();
    }
    
    private void configMainGridPanes() {
        configGridPaneLeft();
        configGridPaneCenter();
        configGridPaneRight();
        gridPanesHGap();
    }
    
    private void configGPButtons() {
        configGridPaneBtnAddDel();
        configGridPaneBtnValCan();
    }
    
    private void configBorderPane() {
        borderPane.setLeft(gpLeft);
        borderPane.setCenter(gpCenter);
        borderPane.setRight(gpRight);
        borderPane.setPadding(new Insets(25));
        borderPane.autosize();
    }
    
    private void configVBoxMiddle() {
        vbMiddle.getChildren().addAll(gpDetailsQuestion, radioBtn1, radioBtn2, radioBtn3, radioBtn4, gpBtnsAddDel);
        vbMiddle.setPadding(new Insets(0, 50, 0, 50));
        vbMiddle.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    
    private void gridPanesHGap() {
        gpLeft.setHgap(10);
        gpCenter.setHgap(10);
        gpRight.setHgap(10);
    }
    
    private void configGridPaneLeft() {
        gpLeft.add(new Label(p1.getFirstName() + "  CONTRE  " + p2.getFirstName()), 0, 0);
        gpLeft.add(questionList, 0, 1);
        gpLeft.add(lbPointsQuestionList, 0, 2);
        gpLeft.alignmentProperty().set(Pos.CENTER_LEFT);
    }
    
    private void configGridPaneCenter() {
        gpCenter.add(new Label("<CONSTRUCTION QUESTIONNAIRE>"), 0, 0);
        gpCenter.add(vbMiddle, 0, 1);
        gpCenter.add(gpBtnsAddDel, 0, 2);
        gpCenter.alignmentProperty().set(Pos.TOP_CENTER);
    }
    
    private void configGridPaneRight() {
        gpRight.add(lbFillQuestions, 0, 0);
        gpRight.add(fillQuestion, 0, 1);
        gpRight.add(lbPointsOppList, 0, 2);
        gpRight.add(gpBtnsValCan, 0, 3);
        gpRight.alignmentProperty().set(Pos.CENTER_RIGHT);
    }
    
    private void configGridPaneBtnValCan() {
        gpBtnsValCan.add(validate, 1, 1);
        gpBtnsValCan.add(cancel, 2, 1);
    }
    
    private void configGridPaneBtnAddDel() {
        gpBtnsAddDel.add(addQuestion, 0, 0);
        gpBtnsAddDel.add(delQuestion, 1, 0);
        gpBtnsAddDel.setVgap(10);
    }
    
    private void configGridPaneQuestDetails() {
        gpDetailsQuestion.add(attrQName, 0, 0);
        gpDetailsQuestion.add(lbAttrQPoints, 0, 1);
        gpDetailsQuestion.add(lbResponse, 0, 2);
    }

    public String css() {
        return "-fx-border-color: black;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 1;\n"
                + "-fx-border-style: solid;\n";
    }

    private void setToggleGroup() {
        radioBtn1.setToggleGroup(group);
        radioBtn2.setToggleGroup(group);
        radioBtn3.setToggleGroup(group);
        radioBtn4.setToggleGroup(group);
    }

    private void configBindRadioBtn() {
        bindingTextRadioBtn();
        bindingSelectRadioBtn();
        bindingDisableRadioBtn();
    }
    
    private void bindingTextRadioBtn() {
        radioBtn1.textProperty().bind(resp1);
        radioBtn2.textProperty().bind(resp2);
        radioBtn3.textProperty().bind(resp3);
        radioBtn4.textProperty().bind(resp4);
    }
    
    private void bindingDisableRadioBtn() {
        radioBtn1.disableProperty().bind(disableRadioBtn);
        radioBtn2.disableProperty().bind(disableRadioBtn);
        radioBtn3.disableProperty().bind(disableRadioBtn);
        radioBtn4.disableProperty().bind(disableRadioBtn);
    }
    
    private void bindingSelectRadioBtn() {
        radioBtn1.selectedProperty().bind(boolSelectRadioBtn1);
        radioBtn2.selectedProperty().bind(boolSelectRadioBtn2);
        radioBtn3.selectedProperty().bind(boolSelectRadioBtn3);
        radioBtn4.selectedProperty().bind(boolSelectRadioBtn4);
    }

    private void configBinding() {
        configBindingViewModel();
        configBindingViewInitGame();
        configBindRadioBtn();
    }

    private void configBindingViewInitGame() {
        configBindQuestions();
        configBindPoints();
        configBindLabels();
        configBindSelectRadioBtn();
        disableRadioBtn.bind(vm.getDisableRadioBtn());
    }
    
    private void configBindQuestions() {
        fillQuestion.itemsProperty().bind(vm.selectedQuestionProperty());
        lbAttrQPoints.textProperty().bind(attrQPoint.asString("%d Point(s)"));
    }
    
    private void configBindPoints() {
        totalPoints.bindBidirectional(vm.pointTotauxProperty());
        cptPoints.bind(vm.cptPointProperty());
    }
    
    private void configBindLabels() {
        lbPointsQuestionList.textProperty().bind(totalPoints.asString("Points disponibles: %d"));
        lbPointsOppList.textProperty().bind(cptPoints.asString("Points max: %d (entre " + vm.getMINIMUM_POINTS() + " et " + vm.getMAXIMUM_POINTS() + ")"));
        lbFillQuestions.textProperty().bind(cptFillQuestions.asString("NOMBRES DE QUESTIONS: %d"));
    }
    
    private void configBindSelectRadioBtn() {
        boolSelectRadioBtn1.bindBidirectional(vm.getBoolSelectRadioBtn1());
        boolSelectRadioBtn2.bindBidirectional(vm.getBoolSelectRadioBtn2());
        boolSelectRadioBtn3.bindBidirectional(vm.getBoolSelectRadioBtn3());
        boolSelectRadioBtn4.bindBidirectional(vm.getBoolSelectRadioBtn4());
    }

    private void configBindingViewModel() {
        vm.getSelectedQuestion().bindBidirectional(this.selectedQuestion);
        vm.getCurrentQuestion().bindBidirectional(currentQuestion);
        vm.getCptFillQuestions().bindBidirectional(cptFillQuestions);
        configBindQuestionAttr();
        configBindRes();
    }
    
    private void configBindQuestionAttr() {
        vm.getQuestionName().bindBidirectional(attrQName.textProperty());
        vm.getQuestionPoint().bindBidirectional(attrQPoint);
        vm.getIndexQuestion().bindBidirectional(this.IndexQuestion);
    }
    
    private void configBindRes() {
        vm.getRes1().bindBidirectional(resp1);
        vm.getRes2().bindBidirectional(resp2);
        vm.getRes3().bindBidirectional(resp3);
        vm.getRes4().bindBidirectional(resp4);
    }

    private void configListener() {
        addQuestion.setOnAction((ActionEvent e) -> {
                vm.addQuestionforOpp(getSelectedItem(questionList));

        });
        delQuestion.setOnAction((ActionEvent e) -> {
                vm.deleteQuestionForOpp(getSelectedItem(fillQuestion));
        });
        validate.setOnAction((ActionEvent event) -> {
           
            try {
                vm.launchPlay(p1, p2, this.stage);
            } catch (Exception ex) {
                ex.getMessage();
            }
        });
        cancel.setOnAction((ActionEvent event) -> {
            try {
                vm.emptySelectedList();
                stage.close();
            } catch (Exception ex) {
            }
        });
    }

    private Question getSelectedItem(ListView<Question> o) {
        return o.getSelectionModel().getSelectedItem();
    }
}
