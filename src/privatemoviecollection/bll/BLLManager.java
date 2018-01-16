package privatemoviecollection.bll;

import java.util.List;
import javafx.scene.media.MediaPlayer;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.DAException;
import privatemoviecollection.dal.DALManager;

/**
 * Connects the Model to the DAL and performs various operations.
 * @author Dominik
 */
public class BLLManager {

    private DALManager dalm = new DALManager();
    private MoviePlayer player = new MoviePlayer();

    /**
     * Database methods*********************************************************
     */
    
    /**
     * Calls the method in the DALManager that loads the movies from the
     * database
     *
     * @return
     * @throws BLLException
     */
    public List<Movie> loadMovies() throws BLLException {
        try {
            return dalm.getMovies();
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

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

    /**
     * Calls the method in the DALManager that deletes the selected movie from
     * the database
     *
     * @param selected The selected movie
     * @throws BLLException
     */
    public void deleteMovie(Movie selected) throws BLLException {
        try {
            dalm.deleteMovie(selected);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    /**
     * Attempt to update the given movie in the database
     *
     * @param selectedMovie The move that will be updated
     * @throws BLLException If an error occurs during database access
     */
    public void updateMovie(Movie selectedMovie) throws BLLException {
        try {
            dalm.editMovie(selectedMovie);
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
     * Deletes the selected category from the database
     *
     * @param cat The selected category
     * @throws BLLException
     */
    public void removeCategory(Category cat) throws BLLException {
        try {
            dalm.deleteCategory(cat);
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

    /**
     * Movie player methods*****************************************************
     */
    
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
     * Set up the video player using a Movie object.
     * @param selected The selected Movie object.
     * @throws privatemoviecollection.bll.BLLException
     */
    public void setupPlayer(Movie selected) throws BLLException {
        player.setupPlayer(selected);
    }

    /**
     * Return the media player.
     * @return The media player.
     */
    public MediaPlayer getPlayer() {
        return player.getPlayer();
    }

    /**
     * Play the selected movie using the built-in player. Must set-up player first.
     */
    public void playBuiltIn() {
        player.playBuiltIn();
    }

    /**
     * Pause the playback.
     */
    public void pauseBuiltIn() {
        player.pauseBuiltIn();
    }

    /**
     * Jump to a new location in the movie.
     * @param value The new location.
     */
    public void seekBuiltIn(double value) {
        player.seekBuiltIn(value);
    }

    /**
     * Stop the playback.
     */
    public void stopBuiltIn() {
        player.stopBuiltIn();
    }

    public void setBuiltInVolume(double value) {
        player.setBuiltInVolume(value);
    }

}
