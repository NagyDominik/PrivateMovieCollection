/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;

/**
 * FXML Controller class
 *
 * @author Dominik
 */
public class NewMovieController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextField imdbField;
    @FXML
    private TextField pratingField;
    @FXML
    private Button fileBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField pathField;
    
    private Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = model.getInstance();
    }

    @FXML
    private void fileChooseClicked(ActionEvent event) {
        FileChooser filech = new FileChooser();
        URI path = filech.showOpenDialog(new ContextMenu()).toURI();
    }

    @FXML
    private void saveClicked(ActionEvent event) {
        saveMovie();
    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        closeStage();
    }

    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            closeStage();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void saveMovie() {
        Movie newmovie = new Movie();
        newmovie.setName(titleField.getText());
        newmovie.setImdbRating(Float.parseFloat(imdbField.getText()));
        newmovie.setPersonalRating(Float.parseFloat(pratingField.getText()));
        newmovie.setPath(pathField.getText());
        
        model.saveMovie(newmovie);
    }
}
