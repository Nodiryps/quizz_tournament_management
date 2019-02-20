/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VMGame;
import controller.VMInitGame;
import controller.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
public class ViewGame extends VBox {

    Stage stage;
    VMGame vm;
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
    private Label lbcptq = new Label("le compteur/nrb de question");
    private IntegerProperty cptQ = new SimpleIntegerProperty();
    private Label pointGagner = new Label("les points");
    private Button valider = new Button("valider");
    private Button annuler = new Button("abandonner");
    private Text attrQName = new Text("Enoncer de la Question");
    private IntegerProperty attrQPoint = new SimpleIntegerProperty();
    private Label attribQuestionPoints= new Label();
    private final IntegerProperty cptQuestion = new SimpleIntegerProperty();
    private Label response = new Label("Reponse");
    private ObservableList<Question> selectedQuestionList = FXCollections.observableArrayList();
    private IntegerProperty indexQuestion = new SimpleIntegerProperty();
    private final Player p1;
    private final Player p2;
    private BooleanProperty gameOver = new SimpleBooleanProperty();
    private BooleanProperty deselectedRadioButon = new SimpleBooleanProperty();

    public ViewGame(VMGame vm, ObservableList<Question> list, Player p1, Player p2) {
        this.selectedQuestionList = list;
        this.vm = vm;
        this.p1 = p1;
        this.p2 = p2;
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
        display.add(new Label(p1.getFirstName() + "  contre  " + p2.getFirstName()), 0, 1);
        display.add(lbcptq, 0, 5);
        displayQuestion.getChildren().addAll(attrQName,attribQuestionPoints, response, reponse1, reponse2, reponse3, reponse4);
        display.add(displayQuestion, 0, 8);
        display.add(pointGagner, 0, 9);
        display.add(valider, 0, 10);
        display.add(annuler, 0, 11);
        displayQuestion.setStyle(css());
    }

    private void configRadioButton() {
        reponse1.setToggleGroup(group);
        reponse2.setToggleGroup(group);
        reponse3.setToggleGroup(group);
        reponse4.setToggleGroup(group);
        cptQ.bind(vm.getCptFillQuestions());
        lbcptq.textProperty().bind(cptQ.asString("Question restante: %d/" + selectedQuestionList.size()));

        vm.getIndexQuestion().bindBidirectional(indexQuestion);
        vm.getQuestionName().bindBidirectional(attrQName.textProperty());
        vm.getQuestionPoint().bindBidirectional((attrQPoint));
        pointGagner.textProperty().bind(score.asString("Votre score: %d/10"));
        score.bind(vm.cptPointProperty());
        gameOver.bindBidirectional(vm.getGameOver());
        attribQuestionPoints.textProperty().bind(attrQPoint.asString("%d point(s)"));
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
            String t = ((RadioButton) group.getSelectedToggle()).getText();
            vm.nextQuestion(t,stage);// on vas lui passer les infos des radiobouton et cree une methode dans la ViewModel qui 
          
        });
        annuler.setOnAction((ActionEvent event) -> {
//           vm.emptyselectedList();
//           this.stage.close();;
//              vider liste de qiestionOPP
//              vider les comboBox.
//              rajouter une defaite.
        });
    }
     public String css(){
    return "-fx-border-color: black;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 1;\n" +
                   "-fx-border-style: solid;\n"+
                     "-fx-padding: 12;\n";
    }
     
     

}
