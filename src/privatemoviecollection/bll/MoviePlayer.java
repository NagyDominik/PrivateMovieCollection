/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import privatemoviecollection.be.Movie;

/**
 *
 * @author Dominik,Bence
 */
public class MoviePlayer {

    private Media media;
    private MediaPlayer player;

    public void setupPlayer(Movie movie) {
        this.media = new Media(movie.getPath());
        player = new MediaPlayer(media);
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void playBuiltIn() {
        player.play();
    }

    public void pauseBuiltIn() {
        player.pause();
    }

    public void seekBuiltIn(double value) {
        player.seek(Duration.seconds(value));
    }

    public void stopBuiltIn() {
        player.stop();
    }

    /**
     * Creates a patch to the movie file and opens it with the system default
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

}
