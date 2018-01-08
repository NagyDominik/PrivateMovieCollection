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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<Movie> listView;
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
    
    private Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        listView.setItems(model.getMovies());
    }
    
    private void createCellValueFactories() {
        
    }

    @FXML
    private void addClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/view/NewMovie.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Movie");
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    @FXML
    private void removeClicked(ActionEvent event) {
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
}
