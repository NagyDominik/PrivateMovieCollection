/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
    public Model() {
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public Movie getSelectedMovie()
    {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
    }
    
    public ObservableList<Movie> getMoviesFromList() {
        return movieList;
    }
    
    public ObservableList<Category> getCategoriesFromList() {
        return categoryList;
    }

    public void loadMovies() throws ModelException {
        try {
            movieList.addAll(bllm.loadMovies());
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    public void removeMovie(Movie selected) throws ModelException {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).equals(selected)) {
                try {
                    movieList.remove(i);
                    bllm.deleteMovie(selected);
                }
                catch (BLLException ex) {
                    throw new ModelException(ex);
                }
            }
        }
    }

    public void saveMovie(Movie newmovie) throws ModelException {
        try {
            bllm.saveMovie(newmovie);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

    public void playSysDef(Movie selected) throws ModelException {
        try {
            bllm.playSysDef(selected);
        }
        catch (BLLException ex) {
            throw new ModelException(ex);
        }
    }

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
     * Load the categories stored in the database
     * @throws ModelException If an error occurs during database access
     */
    public void loadCategories() throws ModelException
    {
        try {
            categoryList.addAll(bllm.loadCategories());
        }
        catch (BLLException ex)
        {
            throw new ModelException(ex);
        }
    }

    /**
     * Associate a category with a movie
     * @param selectedMovie The movie that will be updated
     * @param selectedCat The category that will be added to the movie
     * @throws ModelException If an error occurs during database access
     */
    public void addCategoryToMovie(Movie selectedMovie, Category selectedCat) throws ModelException
    {
        try
        {
           bllm.addCategoryToMovie(selectedMovie, selectedCat); 
        }
        catch(BLLException ex)
        {
            throw new ModelException(ex);
        }
    }
    
    /**
     * Remove the given category from the given movie
     * @param selectedMovie The selected movie
     * @param selectedCat The selected category, that will be removed from the given movie
     * @throws ModelException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMo, Category selectedCat) throws ModelException
    {
        try
        {
            bllm.removeCategoryFromMovie(selectedMo, selectedCat);
        }
        catch(BLLException ex)
        {
            throw new ModelException(ex);
        }
    }
}
