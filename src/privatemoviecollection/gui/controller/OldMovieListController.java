package privatemoviecollection.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * Controls the OldMovieList window.
 *
 * @author sebok
 */
public class OldMovieListController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private TableColumn<Movie, String> colName;
    @FXML
    private TableColumn<Movie, String> colImdbRating;
    @FXML
    private TableColumn<Movie, String> colPersonalRating;
    @FXML
    private TableColumn<Movie, String> colCategories;
    @FXML
    private TableColumn<Movie, String> colLastView;
    @FXML
    private TableView<Movie> tableViewOldMovies;

    private Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        setUpLabel();
        setUpCellValueFatories();
        tableViewOldMovies.setItems(model.getUtilityList());
    }

    /**
     * Set up the cell value factories, to display the correct information in
     * the columns
     */
    private void setUpCellValueFatories() {
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colImdbRating.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory("personalRating"));
        colCategories.setCellValueFactory(new PropertyValueFactory("categoriesAsString"));
        colLastView.setCellValueFactory(new PropertyValueFactory("fileAccessDate"));
    }

    /**
     * Set the text of the label based on how many old movies there are
     */
    private void setUpLabel() {
        String text = model.getUtilityList().size() == 1 ? "There is only one old movie.." : String.format("There are %d old movies.", model.getUtilityList().size());
        lblTitle.setText(text);
    }

    /**
     * Delete the selected movie.
     */
    @FXML
    private void btnDeleteClick(ActionEvent event) {
        Movie selected = tableViewOldMovies.getSelectionModel().getSelectedItem();

        try {
            if (selected == null) {
                throw new Exception("Please selecte a movie!");
            }

            model.removeMovie(selected);
            model.getUtilityList().remove(selected);
            setUpLabel();
        }
        catch (Exception ex) {
            newAlert(ex);
        }
    }

    /**
     * Delete all old movies.
     */
    @FXML
    private void btnDeleteAllClick(ActionEvent event) {
        try {
            for (Movie movie : model.getUtilityList()) {
                model.removeMovie(movie);
            }

            model.getUtilityList().clear();
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }

    /**
     * Close the window.
     */
    @FXML
    private void btnCancelClickk(ActionEvent event) {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close this window?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.NO) {
            return;
        }

        Stage stage = (Stage) tableViewOldMovies.getScene().getWindow();
        stage.close();
    }

    /**
     * Display a new alert window, using the message from an exception, to
     * notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

}
