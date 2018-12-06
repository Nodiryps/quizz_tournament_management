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

    private static Stage popUpWindow;
    private static Controller ctrl;
    private static Match match;
    private static Image img;
    protected static ImageView imgV;
    private static FileInputStream input;
    private static Label question;
    private static Label displayMatch;
    private static Button btnDel;
    private static Button btnCancel;
    private static VBox layout = new VBox();
    private static HBox hbTop = new HBox();
    private static HBox hbBottom = new HBox();
    private static HBox btns = new HBox();
    private static HBox hbImage = new HBox();
    private static GridPane gp = new GridPane();
    private static GridPane gp2 = new GridPane();

    public static void display(Match m, Controller ctrl) throws FileNotFoundException{
        PopUpDelete.ctrl = ctrl;
        PopUpDelete.match = m;
        initData(m);
        popUpWindow = new Stage();
        popUpWindow.setResizable(false);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Confirmation Suppression");
        Scene scene = new Scene(layout, 370, 200);
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }

    public static void initData(Match m) throws FileNotFoundException {

        configImage();
        configLabel(m);
        paint();
        configPop();
        boutonListerner(m);
    }

    public static void configPop() {
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

    public static void configImage() throws FileNotFoundException {
        input = new FileInputStream("src/view/img.png");
        img = new Image(input);
        imgV = new ImageView(img);
        imgV.setFitHeight(50);
        imgV.setFitWidth(50);
    }

    public static void configLabel(Match m) {
        question = new Label("Souhaitez-vous supprimer ce match?");
        displayMatch = new Label("Suppression du match entre: " + m.getPlayer1().getFirstName() + " et " + m.getPlayer2().getFirstName());

        btnDel = new Button("Supprimer");
        btnCancel = new Button("Annuler");
        btnCancel.setOnAction(e -> popUpWindow.close());
    }

    public static void paint() {
        Paint backgroundPaint = Color.LIGHTGRAY;
        Paint backgroundPaint2 = Color.LIGHTGREY;

        hbTop.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        hbTop.setBackground(new Background(new BackgroundFill(backgroundPaint2, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    public static void boutonListerner(Match m) {
        btnDel.setOnAction((ActionEvent event) -> {
            ctrl.DelMatch(m);
            popUpWindow.close();
        });
    }

}