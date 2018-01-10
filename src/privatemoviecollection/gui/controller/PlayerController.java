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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
    private MediaPlayer player;
    private Media media;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = new File("C:\\Users\\Bence\\Documents\\GitHub\\PrivateMovieCollection1\\Snowden.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        //changeListener();
        valueChanger();
        
        slider.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(slider.getValue()));
            }
        
        });
    }

    //public void listener
    @FXML
    private void playClick(MouseEvent event) throws MalformedURLException {
        double d = media.getDuration().toSeconds();

        slider.setMax(d);
        System.out.println(d);
        player.play();
    }

 /*   public void changeListener() {
        player.currentTimeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                slider.setValue(player.getCurrentTime().toSeconds());
            }
        });
    }*/
    public void valueChanger(){
        player.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
               slider.setValue(newValue.toSeconds());
            }
        });
    }
    
    @FXML
    private void pauseClick(MouseEvent event) {
        String current = player.currentTimeProperty().toString();
        System.out.println(current);
        System.out.println(media.durationProperty().toString());
        player.pause();
    }
   
    
    @FXML
    private void changedPosition(MouseEvent event) {
        player.pause();
        player.seek(Duration.seconds(slider.getValue()));
        slider.setValue(player.currentTimeProperty().getValue().toMillis());// player.currentTimeProperty().getValue().toMillis()
        
        
        
    }

}
