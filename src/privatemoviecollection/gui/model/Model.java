/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Movie;

/**
 *
 * @author Dominik
 */
public class Model {
    private static Model instance;
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();

    public Model() {
    }
    
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public ObservableList<Movie> getMovies() {
        return movieList;
    }
}
