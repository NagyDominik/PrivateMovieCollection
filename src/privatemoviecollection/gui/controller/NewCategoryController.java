/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import privatemoviecollection.be.Category;
import privatemoviecollection.gui.model.Model;
import privatemoviecollection.gui.model.ModelException;

/**
 * FXML Controller class
 *
 * @author Dominik
 */
public class NewCategoryController implements Initializable {
    
    @FXML
    private TextField nameField;
    @FXML
    private JFXButton okBtn;
    @FXML
    private ListView<Category> categoryList;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton deleteBtn;
    
    private Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
        categoryList.setItems(model.getCategoriesFromList());
    }
    
    @FXML
    private void btnOkClick(ActionEvent event) {
        closeStage();
    }
    
    /**
     * Attempts to save a new movie to the database
     */
    @FXML
    private void addCategory(ActionEvent event) {
        try {
            Category category = new Category();
            category.setName(nameField.getText());
            model.addCategory(category);
            categoryList.refresh();
        }
        catch (ModelException ex) {
            newAlert(ex);
        }
    }
    
    /**
     * Attempts to delete a selected category from the database
     */
    @FXML
    private void deleteCategory(ActionEvent event) {
        Category selected = categoryList.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
            try {
                model.removeCategory(selected);
                categoryList.refresh();
            }
            catch (ModelException ex) {
                newAlert(ex);
            }
        } else {
            newAlert(new Exception("No selected category!"));
        }
    }
    
    private void closeStage() {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Display a new alert window, to notify the user of some error
     *
     * @param ex The exception that carries the error message
     */
    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        a.show();
    }
}
