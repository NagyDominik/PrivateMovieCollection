package privatemoviecollection.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Movie;

/**
 * Handles operations such as saving and retrieving movies from the database
 * @author sebok
 */
public class MovieDBManager
{
    private ConnectionManager cm = new ConnectionManager();
    
    public List<Movie> getMoviesFromDatabase() throws DAException
    {
        List<Movie> movies = new ArrayList();
        
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Movie tmp = new Movie();
                
                movies.add(tmp)
            }
        }
        catch (SQLException ex)
        {
            throw new DAException(ex);
        }
        
        return movies;
    }
}
