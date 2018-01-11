/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.util.List;
import javafx.scene.media.MediaPlayer;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.DAException;
import privatemoviecollection.dal.DALManager;

/**
 *
 * @author Dominik
 */
public class BLLManager {

    private DALManager dalm = new DALManager();
    private MoviePlayer player = new MoviePlayer();

    /**
     * Calls the method in the DALManager that saves the movie to the database
     *
     * @param newmovie A new movie to be saved
     * @throws BLLException If an error occurs in the DALManager, during the
     * method call
     */
    public void saveMovie(Movie newmovie) throws BLLException {
        try {
            dalm.saveMovie(newmovie);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    public void deleteMovie(Movie selected) throws BLLException {
        try {
            dalm.deleteMovie(selected);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    public List<Movie> loadMovies() throws BLLException {
        try {
            return dalm.getMovies();
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }
    
    /**
     * Plays the selected movie with the system default video player
     * 
     * @param selected The selected movie
     * @throws BLLException If error occurs
     */
    public void playSysDef(Movie selected) throws BLLException {
        player.playSysDef(selected);
    }
    
    /**
     * Saves a new category to the database
     * 
     * @param cat The new category to be saved
     * @throws BLLException If error occurs
     */
    public void saveCategory(Category cat) throws BLLException {
        try {
            dalm.saveCategory(cat);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    /**
     * Loads the list of categories from the database
     *
     * @return The list of categories from the database
     * @throws BLLException If an error occurs during database access
     */
    public List<Category> loadCategories() throws BLLException {
        try {
            return dalm.getCategories();
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    /**
     * Associates a category with a movie
     *
     * @param selectedMovie The movie that will be updated
     * @param selectedCat The category that will be added to the movie
     * @throws BLLException If an error occurs during database access
     */
    public void addCategoryToMovie(Movie selectedMovie, Category selectedCat) throws BLLException {
        try {
            dalm.addCategoryToMovie(selectedMovie, selectedCat);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    /**
     * Removes the given category from the given movie
     *
     * @param selectedMovie The selected movie
     * @param selectedCat The selected category, that will be removed from the
     * given movie
     * @throws BLLException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMovie, Category selectedCat) throws BLLException {
        try {
            dalm.removeCategoryFromMovie(selectedMovie, selectedCat);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    public void setupPlayer(Movie selected) {
        player.setupPlayer(selected);
    }

    public MediaPlayer getPlayer() {
        return player.getPlayer();
    }

    public void playBuiltIn() {
        player.playBuiltIn();
    }

    public void pauseBuiltIn() {
        player.pauseBuiltIn();
    }

    public void seekBuiltIn(double value) {
        player.seekBuiltIn(value);
    }
}
