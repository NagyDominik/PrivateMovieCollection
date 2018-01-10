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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

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

    private MediaPlayer player;
    private Media media;
    private boolean isPlaying = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = new File("C:\\Users\\Bence\\Documents\\GitHub\\PrivateMovieCollection1\\Snowden.mp4").getAbsolutePath();
        //String path = new File("D:\\test2.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        valueChanger();
        /*slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(slider.getValue()));
                player.play();
            }
        });*/
    }

    @FXML
    private void playClick(MouseEvent event) throws MalformedURLException {
        if (!isPlaying) {
            double d = media.getDuration().toSeconds();
            slider.setMax(d);
            isPlaying = true;
        }
        player.play();
    }

    public void valueChanger() {
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
        player.pause();
    }

    @FXML
    private void changedPosition(MouseEvent event) {
        player.seek(Duration.seconds(slider.getValue()));
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
