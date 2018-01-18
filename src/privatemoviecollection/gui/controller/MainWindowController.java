package privatemoviecollection.gui.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * Controls the MainWindow and handles the events
 *
 * @author Bence
 */
public class MainWindowController implements Initializable {

    @FXML
    private Label imdbLbl;
    @FXML
    private Label nameLbl;
    @FXML
    private Label personalLbl;
    @FXML
    private Label categoriesLbl;
    @FXML
    private JFXTextField searchBar;
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
    private TableView<Movie> movieTable;
    @FXML
    private ImageView imgViewMovieImage;

    private Model model;
    private boolean isSearching = false;
    @FXML
    private ImageView searchIV;

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
        checkMovies();
        movieTable.setItems(model.getMoviesFromList());
        imgViewMovieImage.setImage(new Image("/img/no_cover.png"));
        setSearchBarEvent();
    }

    /**
     * Creates cell value factories to properly display the values
     */
    private void createCellValueFactories() {
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        imdbCol.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        pRatingCol.setCellValueFactory(new PropertyValueFactory("personalRating"));
        catCol.setCellValueFactory(new PropertyValueFactory("categoriesAsString"));
        lastViewedCol.setCellValueFactory(new PropertyValueFactory("fileAccessDate"));
    }

    /**
     * Opens a new window where the user can add a new movie to the database
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
     * Attempts to remove a movie from the list and the database
     */
    @FXML
    private void removeClicked(ActionEvent event) {
        try {
            if (!showConfirmationDialog("Are you sure you want to delete this movie?")) {
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
     * Opens a new window, where the user can assign or delete categories to and
     * from a movie
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
     * Opens a new window where the user can edit the personal rating of a movie
     */
    @FXML
    private void editPRatingClicked(ActionEvent event) {
        try {
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
            stage.showAndWait();
            movieTable.refresh();
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Searches through the database for the movies that satisfy the search
     * parameters
     */
    @FXML
    private void searchClicked(ActionEvent event) {
        try {
            if (!isSearching) {
                movieTable.setItems(model.getSearchedMovies());
                model.search(searchBar.getText());
                searchIV.setImage(new Image("/img/exit.png"));
                isSearching = true;
            } else {
                movieTable.setItems(model.getMoviesFromList());
                searchIV.setImage(new Image("/img/search.png"));
                searchBar.clear();
                isSearching = false;
            }
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Attempts to play the selected movie with the default media player
     */
    @FXML
    private void playSysDef(ActionEvent event) {
        try {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            if (movie == null) {
                newAlert(new Exception("Please select a movie"));
                return;
            }
            model.playSysDef(movie);
            movieTable.refresh();
            setLastViewLabel(movie);
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Attempts to play the selected movie within the program
     */
    @FXML
    private void playHere(ActionEvent event) {
        try {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();

            if (movie != null) {
                model.setSelectedMovie(movieTable.getSelectionModel().getSelectedItem());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/Player.fxml"));
                Parent root = (Parent) loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Built-In Media Player Beta_v2");
                stage.setResizable(true);
                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        model.stopBuiltIn();
                        movieTable.refresh();
                        setLastViewLabel(movie);
                    }
                });
            } else {
                newAlert(new Exception("No movie is selected! Please select a movie to play!"));
            }
        }
        catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    /**
     * Shows a new window where we can Add and Delete categories from the
     * database
     */
    @FXML
    private void btnAddDeleteCategoriesClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/NewCategory.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add / delete category");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            newAlert(ex);
        }
    }

    /**
     * Loads the movies from the database
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
     * Adds a listener to the movie table, so the labels can be updated when the
     * selection changes
     */
    private void addListenersAndHandlers() {
        movieTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setLabelsAndImageView();
            }
        }
        );
    }

    /**
     * Sets the labels do display the info of the selected movie
     */
    private void setLabelsAndImageView() {
        Movie tempmovie = movieTable.getSelectionModel().getSelectedItem();
        if (tempmovie != null) {
            nameLbl.setText(tempmovie.getName());
            imdbLbl.setText("IMDb Rating: " + tempmovie.getImdbRating());
            personalLbl.setText("Personal Rating: " + tempmovie.getPersonalRating());
            categoriesLbl.setText("Categories: " + tempmovie.getCategoriesAsString());
            setLastViewLabel(tempmovie);
            imgViewMovieImage.setImage(tempmovie.getImage());
        }
    }

    /**
     * Sets the label displaying the last access to a given movie to the actual
     * value. Used to refresh the UI.
     *
     * @param movie
     */
    private void setLastViewLabel(Movie movie) {
        lastViewLbl.setText("Last Viewed: " + movie.getFileAccessDate());
    }

    /**
     * Uses the model to look for movies that haven't been accesses for more
     * than 2 years, and have a personal rating lower than 6. Ask the user if
     * they should be deleted.
     */
    private void checkMovies() {
        if (model.checkMovies()) {
            Boolean answer = showConfirmationDialog("We foiund some old movies with personal rating lower than 6. Would you like to see a list of these movies?");            
            if (answer) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/OldMovieList.fxml"));
                    Parent root = (Parent) loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Old movies");
                    stage.setResizable(true);
                    stage.showAndWait();
                }
                catch (IOException ex) {
                    newAlert(ex);
                }
            }
        }
    }

    /**
     * Displays a new alert window, to notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    /**
     * Shows a confirmation dialog with the specified prompt.
     *
     * @param prompt The text that will be shown to the user
     * @return True, if the user selected the "Yes" button, false otherwise
     */
    private boolean showConfirmationDialog(String prompt) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, prompt, ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        return confirmation.getResult() == ButtonType.YES;
    }

    /**
     * Set an event handler that allows us to reset the search when the search bar is empty.
     */
    private void setSearchBarEvent()
    {
        searchBar.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (searchBar.getText().isEmpty())
                {
                    movieTable.setItems(model.getMoviesFromList());
                    searchIV.setImage(new Image("/img/search.png"));
                    searchBar.clear();
                    isSearching = false;
                }
            }
        });
    }

}
