/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.BLLException;
import privatemoviecollection.bll.BLLManager;

/**
 *
 * @author Dominik
 */
public class Model {

    private static Model instance;
    private BLLManager bllm = new BLLManager();
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

    public void saveMovie(Movie newmovie) throws ModelException {
        try {
            bllm.saveMovie(newmovie);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }
}
