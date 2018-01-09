/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.DAException;
import privatemoviecollection.dal.DALManager;

/**
 *
 * @author Dominik
 */
public class Model {
    DALManager dalManager = new DALManager();
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

    public void removeMedia(Movie selected) throws DAException {
        for (int i = 0; i < movieList.size(); i++) {
          if(movieList.get(i).equals(selected))
           {
            movieList.remove(i);
            dalManager.delet(selected);
           }
        }
                
                
    }
}
