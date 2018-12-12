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
    private static final int TEXTSIZE = 400, SPACING = 10;
    private final ListView<Player> subsList = new ListView<>();
    private final TableView<Match> matchesList = new TableView<>();
    private final ListView<Tournament> tournamentsList = new ListView<>();
    private final HBox displayZone = new HBox();
    private final GridPane left = new GridPane();
    private final GridPane right = new GridPane();
    private final ComboBox cbPlayersList = new ComboBox();
    private final ComboBox cbOppList = new ComboBox();
    private final ComboBox cbResult = new ComboBox();
    private final Button btnValidate = new Button();
    private final GridPane btnGrid = new GridPane();//gere les boutons
    private PopUpDelete popup;

    public View(Stage primaryStage, Controller ctrl) {
        this.ctrl = ctrl;
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
        tournamentsList.setMinHeight(50);
        subsList.getSelectionModel().select(-1);
        tournamentsList.setPrefWidth(TEXTSIZE);
        matchesList.setPrefWidth(TEXTSIZE);
    }

    private void configDisplay() {
        displayZone.setPadding(new Insets(SPACING));
        left.setPadding(new Insets(20, 10, 20, 20));
        right.setPadding(new Insets(20, 20, 20, 10));
        right.setHgap(10);
        left.add(new Label("les tournois"), 0, 0);
        left.add(new Label("les inscrits"), 0, 2);
        right.add(new Label("les matchs"), 0, 0);
        left.add(tournamentsList, 0, 1);
        left.add(subsList, 0, 3);
        right.add(matchesList, 0, 1);
        right.add(btnGrid, 0, 2);
        displayZone.getChildren().addAll(left, right);
    }

    private void configBottomZone() {
        btnGrid.setVgap(4);
        btnGrid.setHgap(30);
        btnGrid.setPadding(new Insets(20, 0, 0, 20));
        btnGrid.add(new Label("joueur 1: "), 0, 0);
        btnGrid.add(cbPlayersList, 1, 0);
        btnGrid.add(new Label("joueur 2: "), 2, 0);
        btnGrid.add(cbOppList, 3, 0);
        btnGrid.add(new Label("Resultat "), 4, 0);
        btnGrid.add(cbResult, 5, 0);
        btnValidate.setText("valider");
        btnGrid.add(btnValidate, 6, 0);
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

        this.matchesList.getColumns().addAll(player1, player2, results);

    }

    // ajoute un listener sur differents elements.
    private void configFocusListener() {
        tournamentsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    int index = tournamentsList.getSelectionModel().getSelectedIndex();

                    ctrl.setIndex(index);

                });
        subsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                });
        matchesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Match m = (Match) matchesList.getSelectionModel().getSelectedItem();
                        int index = matchesList.getSelectionModel().getSelectedIndex();

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
        cbPlayersList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                    Player p = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
                    ctrl.setPlayer(p);
                    if (cbPlayersList.getSelectionModel().isEmpty() || cbOppList.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    }
                });

        cbOppList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                    Player p = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
                    ctrl.setPlayer(p);
                    if (cbPlayersList.getSelectionModel().isEmpty() || cbOppList.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        cbResult.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbPlayersList.getSelectionModel().isEmpty() || cbOppList.getSelectionModel().isEmpty() || cbResult.getSelectionModel().isEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        btnValidate.setOnAction((ActionEvent event) -> {
            Player p1 = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
            Player p2 = (Player) cbOppList.getSelectionModel().getSelectedItem();
            RESULTS res = (RESULTS) cbResult.getSelectionModel().getSelectedItem();
            ctrl.createMatch(p1, p2, res);
        });

    }

    private void setButtonDisable(boolean b) {
        btnValidate.setDisable(b);
    }

    @Override
    public void update(java.util.Observable o, Object o1) {
        TournamentFacade facade = ctrl.getFacade();
        TournamentFacade.TypeNotif typeNotif = (TournamentFacade.TypeNotif) o1;

        switch (typeNotif) {
            case INIT:
                ObservableList<Player> sub1 = FXCollections.observableArrayList(facade.getSubscrib());

                tournamentsList.getItems().clear();
                subsList.getItems().clear();
                matchesList.getItems().clear();

                for (Tournament t : facade.getTournamentList()) {
                    tournamentsList.getItems().add(t);
                }
                for (Player p : facade.getSubscrib()) {
                    subsList.getItems().add(p);
                }
                for (Match m : facade.getMatchList()) {
                    matchesList.getItems().add(m);
                }
                tournamentsList.getSelectionModel().select(ctrl.getTournament());
                cbPlayersList.setItems(sub1);
                cbOppList.setItems(sub1);
                setButtonDisable(true);

                break;

            case TOURNAMENT_SELECTED:
                ObservableList<Player> sub = FXCollections.observableArrayList(facade.getSubscrib());
                subsList.getItems().clear();
                matchesList.getItems().clear();
                cbPlayersList.getItems().clear();
                cbOppList.getItems().clear();
                cbResult.getSelectionModel().clearSelection();

                subsList.getItems().addAll(facade.getSubscrib());
                for (Match m : facade.getMatchList()) {
                    matchesList.getItems().add(m);
                }
                cbPlayersList.setItems(sub);
                cbOppList.setItems(sub);
                break;

            case PLAYER_ONE_SELECTED:

                ObservableList<Player> sub3 = FXCollections.observableArrayList(facade.addOppponentValidList());
                cbOppList.setItems(sub3);

                break;

            case ADD_MATCH:
                matchesList.getItems().clear();
                for (Match m : facade.getMatchList()) {
                    matchesList.getItems().add(m);
                }
                cbPlayersList.getSelectionModel().clearSelection();
                cbOppList.getSelectionModel().clearSelection();
                cbResult.getSelectionModel().clearSelection();

                break;

            case REMOVE_MATCH:
                try {
                    Match m = ctrl.getSelectedMatch();

                    popup = new PopUpDelete(m, ctrl);

                } catch (FileNotFoundException e) {
                    System.out.println("Fichier introuvable");
                }
                matchesList.getItems().clear();
                for (Match m : facade.getMatchList()) {
                    matchesList.getItems().add(m);
                }
                break;

        }
    }
}
