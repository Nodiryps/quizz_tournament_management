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
    private final GridPane gpTop = new GridPane();
    private final GridPane gpBottom = new GridPane();
    private Label lbPointsQuestionList = new Label();
    private Label lbPointsOppList = new Label();
    private Label lbFillQuestions = new Label();
    //Zone milieu
    private Text attrQName = new Text("Enoncer de la Question: ");
    private IntegerProperty attrQPoint = new SimpleIntegerProperty();
    private Label lbAttrQPoints = new Label();
    private Label lbResponse = new Label("Réponses: ");
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
    private Button addQuestion = new Button("Ajouter");
    private Button delQuestion = new Button("Supprimer");
    /////////////////////////////////////////////////////////
    private Button valider = new Button("valider");
    private Button annuler = new Button("annuler");
    ///////elements bindés
    private StringProperty res1 = new SimpleStringProperty();
    private StringProperty res2 = new SimpleStringProperty();
    private StringProperty res3 = new SimpleStringProperty();
    private StringProperty res4 = new SimpleStringProperty();
    private final ToggleGroup group = new ToggleGroup();
    private final GridPane gpButtons = new GridPane();//gere les boutons
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
        this.questionList = new LVQuestions(this.vm, reponse1, reponse2, reponse3, reponse4);
        this.fillQuestion = new LVOppQuestions(this.vm);
        initGrid();
        configBinding();
        configListener();
        stage = new Stage();
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Choix de questions");
        stage.setScene(new Scene(borderPane, 1145, 500));
        stage.show();
    }

    private void initGrid() {
        configView();
        setToggleGroup();
        configBindRadioBtn();
    }

    private void configView() {
        configBorderPane();
        configGridPaneDetailsQ();
        configVBoxMiddle();
        configGridPaneTop();
        lbFillQuestions.autosize();
        this.gpButtons.add(addQuestion, 0, 0);
        gpButtons.add(delQuestion, 1, 0);
        configGridPaneBottom();
    }
    
    private void configVBoxMiddle() {
        vbMiddle.getChildren().addAll(gpDetailsQuestion, reponse1, reponse2, reponse3, reponse4, gpButtons);
        vbMiddle.setPadding(new Insets(0, 50, 0, 50));
        vbMiddle.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    
    private void configBorderPane() {
        borderPane.setLeft(questionList);
        borderPane.setCenter(vbMiddle);
        borderPane.setRight(fillQuestion);
        borderPane.setTop(gpTop);
        borderPane.setBottom(gpBottom);
        borderPane.setPadding(new Insets(25));
    }
    
    private void configGridPaneBottom() {
        gpBottom.add(lbPointsQuestionList, 0, 0);
        gpBottom.add(valider, 1, 1);
        gpBottom.add(annuler, 2, 1);
        gpBottom.add(lbPointsOppList, 3, 1);
    }
    
    private void configGridPaneTop() {
        gpTop.add(new Label(p1.getFirstName() + "  contre  " + p2.getFirstName()), 0, 1);
        gpTop.add(new Label("Construction Questionnaire"), 1, 0);
        gpTop.add(lbFillQuestions, 2, 1);
    }
    
    private void configGridPaneDetailsQ() {
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
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);
    }

    private void configBindRadioBtn() {
        bindingTextRadioBtn();
        bindingSelectRadioBtn();
        bindingDisableRadioBtn();
    }
    
    private void bindingTextRadioBtn() {
        reponse1.textProperty().bind(res1);
        reponse2.textProperty().bind(res2);
        reponse3.textProperty().bind(res3);
        reponse4.textProperty().bind(res4);
    }
    
    private void bindingDisableRadioBtn() {
        reponse1.disableProperty().bind(disableRadioBtn);
        reponse2.disableProperty().bind(disableRadioBtn);
        reponse3.disableProperty().bind(disableRadioBtn);
        reponse4.disableProperty().bind(disableRadioBtn);
    }
    
    private void bindingSelectRadioBtn() {
        reponse1.selectedProperty().bind(boolSelectRadioBtn1);
        reponse2.selectedProperty().bind(boolSelectRadioBtn2);
        reponse3.selectedProperty().bind(boolSelectRadioBtn3);
        reponse4.selectedProperty().bind(boolSelectRadioBtn4);
    }

    private void configBinding() {
        configBindingViewModel();
        configBindingViewInitGame();
    }

    private void configBindingViewInitGame() {
        configBindQuestions();
        configBindPoints();
        configBindLabels();
        configBindSelectRadioBtn();
        disableRadioBtn.bindBidirectional(vm.getDisableRadioBtn());
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
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);
    }

    private void configListener() {
        addQuestion.setOnAction((ActionEvent e) -> {
                vm.addQuestionforOpp(getSelectedItem(questionList));

        });
        delQuestion.setOnAction((ActionEvent e) -> {
                vm.deleteQuestionForOpp(getSelectedItem(fillQuestion));
        });
        valider.setOnAction((ActionEvent event) -> {
           
            try {
                vm.launchPlay(p1, p2, this.stage);
            } catch (Exception ex) {
                ex.getMessage();
            }
        });
        annuler.setOnAction((ActionEvent event) -> {
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
