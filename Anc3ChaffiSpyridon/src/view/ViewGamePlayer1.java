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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;
import model.Question;

/**
 *
 * @author 2707chshyaka
 */
public class ViewGamePlayer1 extends Popup {

    private final LVQuestions questionList;
    private final LVOppQuestions fillQuestion;
    private Label lbPointsLeft = new Label();
    private Label lbPointsRight = new Label();
    private Label lbFillQuestions = new Label();
    private final BorderPane borderPane = new BorderPane();
    private final VBox vbMiddle = new VBox();
    private final GridPane gpDetailsQuestion = new GridPane();
    private final GridPane gpTop = new GridPane();
    private final GridPane gpBottom = new GridPane();
    //Zone milieu
    private Text attrQName = new Text("Enoncer de la Questions");
   private IntegerProperty attrQPoint = new SimpleIntegerProperty();
    private Label attribQuestionPoints= new Label();
    private Label response = new Label("Reponse");
    private Button addQuestion = new Button("=>");
    private Button delQuestion = new Button("<=");
    private RadioButton reponse1 = new RadioButton();
    private RadioButton reponse2 = new RadioButton();
    private RadioButton reponse3 = new RadioButton();
    private RadioButton reponse4 = new RadioButton();
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
    private IntegerProperty IndexQuestion = new SimpleIntegerProperty();
    private IntegerProperty totalPoints = new SimpleIntegerProperty();
    private IntegerProperty cptPoints = new SimpleIntegerProperty();
    private ObjectProperty<Question> currentQuestion = new SimpleObjectProperty<>();
    private IntegerProperty cptFillQuestions = new SimpleIntegerProperty();
    private final ViewModel vm;
    private final Stage stage;
    private final Player p1;
    private final Player p2;
    
    private final BooleanProperty bl =new SimpleBooleanProperty();

    public ViewGamePlayer1(ViewModel vm, Player p1, Player p2) throws Exception {
    
        this.vm = vm;
        this.p1 = p1;
        this.p2 = p2;
        this.questionList = new LVQuestions(this.vm,reponse1,reponse2,reponse3,reponse4);
        this.fillQuestion = new LVOppQuestions(this.vm);
        initGrid();
        configBinding();
        configListener();
        preselectAnswer();
        System.out.println(vm.bl.get());
        stage = new Stage();
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Choix de questions");
        stage.setScene(new Scene(borderPane, 1145, 500));
        stage.show();
    }

    private void initGrid() {
        configView();
        configRadioButton();
        configBindCheckBox();
        disableRadioButtons(bl.get());
        System.out.println(bl.get());
    }

    private void configView() {
        borderPane.setLeft(questionList);
        borderPane.setCenter(vbMiddle);
        borderPane.setRight(fillQuestion);
        borderPane.setTop(gpTop);
        borderPane.setBottom(gpBottom);
        borderPane.setPadding(new Insets(25));
        gpDetailsQuestion.add(attrQName, 0, 0);
        gpDetailsQuestion.add(attribQuestionPoints, 0, 1);
        gpDetailsQuestion.add(response, 0, 2);
        vbMiddle.getChildren().addAll(gpDetailsQuestion, reponse1, reponse2, reponse3, reponse4, gpButtons);
        vbMiddle.setPadding(new Insets(0, 50, 0, 50));
        vbMiddle.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        gpTop.add(new Label(p1.getFirstName() + "  contre  " + p2.getFirstName()), 0, 1);
        gpTop.add(new Label("Construction Questionnaire"), 1, 0);
        gpTop.add(lbFillQuestions, 2, 1);
        lbFillQuestions.autosize();
        this.gpButtons.add(addQuestion, 0, 0);
        gpButtons.add(delQuestion, 1, 0);
        gpBottom.add(lbPointsLeft, 0, 0);
        gpBottom.add(valider, 1, 1);
        gpBottom.add(annuler, 2, 1);
        gpBottom.add(lbPointsRight, 3, 1);
    }
     
    public String css(){
    return "-fx-border-color: black;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n" +
                   "-fx-border-style: dashed;\n";
    }

    private void configRadioButton() {
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);
    }

    private void configBindCheckBox() {
        reponse1.textProperty().bind(res1);
        reponse2.textProperty().bind(res2);
        reponse3.textProperty().bind(res3);
        reponse4.textProperty().bind(res4);
        
    }

    private void configBinding() {
        configBindingViewModel();
        configBindingViewGP1();
    }

    private void configBindingViewGP1() {
        fillQuestion.itemsProperty().bind(vm.selectedQuestionProperty());
        totalPoints.bindBidirectional(vm.pointTotauxProperty());
        lbPointsLeft.textProperty().bind(totalPoints.asString("Points disponibles: %d"));
        lbPointsRight.textProperty().bind(cptPoints.asString("Points Total: %d /10"));
        cptPoints.bind(vm.cptPointProperty());
        lbFillQuestions.textProperty().bind(cptFillQuestions.asString("NOMBRES DE QUESTIONS: %d"));
        attribQuestionPoints.textProperty().bind(attrQPoint.asString("%d Point(s)"));
        bl.bindBidirectional(vm.bl);

    }

    private void configBindingViewModel() {
        vm.questionNameProperty().bindBidirectional(attrQName.textProperty());
        vm.questionPointProperty().bindBidirectional(attrQPoint);
        vm.getSelectedQuestion().bindBidirectional(this.selectedQuestion);
        vm.getIndexQuestion().bindBidirectional(this.IndexQuestion);
        vm.getRes1().bindBidirectional(res1);
        vm.getRes2().bindBidirectional(res2);
        vm.getRes3().bindBidirectional(res3);
        vm.getRes4().bindBidirectional(res4);
        vm.currentQuestion.bindBidirectional(currentQuestion);
        vm.cptFillQuestions.bindBidirectional(cptFillQuestions);
    }

    private void configListener() {
        addQuestion.setOnAction((ActionEvent e) -> {
            if (questionList.getSelectionModel().getSelectedItem() != null) {
                vm.addQuestionforOpp(questionList.getSelectionModel().getSelectedItem());
            }

        });
        delQuestion.setOnAction((ActionEvent e) -> {
            if (fillQuestion.getSelectionModel().getSelectedItem() != null) {
                vm.deleteQuestionForOpp(getSelected(fillQuestion));
            }
        });
        valider.setOnAction((ActionEvent event) -> {
            try {
                vm.launchPlay(p1,p2);
                stage.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        annuler.setOnAction((ActionEvent event) -> {
            try {
                vm.emptyselectedList();
                stage.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void disableRadioButtons(Boolean b) {
        System.out.println(b);
        reponse1.setDisable(b);
        reponse2.setDisable(b);
        reponse3.setDisable(b);
        reponse4.setDisable(b);
    }
    
     private void preselectAnswer() {
        if (currentQuestion.get() != null) {
            int indice = currentQuestion.get().getNumCorrectResponse().get();
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
     
     public Question getSelected(ListView<Question> o){
         return o.getSelectionModel().getSelectedItem();
     }

}
