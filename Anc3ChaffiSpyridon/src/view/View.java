package view;

import presenter.Presenter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
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
import javafx.stage.StageStyle;

import model.*;

public class View extends VBox implements ViewInterface {

    private Stage stage;
    private final Presenter presenter;
    private static final int TEXTSIZE = 400, SPACING = 10;
    private final ListView<Player> subsList = new ListView<>();
    private final TableView<Match> matchesList = new TableView<>();
    private final ListView<Tournament> tournamentsList = new ListView<>();
    private final HBox displayZone = new HBox();
    private final GridPane left = new GridPane();
    private final GridPane right = new GridPane();
    private final ComboBox<Player> cbPlayersList = new ComboBox<>();
    private final ComboBox<Player> cbOppList = new ComboBox<>();
    private final ComboBox<RESULTS> cbResult = new ComboBox<>();
    private final Button btnValidate = new Button();
    private final Button btnClear = new Button();
    private final GridPane gpButtons = new GridPane();//gere les boutons
    private PopUpDelete popup;

    public View(Stage primaryStage, Presenter presenter) {
        this.presenter = presenter;
        this.stage = primaryStage;
        initData();
        Scene scene = new Scene(displayZone, 1235, 500);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Gestion de  Tournois");
        stage.setScene(scene);
    }

    private void initData() {
        configDisplay();
        configBottomZone();
        decor();
        addResultsToComboBox();
        tableViewColumnConfig();
        configFocusListener();
        addListernerComboBox();
    }

    private void decor() {
        tournamentsList.setMinHeight(50);
        subsList.getSelectionModel().select(-1);
        tournamentsList.setPrefWidth(TEXTSIZE);
        matchesList.setPrefWidth(TEXTSIZE);
    }

    private void configDisplay() {
        displayZone.setPadding(new Insets(SPACING));
        left.setPadding(new Insets(10, 5, 10, 10));
        right.setPadding(new Insets(10, 10, 10, 5));
        right.setHgap(10);
        left.add(new Label("les tournois"), 0, 0);
        left.add(new Label("les inscrits"), 0, 2);
        right.add(new Label("les matchs  (Double clic pour supprimer un tournois)"), 0, 0);
        left.add(tournamentsList, 0, 1);
        left.add(subsList, 0, 3);
        right.add(matchesList, 0, 1);
        right.add(gpButtons, 0, 2);
        displayZone.getChildren().addAll(left, right);
    }

    private void configBottomZone() {
        gpButtons.setVgap(4);
        gpButtons.setHgap(30);
        gpButtons.setPadding(new Insets(20, 0, 0, 20));
        gpButtons.add(new Label("joueur 1: "), 0, 0);
        gpButtons.add(cbPlayersList, 1, 0);
        gpButtons.add(new Label("joueur 2: "), 2, 0);
        gpButtons.add(cbOppList, 3, 0);
        gpButtons.add(new Label("Resultat "), 4, 0);
        gpButtons.add(cbResult, 5, 0);
        btnValidate.setText("valider");
        btnClear.setText("annuler");
        gpButtons.add(btnValidate, 6, 0);
        gpButtons.add(btnClear, 7, 0);
    }

    private void addResultsToComboBox() {
        cbResult.getItems().add(presenter.getresults().EX_AEQUO);
        cbResult.getItems().add(presenter.getresults().VAINQUEUR_J1);
        cbResult.getItems().add(presenter.getresults().VAINQUEUR_J2);

    }

