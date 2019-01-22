package view;

import controller.ViewModel;
import java.io.FileNotFoundException;
import java.util.Observer;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

public class View extends VBox {

    private Stage stage;
    private final ViewModel vm;
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
    private final Button btnClear = new Button();
    private final GridPane gpButtons = new GridPane();//gere les boutons
    private PopUpDelete popup;
    private IntegerProperty indexTournoi = new SimpleIntegerProperty(0);
    private StringProperty actualPlayer = new SimpleStringProperty();

    public View(Stage primaryStage, ViewModel ctrl) {
        this.vm = ctrl;
        this.stage = primaryStage;
        initData();
        tournamentsList.focusedProperty();
        configBinding();
        //configDataBindings();
        Scene scene = new Scene(displayZone, 1235, 500);
        //stage.setResizable(false);
        //stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Gestion de  Tournois");
        stage.setScene(scene);
    }

    public void initData() {
        configDisplay();
        configBottomZone();
        decor();
        addElemComboBox();
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

    public void addElemComboBox() {
        cbResult.getItems().addAll(
                RESULTS.EX_AEQUO,
                RESULTS.VAINQUEUR_J1,
                RESULTS.VAINQUEUR_J2
        );
    }

    public void configBinding() {
        subsList.itemsProperty().bindBidirectional(vm.subscribesListProperty());
        tournamentsList.itemsProperty().bind(vm.tournamantProperty());
        matchesList.itemsProperty().bindBidirectional(vm.matchsProperty());
        actualPlayer.bind(vm.actualProperty());
        cbPlayersList.itemsProperty().bind(vm.subscribesListProperty());
        cbOppList.itemsProperty().bind(vm.opponentsListProperty());
        indexTournoi.bindBidirectional(vm.indexTournamentProperty());
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
                   // this.indexTournoi.set(index);
//                    subsList.getItems().clear();
                    configBinding();
                    //vm.setIndex(index);
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

                        if (!vm.getAllMatch().isEmpty()) {
                            vm.setMatchSelected(m, index);
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
                    configBinding();
                    this.actualPlayer.setValue(p.getFirstName().get());
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    }
                });

        cbOppList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {

                    Player p = (Player) cbPlayersList.getSelectionModel().getSelectedItem(); //trie a partir de la premiere cb
                    this.actualPlayer.set(p.getFirstName().get());
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                    configBinding();
                });

        cbResult.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                    configBinding();
                });

        btnValidate.setOnAction((ActionEvent event) -> {
            Player p1 = (Player) cbPlayersList.getSelectionModel().getSelectedItem();
            Player p2 = (Player) cbOppList.getSelectionModel().getSelectedItem();
            RESULTS res = (RESULTS) cbResult.getSelectionModel().getSelectedItem();
            vm.createMatch(p1, p2, res);
            configBinding();
            clearComboBox();

        });

        btnClear.setOnAction((ActionEvent event) -> {
           clearComboBox();
        });

    }

    private void clearComboBox() {
        cbOppList.getSelectionModel().clearSelection();
        cbPlayersList.getSelectionModel().clearSelection();
        cbResult.getSelectionModel().clearSelection();
    }

    private void setButtonDisable(boolean b) {
        btnValidate.setDisable(b);
    }

    private boolean cbEmpty() {
        return cbPlayersList.getSelectionModel().isEmpty()
                || cbOppList.getSelectionModel().isEmpty()
                || cbResult.getSelectionModel().isEmpty();
    }

}
