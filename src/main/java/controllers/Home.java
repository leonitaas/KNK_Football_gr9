package controllers;

import app.Navigator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class Home implements Initializable {


    @FXML
    private AnchorPane slider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(-260); // Slider starts hidden

        // Automatic sliding animation when the view is loaded
        animateSlider(0);


    }

    private void animateSlider(double toX) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(toX);
        slide.play();

        slide.setOnFinished((ActionEvent e) -> {
            if (toX == 0) {

            } else {

            }
        });
    }
}