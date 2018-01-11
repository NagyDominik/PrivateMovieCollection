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
    private Button cancelBtn;

    private Model model;
    @FXML
    private Button okBtn;
    @FXML
    private ListView<?> categoryList;
    @FXML
    private Button addBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();
    }

    private void btnSaveClick(ActionEvent event) {
        try {
            Category newcat = new Category();
            newcat.setName(nameField.getText());
            model.addCategory(newcat);
            closeStage();
        }
        catch (ModelException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            a.show();
        }
    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnOkClick(ActionEvent event) {
    }

    @FXML
    private void addCategory(ActionEvent event) {
    }
}
