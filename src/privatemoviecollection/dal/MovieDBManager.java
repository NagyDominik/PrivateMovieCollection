package privatemoviecollection.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Category;
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
                //tmp.createMovieFromPath();
                movies.add(tmp);
            }
            
        PreparedStatement ps2 = con.prepareStatement("SELECT Movie.*, CatMovie.CategoryId FROM Movie, CatMovie WHERE CatMovie.MovieId = Movie.id");
        ResultSet rs2 = ps2.executeQuery();
        while(rs2.next())
        {
            for (Movie movie : movies)
            {
                if (movie.getId() == rs2.getInt("id"))
                {
                    Category tmp = new Category();
                    tmp.setId(rs2.getInt(""));
                }
            }
        }
        
        }
        catch (SQLException ex)
        {
            throw new DAException(ex);
        }
        return movies;
    }
    
    /**
     * Save the given movie to the database
     * @param movie  The move that will be saved to the database
     * @throws DAException If an error occurs during database access
     */
    public void save(Movie movie) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Movie(id, name, user_rating, imdb_rating, filelink, lastview) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.setString(2, movie.getName());
            ps.setFloat(3, movie.getPersonalRating());
            ps.setFloat(4, movie.getImdbRating());
            ps.setString(5, movie.getPath());
            //ps.setDate(6, movie.getLastView());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException("Movie could not be saved!");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                movie.setId(rs.getInt("id"));
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
    }
    
    /**
     * Updates an already existing database entry using a Movie object
     * @param movie The updated movie
     * @throws DAException If an error occurs during database access
     */
    public void edit(Movie movie) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("UPADTE Movie SET name=?, user_rating=?, imdb_rating=?, filelink=?, lastview=? WHERE id=?");
            ps.setString(1, movie.getName());
            ps.setFloat(2, movie.getPersonalRating());
            ps.setFloat(3, movie.getImdbRating());
            ps.setString(4, movie.getPath());
            //ps.setDate(5, movie.getLastView());
            ps.setInt(6, movie.getId());
            int affected = ps.executeUpdate();
            if (affected < 0)
            {
                throw new DAException("Movie could not be edited!");
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
    }
    
    /**
     * Delete the specified movie from the database
     * @param movie The movie that will be deleted
     * @throws DAException If an error occurs during database access
     */
    public void delete(Movie movie) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Movie WHERE id=?");
            ps.setInt(1, movie.getId());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException(String.format("Movie with the ID of %d could not be deleted", movie.getId()));
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
    }
}
