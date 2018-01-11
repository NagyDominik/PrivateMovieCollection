/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * FXML Controller class
 *
 * @author Bence
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button categoriesBtn;
    @FXML
    private Button ratingBtn;
    @FXML
    private Label imdbLbl;
    @FXML
    private Label nameLbl;
    @FXML
    private Label personalLbl;
    @FXML
    private Label categoriesLbl;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchBtn;
    @FXML
    private Label lastViewLbl;
    @FXML
    private TableColumn<Movie, String> nameCol;
    @FXML
    private TableColumn<Movie, String> imdbCol;
    @FXML
    private TableColumn<Movie, String> pRatingCol;
    @FXML
    private TableColumn<Movie, String> catCol;
    @FXML
    private TableColumn<Movie, String> lastViewedCol;
    @FXML
    private Button sysdefBtn;
    @FXML
    private Button playhereBtn;
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private Button addDeleteCategories;

    private Model model;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        loadMovies();
        addListenersAndHandlers();
        createCellValueFactories();
        movieTable.setItems(model.getMoviesFromList());
    }

    /**
     * Create cell value factories to properly display the values 
     */
    private void createCellValueFactories() {
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        imdbCol.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        pRatingCol.setCellValueFactory(new PropertyValueFactory("personalRating"));
        catCol.setCellValueFactory(new PropertyValueFactory("categoriesAsString"));
        //lastViewedCol.setCellValueFactory(new PropertyValueFactory("lastAccessTime")); //Will be enabled later
    }

    /**
     * Open a new window where the user can add a new movie to the database
     */
    @FXML
    private void addClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/NewMovie.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Movie");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Attempt to remove a movie from the list and the database
     */
    @FXML
    private void removeClicked(ActionEvent event) {
        try {
            if (showConfirmationDialog("Are you sure you want to delete this movie?")) {
                return;
            }
            Movie selected = (Movie) movieTable.getSelectionModel().getSelectedItem();
            model.removeMovie(selected);
        }
        catch (ModelException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Open a new window, where the user can assign or delete categories to and from a movie
     */
    @FXML
    private void editCatClicked(ActionEvent event) {
        try {
            Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
            if (selectedMovie == null) {
                throw new Exception("Please select a movie!");
            }
            model.setSelectedMovie(selectedMovie);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/EditCategories.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit categories");
            stage.setResizable(false);
            stage.showAndWait();
            movieTable.refresh(); //Refresh the table, so it displays the newly added categories
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Open a new window where the user can edit the personal rating of a movie
     */
    @FXML
    private void editPRatingClicked(ActionEvent event) {
        try
        {
           Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
            if (selectedMovie == null) {
                throw new Exception("Please selecta a movie!");
            }
            model.setSelectedMovie(selectedMovie);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/EditRating.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit categories");
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex)
        {
            newAlert(ex);
        }
    }

    /**
     * Search through the database for the movies that satisfy the search parameters
     */
    @FXML
    private void searchClicked(ActionEvent event) {
        movieTable.getItems().clear();
        try{
            model.search(searchBar.getText());
            
        }catch (ModelException ex) {
            newAlert(ex);
        }
        
    }

    /**
     * Attempt to play the selected movie with the default media player
     */
    @FXML
    private void playSysDef(ActionEvent event) {
        try {
            model.playSysDef(movieTable.getSelectionModel().getSelectedItem());
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Attempt to play the selected movie within the program
     */
    @FXML
    private void playHere(ActionEvent event) {
        try {
            model.setSelectedMovie(movieTable.getSelectionModel().getSelectedItem());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/Player.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("NEM INDUL");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Load the movies from the database
     */
    private void loadMovies() {
        try {
            model.load();
        }
        catch (ModelException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Add a listener to the movie table, so the labels can be updated when the selection changes
     */
    private void addListenersAndHandlers() {
        movieTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setLabels();
            }
        }
        );
    }

    /**
     * Set the labels do display the info of the selected movie
     */
    private void setLabels() {
        Movie tempmovie = movieTable.getSelectionModel().getSelectedItem();
        nameLbl.setText(tempmovie.getName());
        imdbLbl.setText("IMDb Rating: " + tempmovie.getImdbRating());
        personalLbl.setText("Personal Rating: " + tempmovie.getPersonalRating());
        categoriesLbl.setText("Categories: " + tempmovie.getCategoriesAsString());
        lastViewLbl.setText("Last Viewed: " + "WIP");// tempmovie.getLastAccessTime()); //This throws an error, because the createMediaFromPath is not called upon loading movies from the database
    }

    /**
     * Display a new alert window, to notify the user of some error
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    /**
     * Show a confirmation dialog with the specified prompt.
     * @param prompt The text that will be shown to the user
     * @return True, if the user selected the "Yes" button, false otherwise
     */
    private boolean showConfirmationDialog(String prompt) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, prompt, ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        return confirmation.getResult() == ButtonType.NO;
    }

    @FXML
    private void btnAddDeleteCategoriesClicked(ActionEvent event) {
    }
}
