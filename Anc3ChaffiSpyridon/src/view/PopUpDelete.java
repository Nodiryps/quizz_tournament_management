/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ViewModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Match;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author 2707chshyaka
 */
public class PopUpDelete extends Popup {

    private Stage popUpWindow;
    private VBox vbLayout = new VBox();
    private GridPane gpTop = new GridPane();
    private GridPane gpBottom = new GridPane();
    private GridPane gpButtons = new GridPane();
    private ViewModel vm;
    private Match match;
    protected ImageView imgV;
    private Button btnDel = new Button("Supprimer");
    private Button btnCancel = new Button("Annuler");
    

    public PopUpDelete(Match m, ViewModel ctrl) throws FileNotFoundException {
        this.vm = ctrl;
        this.match = m;
        initData();
        popupWindowSettings();
    }

    private void popupWindowSettings() {
        Scene scene = new Scene(vbLayout, 325, 200);
        popUpWindow = new Stage();
        popupWindow();
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
        
    }
    
    private void popupWindow() {
        popUpWindow.setTitle("Confirmation Suppression");
        popUpWindow.setResizable(false);
        popUpWindow.initStyle(StageStyle.UNDECORATED);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
    }

    private void initData() throws FileNotFoundException {
        configImage();
        paint();
        configPop();
        buttonsListerner(match);
    }

    private void configImage() throws FileNotFoundException {
        imgV = new ImageView(new Image(new FileInputStream("src/img/img.png")));
        imgV.setFitHeight(50);
        imgV.setFitWidth(50);
        imgV.setOpacity(150);
        imgV.setLayoutX(75);
    }

    private void paint() {
        Paint backgroundPaint = Color.LIGHTGRAY;
        Paint backgroundPaint2 = Color.LIGHTGREY;
        gpTop.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        gpTop.setBackground(new Background(new BackgroundFill(backgroundPaint2, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void buttonsListerner(Match m) {
        btnDel.setOnAction((ActionEvent event) -> {
            vm.removeMatch();
            popUpWindow.close();
        });
        btnCancel.setOnAction(e -> popUpWindow.close());
    }

    private void configPop() {
        Label labelTop = configLabelTop();
        Label labelBottom = configLabelBottom();
        configLO();
        configGpTop(labelTop);
        configGpButtons();
        configGpBottom(labelBottom);
    }
    
    private void configGpButtons() {
        gpButtons.setHgap(20);
        gpButtons.add(btnDel, 0, 0);
        gpButtons.add(btnCancel, 1, 0);
    }
    
    private void configGpBottom(Label labelBottom) {
        gpBottom.setPadding(new Insets(10));
        gpBottom.setVgap(20);
        gpBottom.add(labelBottom, 0, 0);
        gpBottom.add(imgV, 2, 0);
        gpBottom.add(gpButtons, 0, 1);
    }
    
    private void configLO() {
        vbLayout.getChildren().addAll(gpTop, gpBottom);
        vbLayout.setStyle("-fx-border-color: #bfbfbf; -fx-border-width: 1; -fx-border-style: solid;");
        
    }
    
    private void configGpTop(Label labelTop) {
        gpTop.setPadding(new Insets(10));
        gpTop.setVgap(20);
        gpTop.add(labelTop, 0, 0);
    }
    
    private Label configLabelTop(){
        Label labelTop = new Label("Suppression du match entre: \n"
                + match.getPlayer1().getFirstName()
                + " et " + match.getPlayer2().getFirstName());
        labelTop.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        return labelTop;
    }
    
     private Label configLabelBottom(){
        Label labelBottom = new Label("Souhaitez-vous supprimer ce match");
        labelBottom.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        return labelBottom;
    }
    
}
