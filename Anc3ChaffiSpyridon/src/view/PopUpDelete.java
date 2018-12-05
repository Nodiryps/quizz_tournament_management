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

    private static Controller ctrl;
    private static Match match;

    public static void display(Match m, Controller ctrl) throws FileNotFoundException {
        PopUpDelete.ctrl = ctrl;
        PopUpDelete.match = m;
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Confirmation Suppression");

        FileInputStream input = new FileInputStream("src/view/img.png");

        Image img = new Image(input);
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(50);
        imgV.setFitWidth(50);

        Label question = new Label("Souhaitez-vous supprimer ce match?");
        Label displayMatch = new Label("Suppression du match entre: " + m.getPlayer1().getFirstName() + " et " + m.getPlayer2().getFirstName());
        VBox images = new VBox();

        Button btnDel = new Button("Supprimer");
        Button btnCancel = new Button("Annuler");
        btnCancel.setOnAction(e -> popUpWindow.close());

        VBox layout = new VBox();
        HBox hbMatch = new HBox();
        HBox hbBottom = new HBox();
        HBox btns = new HBox();
        GridPane gp = new GridPane();

        hbBottom.setPadding(new Insets(20.0));
        hbMatch.setPadding(new Insets(20.0));

        gp.add(question, 0, 0);
        gp.add(btns, 2, 1);
        Paint backgroundPaint = Color.LIGHTGRAY;
        Paint backgroundPaint2 = Color.LIGHTGREY;

        hbMatch.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        hbMatch.setBackground(new Background(new BackgroundFill(backgroundPaint2, CornerRadii.EMPTY, Insets.EMPTY)));

        layout.getChildren().addAll(hbMatch, hbBottom);
        hbMatch.getChildren().addAll(displayMatch, imgV);
        btns.getChildren().addAll(btnDel, btnCancel);
        hbBottom.getChildren().add(gp);

        btnDel.setOnAction((ActionEvent event) -> {
            ctrl.DelMatch(m);
            popUpWindow.close();
                     
        });

        Scene scene = new Scene(layout, 400, 200);
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }

}
