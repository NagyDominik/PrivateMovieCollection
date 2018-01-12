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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

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
    @FXML
    private Button addCatToMovie;
    @FXML
    private ListView<Category> movieCategories;
    @FXML
    private ListView<Category> allCetegories;
    @FXML
    private Button removeCatFromMovie;

    private Model model;
    private Movie newmovie = new Movie();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = model.getInstance();
        allCetegories.setItems(model.getCategoriesFromList());
        movieCategories.setItems(newmovie.getCategories());
    }

    @FXML
    private void fileChooseClicked(ActionEvent event) {
        try
        {
            FileChooser filech = new FileChooser();
            URI path = filech.showOpenDialog(new ContextMenu()).toURI();
            if (path.toString().endsWith(".mp4") || path.toString().endsWith("mpeg4")) // Only allow .mp4 and .mpeg4 files
            {
                pathField.setText(path.toString());   
            }
            else
            {
                throw new Exception("Only .mp4 and .mpeg4 files allowed");
            }
        }
        catch (Exception ex) 
        {
            newAlert(ex);
        }
    }

    @FXML
    private void addCatToMovie(ActionEvent event) {
        Category selected = allCetegories.getSelectionModel().getSelectedItem();
        if (!newmovie.hasCategory(selected)) {
            newmovie.addCategory(selected);
        }
    }

    @FXML
    private void saveClicked(ActionEvent event) {
        saveMovie();
        closeStage();
    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        closeStage();
    }

    @FXML
    private void removeCatFromMovie(ActionEvent event) {
        Category selected = movieCategories.getSelectionModel().getSelectedItem();
        newmovie.removeCategory(selected);
    }

    private void closeStage() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void saveMovie() {
        try {
            newmovie.setName(titleField.getText());
            newmovie.setImdbRating(Float.parseFloat(imdbField.getText()));
            newmovie.setPersonalRating(Float.parseFloat(pratingField.getText()));
            newmovie.setPath(pathField.getText());

            model.saveMovie(newmovie);
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

}
