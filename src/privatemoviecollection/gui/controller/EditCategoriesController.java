/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * FXML Controller class
 *
 * @author sebok
 */
public class EditCategoriesController implements Initializable {

    @FXML
    private Label lblMovieTitle;
    @FXML
    private ListView<Category> lstViewCategories;
    @FXML
    private ListView<Category> lstViewMovieCategories;
    @FXML
    private JFXButton btnAddCategory;
    @FXML
    private JFXButton btnRemoveCategory;
    @FXML
    private JFXButton btnCancel;

    private Model model = Model.getInstance();
    private Movie selectedMovie;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedMovie = model.getSelectedMovie();
        lblMovieTitle.setText(selectedMovie.getName());
        lstViewCategories.setItems(model.getCategoriesFromList());
        lstViewMovieCategories.setItems(selectedMovie.getCategories());
    }

    /**
     * Adds the selected category to the movie
     */
    @FXML
    private void btnAddCategoryClicked(ActionEvent event) {
        Category selectedCat = lstViewCategories.getSelectionModel().getSelectedItem();
        //Do nothing, if no category has been selected
        if (selectedCat == null) {
            return;
        }
        //Do nothing, if the movie already contains the selected 
        if (selectedMovie.hasCategory(selectedCat)) {
            return;
        }
        selectedMovie.addCategory(selectedCat);
        try {
            model.addCategoryToMovie(selectedMovie, selectedCat);
        }
        catch (ModelException ex) {
            Logger.getLogger(EditCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Removes a category from a movie
     */
    @FXML
    private void btnRemoveCategoryClicked(ActionEvent event) {
        Category selectedCat = lstViewMovieCategories.getSelectionModel().getSelectedItem();
        //Do nothing if no category has been selected
        if (selectedCat == null) {
            return;
        }
        lstViewMovieCategories.getItems().remove(selectedCat);
        selectedMovie.removeCategory(selectedCat);
        try {
            model.removeCategoryFromMovie(selectedMovie, selectedCat);
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Closes the window
     */
    @FXML
    private void btnCancelClicked(ActionEvent event) {
        Stage thisStage = (Stage) btnCancel.getScene().getWindow();
        thisStage.close();
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }
}
