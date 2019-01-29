package view;

import controller.ViewModel;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    private final Stage stage;
    private final ViewModel vm;
    private static final int TEXTSIZE = 400, SPACING = 10;
    private final ListView<Player> subsList = new ListView<>();
    private final TableView<Match> matchesList = new TableView<>();
    private final ListView<Tournament> tournamentsList = new ListView<>();
    private final HBox displayZone = new HBox();
    private final GridPane left = new GridPane();
    private final GridPane right = new GridPane();
    private final ComboBox<Player> cbPlayersList = new ComboBox<>();
    private final ComboBox<Player> cbOpponentsList = new ComboBox<>();
    private final ComboBox<String> cbResultsList = new ComboBox<>();
    private final Button btnValidate = new Button();
    private final Button btnClear = new Button();
    private final GridPane gpButtons = new GridPane();//gere les boutons
    private PopUpDelete popup;
    private final IntegerProperty indexTournament = new SimpleIntegerProperty();
    private final StringProperty actualPlayer = new SimpleStringProperty("");

    public View(Stage primaryStage, ViewModel ctrl) throws FileNotFoundException {
        this.vm = ctrl;
        this.stage = primaryStage;
        initData();
        configBindings();
        tournamentsList.focusedProperty();
        Scene scene = new Scene(displayZone, 1235, 500);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Gestion de  Tournois");
        stage.setScene(scene);
    }

    public void addResultsToCB() {
        cbResultsList.getItems().addAll(
                "EX_AEQUO",
                "VAINQUEUR_J1",
                "VAINQUEUR_J2"
        );
    }

    public void configBindings() {
        configBindingsView();
        configBindingAttributes();

    }

    public void configBindingsView() {
        subsList.itemsProperty().bindBidirectional(vm.subscribesListProperty());
        matchesList.itemsProperty().bindBidirectional(vm.matchsProperty());
        tournamentsList.itemsProperty().bind(vm.tournamantProperty());
        cbPlayersList.itemsProperty().bindBidirectional(vm.subscribesListProperty());
        cbOpponentsList.itemsProperty().bindBidirectional(vm.opponentsListProperty());
    }

    private void configBindingAttributes() {
        vm.indexTournament.bind(tournamentsList.getSelectionModel().selectedIndexProperty());
        vm.actualProperty().bind(cbPlayersList.getSelectionModel().selectedItemProperty());
        vm.combobox1Property().bind(cbPlayersList.getSelectionModel().selectedItemProperty());
        vm.combobox2Property().bind(cbOpponentsList.getSelectionModel().selectedItemProperty());
        vm.combobox3Property().bind(cbResultsList.getSelectionModel().selectedItemProperty());
        vm.indexMatchProperty().bind(matchesList.getSelectionModel().selectedIndexProperty());
        vm.matchSelected.bind(matchesList.getSelectionModel().selectedItemProperty());
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

        addToTableView(player1, player2, results);
    }

    private void addToTableView(TableColumn<Match, String> p1, TableColumn<Match, String> p2, TableColumn<Match, RESULTS> res) {
        matchesList.getColumns().add(p1);
        matchesList.getColumns().add(p2);
        matchesList.getColumns().add(res);
    }

    private void configFocusListener() throws FileNotFoundException {
        tournamentsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    vm.setTournois();
                    clearComboBox();
                    configBindingsView();
                });
        subsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                });
        matchesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2 && matchesList.getSelectionModel().getSelectedItem() != null) {
                        try {
                            vm.launchPopUp();
                            clearComboBox();
                            vm.clearOppList();
                            vm.oppList();
                            cbPlayersList.itemsProperty().unbindBidirectional(vm.subscribesListProperty());
                            cbOpponentsList.itemsProperty().unbindBidirectional(vm.opponentsListProperty());
                            configBindings();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        matchesList.getSelectionModel().clearSelection();
                        configBindings();

                    }
                }
            }
        });
    }

    // ajoute un listener sur les combobox.
    public void addListernerComboBox() {
        cbPlayersList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    }
                    if (!cbPlayersList.getSelectionModel().isEmpty()) {
                        vm.oppValidList();
                    } else {
                        clearComboBox();
                    }
                });
        cbOpponentsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });
        cbResultsList.getSelectionModel().selectedIndexProperty()
                .addListener((Observable o) -> {
                    if (cbEmpty()) {
                        setButtonDisable(true);
                    } else {
                        setButtonDisable(false);
                    }
                });
        btnValidate.setOnAction((ActionEvent event) -> {
            if (cbOpponentsList.getSelectionModel().getSelectedItem() != null) {
                vm.createMatch();
                vm.clearOppList();
                vm.oppValidList();
                cbOpponentsList.itemsProperty().unbindBidirectional(vm.opponentsListProperty());
                clearComboBox();
                configBindings();
            }
        });
        btnClear.setOnAction((ActionEvent event) -> {
            clearComboBox();
            
            
        });

    }

    private void clearComboBox() {
        cbPlayersList.setValue(new Player(""));
        cbOpponentsList.setValue(new Player(""));
        cbResultsList.setValue(null);
    }

    private void setButtonDisable(boolean b) {
        btnValidate.setDisable(b);
    }

    private boolean cbEmpty() {
        return cbPlayersList.getSelectionModel().isEmpty()
                || cbOpponentsList.getSelectionModel().isEmpty()
                || cbResultsList.getSelectionModel().isEmpty();
    }

    public void initData() throws FileNotFoundException {
        configDisplay();
        configBottomZone();
        decor();
        tableViewColumnConfig();
        configFocusListener();
        addListernerComboBox();
        addResultsToCB();

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
        gpButtons.add(cbOpponentsList, 3, 0);
        gpButtons.add(new Label("Resultat "), 4, 0);
        gpButtons.add(cbResultsList, 5, 0);
        btnValidate.setText("valider");
        btnClear.setText("annuler");
        gpButtons.add(btnValidate, 6, 0);
        gpButtons.add(btnClear, 7, 0);
    }
}
