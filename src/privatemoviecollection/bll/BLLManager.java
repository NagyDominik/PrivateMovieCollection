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

    public void saveMovie(Movie newmovie) throws BLLException {
        try {
            dalm.save(newmovie);
        }
        catch (DAException ex) {
            throw new BLLException(ex);
        }
    }
    
}
