/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import privatemoviecollection.gui.model.Model;

/**
 * FXML Controller class
 *
 * @author Bence
 */
public class PlayerController implements Initializable {

    @FXML
    private Slider slider;
    @FXML
    private Button playClick;
    @FXML
    private MediaView mediaView;
    @FXML
    private Label timeLbl;

    private Model model;
    private Media media;
    private boolean isPlaying = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        if (model.getSelectedMovie() != null) {
            model.setupPlayer(model.getSelectedMovie());
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "No movie selected! Please select a movie and try again.", ButtonType.OK);
            a.show();
            return;
        }
        mediaView.setMediaPlayer(model.getPlayer());
        valueChanger();
    }

    @FXML
    private void playClick(MouseEvent event) throws MalformedURLException {
        if (!isPlaying) {
            double d = media.getDuration().toSeconds();
            slider.setMax(d);
            isPlaying = true;
        }
        model.playBuiltIn();
    }

    public void valueChanger() {
        MediaPlayer player = model.getPlayer();
        media = player.getMedia();
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                slider.setValue(newValue.toSeconds());
                timeLbl.setText(formatTime(player.getCurrentTime().toMillis()) + " / " + formatTime(media.getDuration().toMillis()));
            }
        });
    }

    @FXML
    private void pauseClick(MouseEvent event) {
        model.pauseBuiltIn();
    }

    @FXML
    private void changedPosition(MouseEvent event) {
        model.seekBuiltIn(slider.getValue());
    }

    private String formatTime(double time) {
        long ms = (long) time;
        String timestring = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(ms),
                TimeUnit.MILLISECONDS.toMinutes(ms) % 60,
                TimeUnit.MILLISECONDS.toSeconds(ms) % 60);
        return timestring;
    }

}
