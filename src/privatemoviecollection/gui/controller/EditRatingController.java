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
import privatemoviecollection.gui.model.ModelException;

/**
 * FXML Controller class
 *
 * @author sebok
 */
public class EditRatingController implements Initializable
{

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
    public void initialize(URL url, ResourceBundle rb)
    {
        selectedMovie = model.getSelectedMovie();
        lblTitle.setText(selectedMovie.getName());
        lblCurrentRating.setText(Float.toString(selectedMovie.getPersonalRating()));
    }    

    /**
     * Try to parse the number given by the user, and save it as the new personal rating, if successful 
     */
    @FXML
    private void btnSaveClick(ActionEvent event)
    {
        try
        {
            float rating = Float.parseFloat(txtFieldNewRating.getText());
            if (rating < 0 || rating > 10)
            {
                throw new Exception("Rating must be between 0.0 and 10.0!");
            }
            selectedMovie.setPersonalRating(rating);
            model.updateMovie(selectedMovie);
        }
        catch(NumberFormatException ex)
        {
            newAlert(ex);
        }
        catch(ModelException ex)
        {
            newAlert(ex);
        }
        catch(Exception ex)
        {
            newAlert(ex);
        }
    }

    @FXML
    private void btnExitClick(ActionEvent event)
    {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are yous oure you want to exit?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES)
        {
            Stage current = (Stage) btnExit.getScene().getWindow(); 
            current.close();
        }
    }
    
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }
    
}
