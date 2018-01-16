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
 *
 * @author Dominik,Bence
 */
public class MoviePlayer {

    private Media media;    // The Media object that will be played.
    private MediaPlayer player; // The MediaPlayer that will play the selected movie.

    /**
     * Set up the media player using the selected movie.
     *
     * @param movie The selected movie that will be used to set up the media
     * player.
     * @throws privatemoviecollection.bll.BLLException
     */
    public void setupPlayer(Movie movie) throws BLLException {
        String path = correctPath(movie.getPath());
        File moviefile = new File(path);
        this.media = new Media(moviefile.toURI().toString());
        if (!moviefile.exists()) {
            throw new BLLException("File cannot be found! It might have been added on a different computer.");
        }
        player = new MediaPlayer(media);
        player.setVolume(0.5);
    }

    /**
     * Return the media player.
     *
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
     *
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

    public void setBuiltInVolume(double value) {
        player.setVolume(value);
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
            String path = correctPath(selected.getPath());
            File movie = new File(path);
            if (!movie.exists()) {
                throw new BLLException("File cannot be found! It might have been added on a different computer.");
            }
            Desktop.getDesktop().open(movie);
        }
        catch (IOException ex) {
            throw new BLLException(ex);
        }
    }

    private static String correctPath(String path) {
        String corrected = path.replace("file:/", "").replace("/", "\\").replace("%20", " ")
                .replace("%25", "%").replace("%23", "#").replace("%5B", "[").replace("%5D", "]");
        return corrected;
    }

}
