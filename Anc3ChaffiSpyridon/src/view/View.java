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
import javafx.geometry.Pos;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.*;

public class View extends VBox implements Observer {

    private Stage stage;
    private final Controller ctrl;
    private final TournamentFacade facade;
    private static final int TEXTSIZE = 400, SPACING = 10;
    private final ListView<Player> listInscrit = new ListView<>();
    private final TableView<Match> listMatch = new TableView<>();
    private final ListView<Tournament> listTournoi = new ListView<>();
    private final HBox displayZone = new HBox();
    private final GridPane left = new GridPane();
    private final GridPane right = new GridPane();
    private final ComboBox cbListJoueur = new ComboBox();
    private final ComboBox cbListadversaire = new ComboBox();
    private final ComboBox cbResult = new ComboBox();
    private final Button valider = new Button();
    private final GridPane boutonGrid = new GridPane();//gere les boutons
    private PopUpDelete popup;

    public View(Stage primaryStage, Controller ctrl) {
        this.ctrl = ctrl;
        this.facade = ctrl.getFacade();
        this.stage = primaryStage;

        initData();
        Scene scene = new Scene(displayZone, 1180, 500);
        stage.setResizable(false);
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
    }

    public void decor() {
        listTournoi.setMinHeight(50);
        listInscrit.getSelectionModel().select(-1);
        listTournoi.setPrefWidth(TEXTSIZE);
        listMatch.setPrefWidth(TEXTSIZE);
    }

    private void configDisplay() {
        displayZone.setPadding(new Insets(SPACING));
        left.setPadding(new Insets(20, 10, 20, 20));
        right.setPadding(new Insets(20, 20, 20, 10));
        right.setHgap(10);
        left.add(new Label("les tournois"), 0, 0);
        left.add(new Label("les inscrits"), 0, 2);
        right.add(new Label("les matchs"), 0, 0);
        left.add(listTournoi, 0, 1);
        left.add(listInscrit, 0, 3);
        right.add(listMatch, 0, 1);
        right.add(boutonGrid, 0, 2);
        displayZone.getChildren().addAll(left, right);
    }

    private void configBottomZone() {
        boutonGrid.setVgap(4);
        boutonGrid.setHgap(30);
        boutonGrid.setPadding(new Insets(20, 0, 0, 20));
        boutonGrid.add(new Label("joueur 1: "), 0, 0);
        boutonGrid.add(cbListJoueur, 1, 0);
        boutonGrid.add(new Label("joueur 2: "), 2, 0);
        boutonGrid.add(cbListadversaire, 3, 0);
        boutonGrid.add(new Label("Resultat "), 4, 0);
        boutonGrid.add(cbResult, 5, 0);
        valider.setText("valider");
        boutonGrid.add(valider, 6, 0);
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
        TableColumn<Match, String> player1 = new TableColumn<>("Joueur 1");
        player1.setMinWidth(133);
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));

        TableColumn<Match, String> player2 = new TableColumn("Joueur 2");
        player2.setMinWidth(133);
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));

        TableColumn<Match, String> results = new TableColumn<>("Resultats");
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
                        int index = listMatch.getSelectionModel().getSelectedIndex();
                        System.out.println("view" + m);
                        if (!ctrl.getAllMAtch().isEmpty()) {
                            ctrl.setMatchSelected(m, index);
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
                    if (cbListJoueur.getSelectionModel().isEmpty() || cbListadversaire.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    }
                });

        cbListadversaire.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                    Player p = (Player) cbListJoueur.getSelectionModel().getSelectedItem();
                    facade.SetPlayer(p);
                    if (cbListJoueur.getSelectionModel().isEmpty() || cbListadversaire.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        cbResult.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbListJoueur.getSelectionModel().isEmpty() || cbListadversaire.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        valider.setOnAction((ActionEvent event) -> {
            Player p1 = (Player) cbListJoueur.getSelectionModel().getSelectedItem();
            Player p2 = (Player) cbListadversaire.getSelectionModel().getSelectedItem();
            RESULTS res = (RESULTS) cbResult.getSelectionModel().getSelectedItem();
            ctrl.createMatch(p1, p2, res);
        });

    }

    private void setButtonDisable(boolean b) {
        valider.setDisable(b);
    }

    @Override
    public void update(java.util.Observable o, Object o1) {
        TournamentFacade facade = ctrl.getFacade();
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
                listTournoi.getSelectionModel().select(ctrl.getTournament());
                cbListJoueur.setItems(sub1);
                cbListadversaire.setItems(sub1);
                setButtonDisable(true);

                break;

            case TOURNAMENT_SELECTED:
                ObservableList<Player> sub = FXCollections.observableArrayList(facade.getSubscrib());
                listInscrit.getItems().clear();
                listMatch.getItems().clear();
                cbListJoueur.getItems().clear();
                cbListadversaire.getItems().clear();
                cbResult.getSelectionModel().clearSelection();

                Tournament t = ctrl.getTournament();
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
                try {
                    Match m = ctrl.getSelectedMatch();
                    System.out.println("NOTIFY" + m);
                    popup = new PopUpDelete(m, ctrl);

                } catch (FileNotFoundException e) {
                    System.out.println("Fichier introuvable");
                }
                listMatch.getItems().clear();
                for (Match m : facade.getMatchList()) {
                    listMatch.getItems().add(m);
                }
                break;

        }
    }
}
