package privatemoviecollection.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * Controls the NewMovie window.
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
    private JFXButton cancelBtn;
    @FXML
    private TextField pathField;
    @FXML
    private ListView<Category> movieCategories;
    @FXML
    private ListView<Category> allCetegories;
    @FXML
    private TextField txtFieldImage;

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

    /**
     * Opens a FileChooser where we can select the movie we want to add
     */
    @FXML
    private void fileChooseClicked(ActionEvent event) {
        try {
            FileChooser filech = new FileChooser();
            URI path = filech.showOpenDialog(new ContextMenu()).toURI();
            if (path.toString().endsWith(".mp4") || path.toString().endsWith("mpeg4")) { // Only allow .mp4 and .mpeg4 files
                pathField.setText(path.toString());
            } else {
                throw new Exception("Only .mp4 and .mpeg4 files allowed");
            }
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Adds the selected category to the movie
     */
    @FXML
    private void addCatToMovie(ActionEvent event) {
        Category selected = allCetegories.getSelectionModel().getSelectedItem();
        if (!newmovie.hasCategory(selected)) {
            newmovie.addCategory(selected);
        }
    }

    /**
     * Attempts to save the new movie to the database and closes the window
     */
    @FXML
    private void saveClicked(ActionEvent event) {        
        saveMovie();
        closeStage();
    }

    /**
     * Closes the window without saving
     */
    @FXML
    private void cancelClicked(ActionEvent event) {
        closeStage();
    }

    /**
     * Removes the selected category from the movie
     */
    @FXML
    private void removeCatFromMovie(ActionEvent event) {
        Category selected = movieCategories.getSelectionModel().getSelectedItem();
        newmovie.removeCategory(selected);
    }

    /**
     * Select an optional image for the movie
     */
    @FXML
    private void btnImageFIleChooserClicked(ActionEvent event) {
        try {
            FileChooser filech = new FileChooser();
            URI path = filech.showOpenDialog(new ContextMenu()).toURI();
            if (path.toString().endsWith(".jpeg") || path.toString().endsWith(".jpg") || path.toString().endsWith(".png")) { // Only allow .mp4 and .mpeg4 files
                txtFieldImage.setText(path.toString());
            } else {
                throw new Exception("Only .jpeg and .png files allowed");
            }
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Creates a temporary Movie class, fills it with the data the user provided
     * and saves it to the database
     */
    private void saveMovie() {
        
        try {
            newmovie.setName(titleField.getText());
            newmovie.setImdbRating(Float.parseFloat(imdbField.getText()));
            newmovie.setPersonalRating(Float.parseFloat(pratingField.getText()));
            newmovie.setPath(pathField.getText());
            newmovie.setFileAccessDate(new Timestamp(System.currentTimeMillis()));
            newmovie.setImagePath(txtFieldImage.getText().isEmpty() ? "None" : txtFieldImage.getText());
            newmovie.createImage();
            
            if (checkForSimilarMovies(newmovie))
            {
                model.saveMovie(newmovie);   
            }            
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Display a new alert window, to notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Check for similar movies, and if there are, ask the user if they want to save anyway 
     * @param newmovie The movie that will be checked against the already saved movies
     * @return True if the user wants to save the movie, or if there are no similar movie, False otherwise
     */
    private boolean checkForSimilarMovies(Movie newmovie)
    {
        if (!model.checkSimilarities(newmovie).isEmpty())
        {          
            ButtonType showList = new ButtonType("Show");
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "There is atleast one movie with a similar name. Save anyway? (Click Show to see the similar movies)", 
                                            ButtonType.YES, ButtonType.NO, showList);
            confirmation.showAndWait();  
            
            while(confirmation.getResult() == showList) //Show a list of similar movies
            {
                try
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/SimilarMoviesList.fxml"));
                    Parent root = (Parent) loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Similar movies");
                    stage.showAndWait();
                } catch (IOException ex)
                {
                    newAlert(ex);
                }
                
                confirmation.showAndWait();
            }
            return confirmation.getResult() == ButtonType.YES;
        }
        return true;
    }
}
