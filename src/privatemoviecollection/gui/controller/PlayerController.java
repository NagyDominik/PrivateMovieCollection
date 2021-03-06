package privatemoviecollection.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * Controls the video player.
 *
 * @author Bence
 */
public class PlayerController implements Initializable {

    @FXML
    private Slider slider;
    @FXML
    private MediaView mediaView;
    @FXML
    private Label timeLbl;
    @FXML
    private ImageView playIV;
    @FXML
    private StackPane moviePane;
    @FXML
    private JFXSlider volumeSlider;
    @FXML
    private JFXButton playClick;
    @FXML
    private AnchorPane movieAnchorPane;
    private Model model;
    private Media media;
    private boolean isStarted = false;  //True when the playback is started
    private boolean isPaused = false;   // True when the palyback is paused (but not stopped)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = Model.getInstance();
            model.setupPlayer(model.getSelectedMovie());
            mediaView.setMediaPlayer(model.getPlayer());
            setListeners();
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Sets the seek bar's max property to the length of the movie and plays it
     */
    @FXML
    private void playClick(MouseEvent event) {
        try {
            if (!isStarted) {
                double d = media.getDuration().toSeconds();
                slider.setMax(d);
                isStarted = true;
            }
            if (!isPaused) {
                playIV.setImage(new Image("/img/pause.png"));
                model.playBuiltIn();
                isPaused = true;
            } else {
                playIV.setImage(new Image("/img/play.png"));
                model.pauseBuiltIn();
                isPaused = false;
            }
        }
        catch (ModelException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void stopClick(MouseEvent event) {
        model.stopBuiltIn();
        isStarted = false;
        playIV.setImage(new Image("/img/play.png"));
        isPaused = false;
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
     * Sets up a ChangeListener that sets the slider's value to the movie's
     * current position, sets the volume with the volume slider and resizes the
     * movie player when the window is resized
     */
    public void setListeners() {
        MediaPlayer player = model.getPlayer();
        media = player.getMedia();

        //Listener that sets the slider's value to the movie's current position
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                slider.setValue(newValue.toSeconds());
                timeLbl.setText(formatTime(player.getCurrentTime().toMillis()) + " / " + formatTime(media.getDuration().toMillis()));
            }
        });

        //Listener that sets the movies volume to the volume sliders value
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volumeSlider.getValue() / 100);
            }
        });

        //Listener that sets the player's width and height to the window's width and height
        InvalidationListener resizeMediaView = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaView.setFitWidth(moviePane.getWidth());
                mediaView.setFitHeight(moviePane.getHeight());
            }
        };
        moviePane.heightProperty().addListener(resizeMediaView);
        moviePane.widthProperty().addListener(resizeMediaView);

    }
}
