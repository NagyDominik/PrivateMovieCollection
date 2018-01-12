/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import privatemoviecollection.be.Category;
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
    private ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private Movie selectedMovie;
    private ObservableList<Movie> searchedList = FXCollections.observableArrayList();

    public Model() {

    }

    /**
     * Return an instance of the Model
     *
     * @return An instance of the Model
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Return the selected movie
     *
     * @return The selected movie
     */
    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    /**
     * Used to reference a selected movie (for example, when editing the
     * personal rating or categories) between different windows
     *
     * @param selectedMovie The selected movie that will be referenced
     */
    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    /**
     * Return a list of movies
     *
     * @return A list of movies
     */
    public ObservableList<Movie> getMoviesFromList() {
        return movieList;
    }

    /**
     * Return a list of categories
     *
     * @return A list of categories
     */
    public ObservableList<Category> getCategoriesFromList() {
        return categoryList;
    }

    /**
     * Load the list of movies and categories from the database
     *
     * @throws ModelException If an error occurs during database access
     */
    public void load() throws ModelException {
        try {
            movieList.addAll(bllm.loadMovies());
            categoryList.addAll(bllm.loadCategories());
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Remove the selected movie from the list and from the database
     *
     * @param selected The movie that will be removed
     * @throws ModelException If an error occurs during database access
     */
    public void removeMovie(Movie selected) throws ModelException {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).equals(selected)) {
                try {
                    movieList.remove(i);
                    bllm.deleteMovie(selected);
                    break;
                }
                catch (BLLException ex) {
                    throw new ModelException(ex);
                }
            }
        }
    }

    /**
     * Save a new movie to the list and to the database
     *
     * @param newmovie The movie that will be saved
     * @throws ModelException If an error occurs during database access
     */
    public void saveMovie(Movie newmovie) throws ModelException {
        try {
            movieList.add(newmovie);
            bllm.saveMovie(newmovie);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Attempt to play the selected movie with the default media player
     *
     * @param selected The selected movie that will be played
     * @throws ModelException If an error occurs
     */
    public void playSysDef(Movie selected) throws ModelException {
        try {
            selected.setFileAccessDate(new Timestamp(System.currentTimeMillis()));
            updateMovie(selected);
            bllm.playSysDef(selected);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Attempt to
     *
     * @param cat
     * @throws ModelException
     */
    public void addCategory(Category cat) throws ModelException {
        if (!categoryList.contains(cat)) {
            try {
                categoryList.add(cat);
                bllm.saveCategory(cat);
            }
            catch (BLLException ex) {
                throw new ModelException(ex);
            }
        } else {
            throw new ModelException("The category already exists");
        }
    }

    /**
     * Associate a category with a movie
     *
     * @param selectedMovie The movie that will be updated
     * @param selectedCat The category that will be added to the movie
     * @throws ModelException If an error occurs during database access
     */
    public void addCategoryToMovie(Movie selectedMovie, Category selectedCat) throws ModelException {
        try {
            bllm.addCategoryToMovie(selectedMovie, selectedCat);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Remove the given category from the given movie
     *
     * @param selectedMo The selected movie
     * @param selectedCat The selected category, that will be removed from the
     * given movie
     * @throws ModelException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMo, Category selectedCat) throws ModelException {
        try {
            bllm.removeCategoryFromMovie(selectedMo, selectedCat);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Attempt to update the given movie in the database
     *
     * @param selectedMovie The move that will be updated
     * @throws ModelException If an error occurs during database access
     */
    public void updateMovie(Movie selectedMovie) throws ModelException {
        try {
            bllm.updateMovie(selectedMovie);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Set up a player to play the movie in the program
     *
     * @param selected The movie that will be played
     */
    public void setupPlayer(Movie selected) {
        bllm.setupPlayer(selected);
    }

    /**
     * Return a media player object
     *
     * @return A media player object
     */
    public MediaPlayer getPlayer() {
        return bllm.getPlayer();
    }

    /**
     * Use the built-in player
     */
    public void playBuiltIn() throws ModelException {
        selectedMovie.setFileAccessDate(new Timestamp(System.currentTimeMillis()));
        updateMovie(selectedMovie);
        bllm.playBuiltIn();
    }

    /**
     * Pause the play back
     */
    public void pauseBuiltIn() {
        bllm.pauseBuiltIn();
    }

    /**
     * Move to a different location during play
     *
     * @param value The new location
     */
    public void seekBuiltIn(double value) {
        bllm.seekBuiltIn(value);
    }

    public void search(String searchString) throws ModelException {
        /*try {
            searchedList.clear();
            searchedList.addAll(bllm.search(searchString));
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }*/
        searchedList.clear();
        for (Movie movie : movieList) {
            boolean isAdded = false;
            if (movie.getName().toLowerCase().contains(searchString)) {
                searchedList.add(movie);
                isAdded = true;
            }
            try {
                float searchFloat = Float.parseFloat(searchString);
                if ((movie.getImdbRating() > searchFloat || movie.getPersonalRating() > searchFloat) && !isAdded) {
                    searchedList.add(movie);
                }
            }
            catch (Exception e) {
            }
            for (Category category : movie.getCategories()) {
                if (category.getName().toLowerCase().contains(searchString) && !isAdded) {
                    searchedList.add(movie);
                    break;
                }
            }
        }
    }

    public ObservableList<Movie> getSearchedMovies() {
        return searchedList;
    }


    public void stopBuiltIn() {
        bllm.stopBuiltIn();
    }
    
    /**
     * Filter out movies that haven't been accessed for more than two years and have a lower personal rating than 6.
     * @return The list of movies that match the above criteria.
     */
    public List<Movie> checkMovies()
    {
        List<Movie> oldMovies = new ArrayList<>();
        Calendar checkDate = Calendar.getInstance();
        checkDate.add(Calendar.MINUTE, -1);
        // checkDate.add(Calendar.YEAR, -2);
        
        for (Movie movie: movieList)
        {
            if (movie.getPersonalRating() < 10.0f)
            {
                if (movie.getTimeStamp().before(checkDate.getTime()))
                {
                    oldMovies.add(movie);
                }
            }
        }
        
        return oldMovies;
    }
}
