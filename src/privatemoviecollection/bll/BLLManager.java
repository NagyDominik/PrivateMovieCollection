/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

    /**
     * Calls the method in the DALManager that saves
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

    public static void playSysDef(Movie selected) throws BLLException {
        try {
            File movie = new File(selected.getPath());
            //File movie = new File("D:\\test2.mp4");
            Desktop.getDesktop().open(movie);
        }
        catch (IOException ex) {
            throw new BLLException(ex);
        }
    }

    public void saveCategory(Category cat) throws BLLException {
        try {
            dalm.saveCategory(cat);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

    /**
     * Load the list of categories from the database  
     * @return The list of categories from the database
     * @throws BLLException If an error occurs during database access
     */
    public List<Category> loadCategories() throws BLLException
    {
        try {
           return dalm.getCategories();
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }

     /**
     * Associate a category with a movie
     * @param selectedMovie The movie that will be updated
     * @param selectedCat The category that will be added to the movie
     * @throws BLLException If an error occurs during database access
     */
    public void addCategoryToMovie(Movie selectedMovie, Category selectedCat) throws  BLLException
    {
        try
        {
            dalm.addCategoryToMovie(selectedMovie, selectedCat);
        }
        catch(DAException ex)
        {
            throw new BLLException(ex);
        }
    }

    /**
     * Remove the given category from the given movie
     * @param selectedMovie The selected movie
     * @param selectedCat The selected category, that will be removed from the given movie
     * @throws BLLException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMovie, Category selectedCat) throws  BLLException
    {
        try
        {
            dalm.removeCategoryFromMovie(selectedMovie, selectedCat);
        }
        catch (DAException ex)
        {
            throw new BLLException(ex);
        }
    }
}
