package privatemoviecollection.bll;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import privatemoviecollection.be.Movie;

/**
 * THis class is responsible for playing the selected movie.
 * @author Dominik,Bence
 */
public class MoviePlayer {

    private Media media;    // The Media object that will be played.
    private MediaPlayer player; // The MediaPlayer that will play the selected movie.

    /**
     * Set up the media player using the selected movie.
     * @param movie The selected movie that will be used to set up the media player.
     */
    public void setupPlayer(Movie movie) {
        this.media = new Media(movie.getPath());
        player = new MediaPlayer(media);
    }

    /**
     * Return the media player.
     * @return The media player.
     */
    public MediaPlayer getPlayer() {
        return player;
    }

    /**
     * Use the built-in media player.
     */
    public void playBuiltIn() {
        player.play();
    }

    /**
     * Pause the playback.
     */
    public void pauseBuiltIn() {
        player.pause();
    }

    /**
     * Jump to the specified location during playback.
     * @param value The specified location.
     */
    public void seekBuiltIn(double value) {
        player.seek(Duration.seconds(value));
    }

    /**
     * Stop the playback.
     */
    public void stopBuiltIn() {
        player.stop();
    }

    /**
     * Creates a path to the movie file and opens it with the system default
     * media player
     *
     * @param selected The selected movie
     * @throws BLLException
     */
    public static void playSysDef(Movie selected) throws BLLException {
        try {
            String path = selected.getPath().replace("file:/", "").replace("/", "\\");
            File movie = new File(path);
            if (!movie.exists())
            {
                throw new BLLException("File does not exist! It might have been added on a different computer.");
            }
            Desktop.getDesktop().open(movie);
        }
        catch (IOException ex) {
            throw new BLLException(ex);
        }
    }

    void setBuiltInVolume(double value) {
        player.setVolume(value);
    }

}
