/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

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
}
