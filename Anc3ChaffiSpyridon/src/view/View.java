package view;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.testList;
import view.testList;
import model.*;

public class View extends HBox implements Observer {

    private Stage stage;
    private Controller ctrl = new Controller();
    private TournamentFacade facade = new TournamentFacade();
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
    //private ObservableList<String> SubscribsView = FXCollections.observableList(facade.getOpponentList());
    // private ObservableList<Tournament> TournamentView = FXCollections.observableList(facade.getTournois());
//  private ObservableList<String> MatchView = FXCollections.observableList(facade.getConvertMAtchList());
    //private ObservableList<String> ComboOneView = FXCollections.observableList(facade.getOpponentList());
    //private ObservableList<Enum> Res = FXCollections.observableList();

    private final ListView<Player> listInscrit = new ListView<>();
    private final TableView<Match> listMatch = new TableView<>();
    private final ListView<Tournament> listTournoi = new ListView<>();
    private final Label lbNbLines = new Label();
    private final ComboBox listJouer = new ComboBox();
    private final ComboBox listadversaire = new ComboBox();
    private final ComboBox result = new ComboBox();
    private final Button valider = new Button();
    private final Label titreTournois = new Label();
    private final Label titreInscrit = new Label();
    private final Label titrematch = new Label();
    private final GridPane boutonGrid = new GridPane();//gere les boutons
    private final GridPane topGrid = new GridPane();
    private final GridPane bottomGrid = new GridPane();
    private final GridPane bottomRightGrid = new GridPane();

    // ObservableList<String> tableData = FXCollections.observableList(ts.getListe());
    public View(Stage primaryStage) {
        initData();
        Scene scene = new Scene(displayZone, 1125, 500);
        primaryStage.setTitle("Gestion de  Tournois");
        primaryStage.setScene(scene);
    }

    public void initData() {
        configDisplay();
        configBottomZone();
        decor();
        addElemComboBox();
        addElemListView();
        tableViewColumnConfig();
        // addActionEvent();
        configFocusListener();
        addListernerComboBox();
        topZone.getChildren().addAll(listTournoi, titreTournois);
        //bottomZone.getChildren().addAll(listInscrit, getMatch, listJouer, listadversaire);
    }

    public void decor() {

        topZone.setSpacing(SPACING);
        bottomZone.setSpacing(SPACING);
        listTournoi.setMinHeight(50);
        listInscrit.getSelectionModel().select(-1);
        listInscrit.setPrefWidth(TEXTSIZE);
        listTournoi.setPrefWidth(TEXTSIZE);
        listMatch.setPrefWidth(TEXTSIZE);
    }

    private void configDisplay() {
        displayZone.setPadding(new Insets(SPACING));
        displayZone.setSpacing(10);
        topGrid.setPadding(new Insets(10, 0, 0, 10));
        topGrid.add(new Label("les tournois: "), 0, 0);
        topGrid.add(topZone, 0, 1);
        bottomGrid.setPadding(new Insets(10, 0, 0, 10));
        bottomGrid.add(new Label("les inscrits: "), 0, 0);
        bottomRightGrid.setPadding(new Insets(10, 0, 0, 10));
        bottomRightGrid.add(new Label("les Matchs"), 0, 0);
        bottomRightGrid.add(listMatch, 0, 1);// column lines
        bottomGrid.add(bottomZone, 0, 1);

        displayZone.getChildren().addAll(topGrid, bottomGrid);
    }

    // a supprimmer peut etre .
//    private void setTexteLabel() {
//        titreInscrit.setText("Inscrit");
//        titreTournois.setText("Tournois");
//        titrematch.setText("match");
//
//    }
    private void configBottomZone() {
        boutonGrid.setVgap(4);
        boutonGrid.setHgap(30);
        boutonGrid.setPadding(new Insets(20, 0, 0, 20));
        boutonGrid.add(new Label("jouer 1: "), 0, 0);
        boutonGrid.add(listJouer, 1, 0);
        boutonGrid.add(new Label("jouer 2: "), 2, 0);
        boutonGrid.add(listadversaire, 3, 0);
        boutonGrid.add(new Label("Resultat "), 4, 0);
        boutonGrid.add(result, 5, 0);
        valider.setText("valider");
        boutonGrid.add(valider, 6, 0);

        leftZone.getChildren().add(listInscrit);
        rightZone.getChildren().addAll(bottomRightGrid, boutonGrid);
        bottomZone.getChildren().addAll(leftZone, rightZone);
    }