    public void tableViewColumnConfig() {
        TableColumn<Match, String> player1 = new TableColumn<>("Joueur 1");
        player1.setMinWidth(133);
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));

        TableColumn<Match, String> player2 = new TableColumn<>("Joueur 2");
        player2.setMinWidth(133);
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));

        TableColumn<Match, RESULTS> results = new TableColumn<>("Resultats");
        results.setMinWidth(133);
        results.setCellValueFactory(new PropertyValueFactory<>("results"));

        addToTableCol(player1, player2, results);
    }

    private void addToTableCol(TableColumn<Match, String> player1,TableColumn<Match, String> player2,TableColumn<Match, RESULTS> results) {
        this.matchesList.getColumns().add(player1);
        this.matchesList.getColumns().add(player2);
        this.matchesList.getColumns().add(results);
    }

    private void configFocusListener() {
        tournamentsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    int index = tournamentsList.getSelectionModel().getSelectedIndex();
                    presenter.setIndex(index);
                });

        subsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                });

        matchesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2 && matchesList.getSelectionModel().getSelectedItem() != null) {
                        Match m = (Match) matchesList.getSelectionModel().getSelectedItem();
                        int index = matchesList.getSelectionModel().getSelectedIndex();

                        if (!presenter.getAllMatch().isEmpty()) {
                            presenter.setMatchSelected(m, index);
                        }
                    }
                }
            }
        });
    }

    public void addListernerComboBox() {
        cbPlayersList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    Player p = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
                    presenter.setPlayer(p);
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    }
                });

        cbOppList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    Player p = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
                    presenter.setPlayer(p);
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        cbResult.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });

        btnValidate.setOnAction((ActionEvent event) -> {
            Player p1 = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
            Player p2 = (Player) cbOppList.getSelectionModel().getSelectedItem();
            RESULTS res = (RESULTS) cbResult.getSelectionModel().getSelectedItem();
            presenter.createMatch(p1, p2, res);
        });

        btnClear.setOnAction((ActionEvent event) -> {
            cbOppList.getSelectionModel().clearSelection();
            cbPlayersList.getSelectionModel().clearSelection();
            cbResult.getSelectionModel().clearSelection();
        });

    }

    private void setButtonDisable(boolean b) {
        btnValidate.setDisable(b);
    }

    private boolean cbEmpty() {
        return cbPlayersList.getSelectionModel().isEmpty()
                || cbOppList.getSelectionModel().isEmpty()
                || cbResult.getSelectionModel().isEmpty();
    }

    @Override
    public void initView(Set<Match> match, List<Player> subscribes, List<Tournament> tournament, Tournament tournois) {
        tournamentsList.getItems().clear();
        subsList.getItems().clear();
        matchesList.getItems().clear();

        ObservableList<Player> sub1 = FXCollections.observableArrayList(subscribes);
        for (Tournament t : tournament) {
            tournamentsList.getItems().add(t);
        }
        for (Player p : subscribes) {
            subsList.getItems().add(p);
        }
        for (Match m : match) {
            matchesList.getItems().add(m);
        }
        tournamentsList.getSelectionModel().select(tournois);
        cbPlayersList.setItems(sub1);
        cbOppList.setItems(sub1);
        setButtonDisable(true);
    }

    @Override
    public void selectedTournament(Set<Match> match, List<Player> subscribes) {
        ObservableList<Player> sub = FXCollections.observableArrayList(subscribes);
        subsList.getItems().clear();
        matchesList.getItems().clear();
        cbPlayersList.getItems().clear();
        cbOppList.getItems().clear();
        cbResult.getSelectionModel().clearSelection();

        subsList.getItems().addAll(subscribes);
        for (Match m : match) {
            matchesList.getItems().add(m);
        }
        cbPlayersList.setItems(sub);
        cbOppList.setItems(sub);

    }

    @Override
    public void playerOneSelected(List<Player> player_valid) {
        ObservableList<Player> sub3 = FXCollections.observableArrayList(player_valid);
        cbOppList.setItems(sub3);
    }

    @Override
    public void add_match(Set<Match> match) {
        matchesList.getItems().clear();
        for (Match m : match) {
            matchesList.getItems().add(m);
        }
        cbPlayersList.getSelectionModel().clearSelection();
        cbOppList.getSelectionModel().clearSelection();
        cbResult.getSelectionModel().clearSelection();

    }

    @Override
    public void removeMatch(Set<Match> match) {
        try {
            Match m = presenter.getSelectedMatch();
            popup = new PopUpDelete(m, presenter);

        } catch (FileNotFoundException e) {
            System.out.println("PopUp introuvable");
        }
        matchesList.getItems().clear();
        for (Match m : match) {
            matchesList.getItems().add(m);
        }
    }
}
