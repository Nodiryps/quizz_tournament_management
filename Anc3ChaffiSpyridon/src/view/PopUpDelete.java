/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Match;

/**
 *
 * @author 2707chshyaka
 */
public class PopUpDelete extends Popup {

    private Stage popUpWindow;
    private Controller ctrl;
    private Match match;
    private Image img;
    protected ImageView imgV;
    private FileInputStream input;
    private Label question;
    private Label displayMatch;
    private Button btnDel;
    private Button btnCancel;
    private VBox layout = new VBox();
    private HBox hbTop = new HBox();
    private HBox hbBottom = new HBox();
    private HBox btns = new HBox();
    private HBox hbImage = new HBox();
    private GridPane gp = new GridPane();
    private GridPane gp2 = new GridPane();

    public PopUpDelete(Match m, Controller ctrl) throws FileNotFoundException {
        this.ctrl = ctrl;
        this.match = m;
        initData();
        popUpWindow = new Stage();
        popUpWindow.setResizable(false);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Confirmation Suppression");
        Scene scene = new Scene(layout, 370, 200);
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();

    }

    public void initData() throws FileNotFoundException {
        configImage();
        configLabel();
        paint();
        configPop();
        boutonListerner(match);
    }

    public void configImage() throws FileNotFoundException {
        input = new FileInputStream("src/img/img.png");
        img = new Image(input);
        imgV = new ImageView(img);
        imgV.setFitHeight(50);
        imgV.setFitWidth(50);
    }

    public void configLabel() {
        question = new Label("Souhaitez-vous supprimer ce match?");
        displayMatch = new Label("Suppression du match entre: " + match.getPlayer1().getFirstName() + " et " + match.getPlayer2().getFirstName());
        btnDel = new Button("Supprimer");
        btnCancel = new Button("Annuler");
        btnCancel.setOnAction(e -> popUpWindow.close());
    }

    public void paint() {
        Paint backgroundPaint = Color.LIGHTGRAY;
        Paint backgroundPaint2 = Color.LIGHTGREY;

        hbTop.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        hbTop.setBackground(new Background(new BackgroundFill(backgroundPaint2, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    public void boutonListerner(Match m) {
        btnDel.setOnAction((ActionEvent event) -> {
            ctrl.DelMatch(m);
            popUpWindow.close();
        });
    }

    public void configPop() {
        hbBottom.setPadding(new Insets(20.0));
        hbTop.setPadding(new Insets(20.0));
        gp.add(question, 0, 0);
        gp.add(btns, 2, 1);
        gp2.add(displayMatch, 0, 0);
        gp2.add(hbImage, 1, 0);
        layout.getChildren().addAll(hbTop, hbBottom);
        hbImage.getChildren().add(imgV);
        imgV.setOpacity(150);
        imgV.setLayoutX(75);
        hbTop.getChildren().add(gp2);
        btns.getChildren().addAll(btnDel, btnCancel);
        hbBottom.getChildren().add(gp);
    }

}

