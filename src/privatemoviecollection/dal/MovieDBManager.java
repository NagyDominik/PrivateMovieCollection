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
    
    /**
     * Return a the list of movies stored in the database
     * @return A list of movies stored in the database
     * @throws DAException If an error occurs during database access
     */
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
                tmp.setId(rs.getInt("id"));
                tmp.setImdbRating(rs.getFloat("imdb_rating"));
                tmp.setPersonalRating(rs.getFloat("user_rating"));
                tmp.setName(rs.getString("name"));
                tmp.setPath(rs.getString("filelink"));
                movies.add(tmp);
            }
        }
        catch (SQLException ex)
        {
            throw new DAException(ex);
        }
        
        return movies;
    }
}
