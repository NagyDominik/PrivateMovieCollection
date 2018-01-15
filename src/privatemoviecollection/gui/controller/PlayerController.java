package privatemoviecollection.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

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
        model.setupPlayer(model.getSelectedMovie());
        mediaView.setMediaPlayer(model.getPlayer());
        valueChanger();

    }

    /**
     * Sets the seek bar's max property to the length of the movie and plays it
     */
    @FXML
    private void playClick(MouseEvent event) {
        try {
            if (!isPlaying) {
                double d = media.getDuration().toSeconds();
                slider.setMax(d);
                isPlaying = true;
            }
            model.playBuiltIn();
        }
        catch (ModelException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pauses the player 
     */
    @FXML
    private void pauseClick(MouseEvent event) {
        model.pauseBuiltIn();
    }

    /**
     * Goes to the sliders position in the movie
     */
    @FXML
    private void changedPosition(MouseEvent event) {
        model.seekBuiltIn(slider.getValue());
    }

    /**
     * Formats time given time in milliseconds to HH:mm:ss formated string
     * 
     * @param time Time in milliseconds to be formated
     * @return Formated string
     */
    private String formatTime(double time) {
        long ms = (long) time;
        String timestring = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(ms),
                TimeUnit.MILLISECONDS.toMinutes(ms) % 60,
                TimeUnit.MILLISECONDS.toSeconds(ms) % 60);
        return timestring;
    }
    
        /**
     * Display a new alert window, to notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    /**
     * Sets up a ChangeListener that sets the slider's value to the movie's position.
     */
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

}
