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
import java.util.logging.Level;
import java.util.logging.Logger;
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
}
