/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;

/**
 * FXML Controller class
 *
 * @author sebok
 */
public class EditRatingController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblCurrentRating;
    @FXML
    private TextField txtFieldNewRating;
    @FXML
    private Button btnExit;

    private Model model = Model.getInstance();
    private Movie selectedMovie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedMovie = model.getSelectedMovie();
        lblTitle.setText(selectedMovie.getName());
        lblCurrentRating.setText(Float.toString(selectedMovie.getPersonalRating()));
    }

    /**
     * Tries to parse the number given by the user, and save it as the new
     * personal rating, if successful
     */
    @FXML
    private void btnSaveClick(ActionEvent event) {
        try {
            float rating = Float.parseFloat(txtFieldNewRating.getText());
            if (rating < 0 || rating > 10) {
                throw new Exception("Rating must be between 0.0 and 10.0!");
            }
            selectedMovie.setPersonalRating(rating);
            model.updateMovie(selectedMovie);
            closeStage();
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Closes the window
     */
    @FXML
    private void btnExitClick(ActionEvent event) {
        closeStage();        
    }

    /**
     * Displays a new alert window, to notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    private void closeStage() {
        Stage current = (Stage) btnExit.getScene().getWindow();
        current.close();
    }
}
