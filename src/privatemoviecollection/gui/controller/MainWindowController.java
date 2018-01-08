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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import privatemoviecollection.be.Movie;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