    public void addElemComboBox() {

        result.getItems().addAll(
                RESULTS.DRAW,
                RESULTS.WINNER_P1,
                RESULTS.WINNER_P2
        );
    }

    public void addElemListView() {

    }

    //a supprimer aussi.
//    public void fillMatchView(TableColumn<Match, String> player1, TableColumn<Match, String> player2, TableColumn<Match, String> results) {
//        List<Match> listmatch2 = new ArrayList<>();
//        for (Match m : facade.getMatchList()) {
//            player1.setCellValueFactory(new PropertyValueFactory<>("test"));
//            player2.setCellValueFactory(new PropertyValueFactory<>("test"));
//            results.setCellValueFactory(new PropertyValueFactory<>("test"));
//        }
//        for (Match ms : facade.getMatchList()) {
//            listmatch2.add(ms);
//        }
//
//        ObservableList<Match> list = FXCollections.observableArrayList(listmatch2);
//        listMatch.setItems(list);
//
//    }
    public void tableViewColumnConfig() {
        TableColumn<Match, String> player1 = new TableColumn<>("player One");
        player1.setMinWidth(133);
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));

        TableColumn<Match, String> player2 = new TableColumn("player Two");
        player2.setMinWidth(133);
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));

        TableColumn<Match, String> results = new TableColumn<>("results");
        results.setMinWidth(133);
        results.setCellValueFactory(new PropertyValueFactory<>("results"));

        this.listMatch.getColumns().addAll(player1, player2, results);

    }

    // ajoute un listener sur les listes.methode reprise dans configFocusListerner();
//    public void addActionEvent() {
//        listTournoi.getSelectionModel().selectedIndexProperty()
//                .addListener((Observable o) -> {
//                    System.out.println(listTournoi.getSelectionModel().getSelectedItem());
//                });
//    }
    // ajoute un listener sur differents elements.
    private void configFocusListener() {
        listInscrit.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    System.out.println(listInscrit.getSelectionModel().getSelectedItem());// ne retourne qu'une seul valeur a la fois (pas bon)

                });
//        listTournoi.getSelectionModel().selectedIndexProperty()
//                .addListener((Observable o) -> {
//                    int index = listTournoi.getSelectionModel().getSelectedIndex();
//                    facade.setIndex(index);
//                    System.out.println(index);
//                });

//        listMatch.getSelectionModel().selectedIndexProperty()
//                .addListener((Observable o) -> {
//                    System.out.println(listMatch.getSelectionModel().getSelectedItem());
//                });
        listMatch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(listMatch.getSelectionModel().getSelectedItem());
            }
        }
        );

    }
    // ajoute un listener sur les combobox.

    public void addListernerComboBox() {

        listJouer.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                  
                });

        listadversaire.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    System.out.println(listadversaire.getSelectionModel().getSelectedItem());
                });

        result.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    System.out.println(result.getSelectionModel().getSelectedItem());
                });

        valider.setOnAction((ActionEvent event) -> {
            //return new PopUpview(stage);
        });
    }

    @Override
    public void update(java.util.Observable o, Object o1) {
        TournamentFacade facade = (TournamentFacade) o;
        TournamentFacade.TypeNotif typeNotif = (TournamentFacade.TypeNotif) o1;
        switch (typeNotif) {
            case INIT:
                for (Tournament t : facade.getTournamentList()) {
                    listTournoi.getItems().add(t);
                }
                for(Player p:facade.getSubscrib(facade.getTournois())){
                  listInscrit.getItems().add(p);
                }
                
                 for(Match m:facade.getMatchList(facade.getTournois())){
                  listMatch.getItems().add(m);
                }
                break;
            case TOURNAMENT_SELECTED:
                listInscrit.getItems().clear();
                listMatch.getItems().clear();
                
                Tournament t = facade.getTournois();
                for (Player p : facade.getSubscrib(t)) {
                    listInscrit.getItems().add((p));
                }
                for (Match m : facade.getMatchList(t)) {
                    listMatch.getItems().add(m);
                }
                break;
        }
    }
}
