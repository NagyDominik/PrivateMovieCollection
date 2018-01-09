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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

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
    
    private MediaPlayer mp;
    private Media me;
    @FXML
    private MediaView mw;
    
    
    /*
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = new File("C:\\Users\\Bence\\Documents\\GitHub\\PrivateMovieCollection1\\Snowden.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mw.setMediaPlayer(mp);
    }    
    
    //public void listener


    @FXML
    private void playClick(MouseEvent event) throws MalformedURLException {
         double d = me.getDuration().toSeconds();
       
       slider.setMax(d);
        System.out.println(d);
       //slider.setValue(1);
        mp.play();
    }

    
    @FXML
    private void pauseClick(MouseEvent event) {
        String current = mp.currentTimeProperty().toString();
        System.out.println(current);
        System.out.println(me.durationProperty().toString());
        mp.pause();
    }
}*/
