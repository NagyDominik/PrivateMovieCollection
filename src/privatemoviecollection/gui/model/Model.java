/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import java.sql.Timestamp;
import java.util.Calendar;
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
    private final BLLManager bllm = new BLLManager();
    private final ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private final ObservableList<Movie> searchedList = FXCollections.observableArrayList(); //Used to store the result of a user-initiated search
    private final ObservableList<Movie> movieUtilityList = FXCollections.observableArrayList(); //Used to store moves that are old or movies that have similar titles to a new movie
    private Movie selectedMovie;

    public Model() {
    }

    /**
     * Returns an instance of the Model
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
     * Returns the selected movie
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
     * Returns a list of movies
     *
     * @return A list of movies
     */
    public ObservableList<Movie> getMoviesFromList() {
        return movieList;
    }

    /**
     * Returns a list of movies we searched for
     *
     * @return A filtered list of movies
     */
    public ObservableList<Movie> getSearchedMovies() {
        return searchedList;
    }

    /**
     * Returns a list of categories
     *
     * @return A list of categories
     */
    public ObservableList<Category> getCategoriesFromList() {
        return categoryList;
    }

    /**
     * Return a list of old movies
     *
     * @return A list of old movies
     */
    public ObservableList<Movie> getUtilityList() {
        return this.movieUtilityList;
    }

    /**
     * Database Methods*********************************************************
     */
    /**
     * Loads the list of movies and categories from the database
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
     * Removes the selected movie from the list and from the database
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
     * Saves a new movie to the list and to the database
     *
     * @param newmovie The movie that will be saved
     * @throws ModelException If an error occurs during database access
     */
    public void saveMovie(Movie newmovie) throws ModelException {
        try { 
            //Do not allow movies with already existing titles
            for (Movie movie : movieList)
            {
                if (movie.getName().equals(newmovie.getName()))
                {
                    throw new ModelException("Movie is already in the database!");
                }
            }
            
            movieList.add(newmovie);
            bllm.saveMovie(newmovie);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }
    
    /**
     * Check the list of movies for similar titles. 
     * @param The new movie that will be checked against the already existing movies.
     * @return The list containing a list of similar movies.
     */
    public ObservableList<Movie> checkSimilarities(Movie newMovie)
    {
        //Check if a movie is already in the database
        for (Movie movie : movieList)
        {
            if (levenshtein(movie.getName(), newMovie.getName()) < 3 )
            {
                movieUtilityList.add(movie);
            }
        }
        return movieUtilityList;
    }

    /**
     * Attempts to add a new category to the database and the list
     *
     * @param cat The new category to be added
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
     * Attempts to remove a selected category from the database
     *
     * @param cat The selected category
     * @throws ModelException
     */
    public void removeCategory(Category cat) throws ModelException {
        try {
            bllm.removeCategory(cat);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    /**
     * Associates a category with a movie
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
     * Removes the given category from the given movie
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
     * Attempts to update the given movie in the database
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
     * Movie player methods*****************************************************
     */
    
    
    /**
     * Attempts to play the selected movie with the default media player
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
     * Sets up a player to play the movie in the program
     *
     * @param selected The movie that will be played
     */
    public void setupPlayer(Movie selected) {
        bllm.setupPlayer(selected);
    }

    /**
     * Returns a MediaPlayer object
     *
     * @return A media player object
     */
    public MediaPlayer getPlayer() {
        return bllm.getPlayer();
    }

    /**
     * Uses the built-in player
     *
     * @throws ModelException
     */
    public void playBuiltIn() throws ModelException {
        selectedMovie.setFileAccessDate(new Timestamp(System.currentTimeMillis()));
        updateMovie(selectedMovie);
        bllm.playBuiltIn();
    }

    /**
     * Pauses the built-in player
     */
    public void pauseBuiltIn() {
        bllm.pauseBuiltIn();
    }

    /**
     * Goes to the given position in the movie
     *
     * @param value The new location
     */
    public void seekBuiltIn(double value) {
        bllm.seekBuiltIn(value);
    }

    /**
     * Stops the built-in player
     */
    public void stopBuiltIn() {
        bllm.stopBuiltIn();
    }

    /**
     * Other methods************************************************************
     */
    /**
     * Looks for a given character sequence in the movie titles, categories,
     * imdb and personal ratings
     *
     * @param searchString The character sequence we are looking for
     * @throws ModelException
     */
    public void search(String searchString) throws ModelException {
        searchedList.clear();
        for (Movie movie : movieList) {
            boolean isAdded = false;
            if (movie.getName().toLowerCase().contains(searchString.toLowerCase())) {
                searchedList.add(movie);
                isAdded = true;
            }
            try {
                float searchFloat = Float.parseFloat(searchString);
                if ((movie.getImdbRating() > searchFloat || movie.getPersonalRating() > searchFloat) && !isAdded) {
                    searchedList.add(movie);
                }
            }
            catch (NumberFormatException e) {
            }
            for (Category category : movie.getCategories()) {
                if (category.getName().toLowerCase().contains(searchString.toLowerCase()) && !isAdded) {
                    searchedList.add(movie);
                    break;
                }
            }
        }
    }

    /**
     * Filter out movies that haven't been accessed for more than two years and
     * have a lower personal rating than 6.
     *
     * @return The list of movies that match the above criteria.
     */
    public Boolean checkMovies() {
        Calendar checkDate = Calendar.getInstance();
        checkDate.add(Calendar.YEAR, -2);

        for (Movie movie : movieList) {
            if (movie.getPersonalRating() < 6.0f) {
                if (movie.getTimeStamp().before(checkDate.getTime())) {
                    movieUtilityList.add(movie);
                }
            }
        }
        return !movieUtilityList.isEmpty();

    }

    /**
     * Check the distance between two strings
     * Based on this article: https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
     * @param first The first string
     * @param second The second string
     * @return The distance between the two parameters
     */
    private int levenshtein(String first, String second)
    {
        int d[][];  //Matrix
        int firstLength;  //Length of the first string
        int secondLengt;  //Length of the seconf string
        int i;  //Iterate through the first string  
        int j;  //Iterate through the second string
        char firstIthChar;   //ith character of the first string
        char secondJthChar;   //jth charachter of the second string
        int cost;
        
        firstLength = first.length();
        secondLengt = second.length();
        
        if (firstLength == 0) {return secondLengt; }    //If the first string is empty, the number of transformation required is the length of the second string.
        if (secondLengt == 0) {return firstLength; }    //If the second string is empty, the number of transformation required is the length of the first string.
        
        d = new int[firstLength+1][secondLengt+1];  //Create a new matrix with the dimension of the first and second strings
        
        for (i = 0; i < firstLength; i++) //Set the first row of the matrix to numbers 0 through length_first
        {
            d[i][0] = i;
        }
        
        for (j = 0; j < secondLengt; j++) //Set the first column of the matrix to numbers 0 through length_second
        {
            d[0][j] = j;
        }
        
        for (i = 1; i<=firstLength; i++)
        {
            firstIthChar =  first.charAt(i - 1); //Examine each character of the first string
            
            for (j = 1; j <= secondLengt; j++)
            {
                secondJthChar = second.charAt(j - 1); //Examine each charachter of the second string
                
                if (firstIthChar == secondJthChar)
                {
                    cost = 0;   //If the two charachters are equal, the cost is 0
                }
                else
                {
                    cost = 1;   //Otherwise the cost is 1
                }
                
                d[i][j] = min(d[i-1][j]+1, //Deletion
                        d[i][j-1]+1, //Insertion
                        d[i-1][j-1] + cost); //Substitution
            }
        }
        
        return d[firstLength][secondLengt];
        
    }
    
    /**
     * Return the lowest number from the three parameters
     * @param a First number
     * @param b Second number
     * @param c Third number
     * @return The lowest number
     */
    private int min(int a, int b, int c)
    {
        int min = a;
        
        if (b < min)
        {
            min = b; 
        }
        if (c < min)
        {
            min = c;
        }
        
        return min;
    }

}
