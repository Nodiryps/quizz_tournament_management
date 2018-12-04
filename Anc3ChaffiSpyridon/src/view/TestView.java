package view;

import controller.Controller;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.testList;
import view.testList;
import model.*;

public class TestView extends Application {

    private Controller ctrl = new Controller();
    private testList ts = new testList();
    private RESULTS r;
    private static final int MAX_WORD_LENGTH = 15;
    private static final int TEXTSIZE = 400, SPACING = 10;

    // Composants graphiques
    private final VBox displayZone = new VBox();
    private final VBox leftZone = new VBox();
    private final VBox rightZone = new VBox();
    private final HBox topZone = new HBox();
    private final HBox bottomZone = new HBox();
    private ObservableList<String> SubscribsView = FXCollections.observableList(ctrl.getOpponentList());
    private ObservableList<Tournament> TournamentView = FXCollections.observableList(ctrl.getTournois());
    private ObservableList<String> MatchView = FXCollections.observableList(ctrl.getConvertMAtchList());
    private ObservableList<String> ComboOneView = FXCollections.observableList(ctrl.getOpponentList());
    //private ObservableList<Enum> Res = FXCollections.observableList();
 
    private final ListView<String> listInscrit = new ListView<String>(SubscribsView);
    private final ListView<String> listMatch = new ListView<String>(MatchView);
    private final ListView<Tournament> listTournoi = new ListView<Tournament>(TournamentView);
    private final Label lbNbLines = new Label();
    private final ComboBox listJouer = new ComboBox();
    private final ComboBox listadversaire = new ComboBox();
    private final ComboBox result = new ComboBox();
    private final Button valider  = new Button();
    private final Label titreTournois = new Label();
    private final Label titreInscrit = new Label();
    private final Label titrematch = new Label();
    private final GridPane bouton = new GridPane();
    private final GridPane top = new GridPane();
    private final GridPane bottom = new GridPane();
    private final GridPane bottomLeft = new GridPane();

    ObservableList<String> tableData = FXCollections.observableList(ts.getListe());

    public TestView() {
        initData();
    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(displayZone, 800, 400);
        primaryStage.setTitle("Gestion de  Tournois");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initData() {
        configDisplay();
        configBottomZone();
        addElem();
        decor();
        addComboBox();
        topZone.getChildren().addAll(listTournoi, titreTournois);
        //bottomZone.getChildren().addAll(listInscrit, listMatch, listJouer, listadversaire);
    }

    public void addElem() {
//        listTournoi.getItems().add("michel");
//        listTournoi.getItems().add("paul");
//        listTournoi.getItems().add("jean");
//        listInscrit.getItems().add("michel");
//        listInscrit.getItems().add("paul");
//        listInscrit.getItems().add("jean");
//        listMatch.getItems().add("michel");
//        listMatch.getItems().add("paul");
//        listMatch.getItems().add("jean");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void decor() {

        topZone.setSpacing(SPACING);
        bottomZone.setSpacing(SPACING);
        listTournoi.setMinHeight(50);

        listInscrit.setPrefWidth(TEXTSIZE);
        listTournoi.setPrefWidth(TEXTSIZE);
        listMatch.setPrefWidth(TEXTSIZE);
    }

    private void configDisplay() {
        displayZone.setPadding(new Insets(SPACING));
        displayZone.setSpacing(10);
        top.setPadding(new Insets(10, 0, 0, 10));
        top.add(new Label("les tournois: "), 0, 0);
        top.add(topZone, 0, 1);
        bottom.setPadding(new Insets(10, 0, 0, 10));
        bottom.add(new Label("les inscrits: "), 0, 0);
        bottom.add(bottomZone, 0, 1);

        displayZone.getChildren().addAll(top, bottom);
    }

    private void setTexteLabel() {
        titreInscrit.setText("Inscrit");
        titreTournois.setText("Tournois");
        titrematch.setText("matchs");

    }

    private void configBottomZone() {
        bouton.setVgap(4);
        bouton.setHgap(30);
        bouton.setPadding(new Insets(20, 0, 0, 20));
        bouton.add(new Label("jouer 1: "), 0, 0);
        bouton.add(listJouer, 1, 0);
        bouton.add(new Label("jouer 2: "), 2, 0);
        bouton.add(listadversaire, 3, 0);
        valider.setText("valider");
        
        leftZone.getChildren().add(listInscrit);
        rightZone.getChildren().addAll(listMatch, bouton,valider);
        bottomZone.getChildren().addAll(leftZone, rightZone);
    }

    public void addComboBox() {

        listJouer.setItems(ComboOneView);
        listadversaire.setItems(ComboOneView);
        listMatch.setItems(MatchView);

    }

}
