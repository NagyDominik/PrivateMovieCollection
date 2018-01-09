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
        
    private Model model;
    @FXML
    private Button sysdefBtn;
    @FXML
    private Button playhereBtn;
    @FXML
    private TableView<?> movieTable;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        createCellValueFactories();
        movieTable.setItems(model.getMovies());
    }
    
    private void createCellValueFactories() {
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        imdbCol.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        pRatingCol.setCellValueFactory(new PropertyValueFactory("personalRating"));
        catCol.setCellValueFactory(new PropertyValueFactory("categories"));
        lastViewedCol.setCellValueFactory(new PropertyValueFactory(""));
    }

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

    @FXML
    private void removeClicked(ActionEvent event) {
        if (showConfirmationDialog("Are you sure you want to delete this song?")) {
            return;
        }
        Movie selected = (Movie) movieTable.getSelectionModel().getSelectedItem();
        try {
            model.removeMedia(selected);
        }
        catch (ModelException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert(ex);
        }
    }

    @FXML
    private void editCatClicked(ActionEvent event) {
    }

    @FXML
    private void editPRatingClicked(ActionEvent event) {
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    @FXML
    private void searchClicked(ActionEvent event) {
    }

    @FXML
    private void playSysDef(ActionEvent event) {
    }

    @FXML
    private void playHere(ActionEvent event) {
        try {
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
    
    private boolean showConfirmationDialog(String prompt) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, prompt, ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        return confirmation.getResult() == ButtonType.NO;
        }
}
