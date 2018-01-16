package privatemoviecollection.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;

/**
 * Controls the SimilarMovieList window
 *
 * @author sebok
 */
public class SimilarMoviesListController implements Initializable
{

    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, String> colImdbRating;
    @FXML
    private TableColumn<Movie, String> colPersonalRating;
    @FXML
    private TableColumn<Movie, String> colCategories;
    @FXML
    private TableColumn<Movie, String> colLastView;
    @FXML
    private TableView<Movie> tableViewSimilarMovies;
    
    private Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = Model.getInstance();
        setCellValueFactories();
        tableViewSimilarMovies.setItems(model.getUtilityList());
    }    

    /**
     * Set the cell value factories to display data correctly.
     */
    private void setCellValueFactories()
    {
        colTitle.setCellValueFactory(new PropertyValueFactory("name"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory("personalRating"));
        colCategories.setCellValueFactory(new PropertyValueFactory("categoriesAsString"));
        colLastView.setCellValueFactory(new PropertyValueFactory("fileAccessDate"));
    }

    
}
