/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

    public static void playSysDef(Movie selected) throws BLLException {
        try {
            String path = selected.getPath().replace("file:/", "").replace("/", "\\");
            File movie = new File(path);
            Desktop.getDesktop().open(movie);
        }
        catch (IOException ex) {
            throw new BLLException(ex);
        }
    }

    public void stopBuiltIn() {
        player.stop();
    }
}
