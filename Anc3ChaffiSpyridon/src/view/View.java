package view;

import controller.Controller;
import java.io.FileNotFoundException;
import java.util.Observer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.*;

public class View extends VBox implements Observer {

    private Stage stage;
    private final Controller ctrl;
    private final TournamentFacade facade;
    private Boolean playerOneSelected = true;

    private final testList ts = new testList();
    private RESULTS r;
    private static final int MAX_WORD_LENGTH = 15;
    private static final int TEXTSIZE = 400, SPACING = 10;

    private final VBox displayZone = new VBox();
    private final VBox leftZone = new VBox();
    private final VBox rightZone = new VBox();
    private final HBox topZone = new HBox();
    private final HBox bottomZone = new HBox();

    private final ListView<Player> listInscrit = new ListView<>();
    private final TableView<Match> listMatch = new TableView<>();
    private final ListView<Tournament> listTournoi = new ListView<>();
   
    private final ComboBox cbListJoueur = new ComboBox();
    private final ComboBox cbListadversaire = new ComboBox();
    private final ComboBox cbResult = new ComboBox();
    private final Button valider = new Button();
    private final Label titreTournois = new Label();
    private final Label titreInscrit = new Label();
    
    private final GridPane boutonGrid = new GridPane();//gere les boutons
    private final GridPane topGrid = new GridPane();
    private final GridPane bottomGrid = new GridPane();
    private final GridPane bottomRightGrid = new GridPane();

    public View(Stage primaryStage, Controller ctrl) {
        this.ctrl = ctrl;
        this.facade = ctrl.getFacade();
        this.stage = primaryStage;
        initData();
        Scene scene = new Scene(displayZone, 1125, 500);
        stage.setTitle("Gestion de  Tournois");
        stage.setScene(scene);
    }

    public void initData() {
        configDisplay();
        configBottomZone();
        decor();
        addElemComboBox();
        addElemListView();
        tableViewColumnConfig();
        configFocusListener();
        addListernerComboBox();
        topZone.getChildren().addAll(listTournoi, titreTournois);

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
        boutonGrid.add(cbListJoueur, 1, 0);
        boutonGrid.add(new Label("jouer 2: "), 2, 0);
        boutonGrid.add(cbListadversaire, 3, 0);
        boutonGrid.add(new Label("Resultat "), 4, 0);
        boutonGrid.add(cbResult, 5, 0);
        valider.setText("valider");
        boutonGrid.add(valider, 6, 0);

        leftZone.getChildren().add(listInscrit);
        rightZone.getChildren().addAll(bottomRightGrid, boutonGrid);
        bottomZone.getChildren().addAll(leftZone, rightZone);
    }

    public void addElemComboBox() {

        cbResult.getItems().addAll(
                RESULTS.DRAW,
                RESULTS.WINNER_P1,
                RESULTS.WINNER_P2
        );
    }

    public void addElemListView() {

    }

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

    // ajoute un listener sur differents elements.
    private void configFocusListener() {
        listTournoi.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    int index = listTournoi.getSelectionModel().getSelectedIndex();
                    ctrl.setIndex(index);

                });
        listInscrit.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    System.out.println(listInscrit.getSelectionModel().getSelectedItem());// ne retourne qu'une seul valeur a la fois (pas bon)

                });
        listMatch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Match m = (Match) listMatch.getSelectionModel().getSelectedItem();
                        try{
                            PopUpDelete.display(m, ctrl);
                        }
                        catch(FileNotFoundException e){
                        
                        }
                    }
                }
            }
        });

    }
    // ajoute un listener sur les combobox.

    public void addListernerComboBox() {
        cbListJoueur.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                    Player p = (Player) cbListJoueur.getSelectionModel().getSelectedItem();
                    facade.SetPlayer(p);
                });

        cbListadversaire.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

//                    Player p = (Player) cbListJoueur.getSelectionModel().getSelectedItem();
//                    facade.SetPlayer(p, !playerOneSelected);
                });

        cbResult.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                });

        valider.setOnAction((ActionEvent event) -> {
            Player p1 = (Player) cbListJoueur.getSelectionModel().getSelectedItem();
            Player p2 = (Player) cbListadversaire.getSelectionModel().getSelectedItem();
            RESULTS res = (RESULTS) cbResult.getSelectionModel().getSelectedItem();
            ctrl.createMatch(p1, p2, res);
        });

    }

    @Override
    public void update(java.util.Observable o, Object o1) {
        TournamentFacade facade = (TournamentFacade) o;
        TournamentFacade.TypeNotif typeNotif = (TournamentFacade.TypeNotif) o1;

        switch (typeNotif) {
            case INIT:
                ObservableList<Player> sub1 = FXCollections.observableArrayList(facade.getSubscrib());

                System.out.println("update INIT");
                listTournoi.getItems().clear();
                listInscrit.getItems().clear();
                listMatch.getItems().clear();

                for (Tournament t : facade.getTournamentList()) {
                    listTournoi.getItems().add(t);
                }
                for (Player p : facade.getSubscrib()) {
                    listInscrit.getItems().add(p);
                }
                for (Match m : facade.getMatchList()) {
                    listMatch.getItems().add(m);
                }
                listTournoi.getSelectionModel().select(facade.getTournois());
                cbListJoueur.setItems(sub1);
                cbListadversaire.setItems(sub1);

                break;

            case TOURNAMENT_SELECTED:
                ObservableList<Player> sub = FXCollections.observableArrayList(facade.getSubscrib());
                listInscrit.getItems().clear();
                listMatch.getItems().clear();
                cbListJoueur.getItems().clear();
                cbListadversaire.getItems().clear();
                cbResult.getSelectionModel().clearSelection();

                Tournament t = facade.getTournois();
                listInscrit.getItems().addAll(facade.getSubscrib());
                for (Match m : facade.getMatchList()) {
                    listMatch.getItems().add(m);
                }
                cbListJoueur.setItems(sub);
                cbListadversaire.setItems(sub);
                break;

            case PLAYER_ONE_SELECTED:
                ObservableList<Player> sub3 = FXCollections.observableArrayList(facade.addOppponentValidList());
                cbListadversaire.setItems(sub3);

                break;

//            case PLAYER_TWO_SELECTED:
//
//                ObservableList<Player> sub4 = FXCollections.observableArrayList(facade.addOppponentValidList());
//                cbListJoueur.setItems(sub4);
//
//                break;
            case ADD_MATCH:
                listMatch.getItems().clear();
                for (Match m : facade.getMatchList()) {
                    listMatch.getItems().add(m);
                }
                cbListJoueur.getSelectionModel().clearSelection();
                cbListadversaire.getSelectionModel().clearSelection();
                cbResult.getSelectionModel().clearSelection();

                break;

            case REMOVE_MATCH:
                listMatch.getItems().clear();
                for (Match m : facade.getMatchList()) {
                    listMatch.getItems().add(m);
                }
                break;

        }
    }
}
