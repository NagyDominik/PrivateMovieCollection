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
            
        //Retrieve the categores associated with the movies
        PreparedStatement ps2 = con.prepareStatement("SELECT CatMovie.MovieId, Category.id, Category.name"
                + " FROM CS2017B_24_PrivateMovieCollection.dbo.Movie, CS2017B_24_PrivateMovieCollection.dbo.Category, CS2017B_24_PrivateMovieCollection.dbo.CatMovie "
                + "WHERE CatMovie.MovieId = Movie.id AND CatMovie.CategoryId = Category.id;");
        ResultSet rs2 = ps2.executeQuery();
        while(rs2.next())
        {
            for (Movie movie : movies)
            {
                if (movie.getId() == rs2.getInt("MovieId"))
                {
                    Category tmp = new Category();
                    tmp.setId(rs2.getInt("id"));
                    tmp.setName(rs2.getString("name"));
                    movie.addCategory(tmp);
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
            PreparedStatement ps = con.prepareStatement("INSERT INTO Movie(name, user_rating, imdb_rating, filelink) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getName());
            ps.setFloat(2, movie.getPersonalRating());
            ps.setFloat(3, movie.getImdbRating());
            ps.setString(4, movie.getPath());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException("Movie could not be saved!");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                movie.setId(rs.getInt(1));
            }
            
            //Save the associated categories
            if (!movie.getCategories().isEmpty())
            {
               for (Category cat : movie.getCategories())
                {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?, ?)");
                    ps2.setInt(1, cat.getId());
                    ps2.setInt(2, movie.getId());
                    int affected2 = ps2.executeUpdate();
                    if (affected2 < 1)
                    {
                        throw new DAException("Category could not be associated with movie!");
                    }
                }      
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
            PreparedStatement ps = con.prepareStatement("UPDATE Movie SET name=?, user_rating=?, imdb_rating=?, filelink=?  WHERE id=?");
            ps.setString(1, movie.getName());
            ps.setFloat(2, movie.getPersonalRating());
            ps.setFloat(3, movie.getImdbRating());
            ps.setString(4, movie.getPath());
            ps.setInt(5, movie.getId());
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
     * Add a category to a move
     * @param movie The movie that will be updated with a new category
     * @param newCategory The new category, that will be added to the movie
     * @throws DAException If an error occurs during database access
     */
    public void addCategoryToMovie(Movie movie, Category newCategory) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?, ?)");
            ps.setInt(1, newCategory.getId());
            ps.setInt(2, movie.getId());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException("Category could not be saved to the movie");
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
            //If the movie is associated with at least one category, delete those associations
            if (!movie.getCategories().isEmpty())
            {
                PreparedStatement ps2 = con.prepareStatement("DELETE FROM CatMovie WHERE CatMovie.MovieId = ?");
                ps2.setInt(1, movie.getId());
                int affected = ps2.executeUpdate();
                if (affected < 1)
                {
                    throw new DAException("Category associations could not be deleted");
                }
            }
            
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

    /**
     * Remove the given category from the given movie
     * @param selectedMovie The selected movie
     * @param selectedCat The selected category, that will be removed from the given movie
     * @throws DAException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMovie, Category selectedCat) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CatMovie WHERE CatMovie.MovieId = ? AND CatMovie.CategoryId = ?");
            ps.setInt(1, selectedMovie.getId());
            ps.setInt(2, selectedCat.getId());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException("Could not remove category from movie");
            }
        }
        catch (SQLException ex)
        {
            
        }
    }
    
    /*public List<Movie>search(String searchString){
        List<Movie> allMovies = new ArrayList();
        
        try(Connection con = cm.getConnection())
        {
            String querry = "SELECT Movie.*, Category.* FROM [Movie], [Category], [CatMovie] "
                    + "WHERE CatMovie.CategoryId = Category.id AND CatMovie.MovieId = Movie.id "
                    + "AND Movie.name LIKE '%' + ? + '%' OR Category.name LIKE '%' + ? + '%' ";
            
            String querry2 = "SELECT Movie.*, Category.* FROM [Movie], [Category], [CatMovie] "
                    + "WHERE CatMovie.CategoryId = Category.id AND CatMovie.MovieId = Movie.id "
                    + "AND Movie.user_rating LIKE '%' + ? + '%' OR Movie.imdb_rating LIKE '%' + ? + '%'";
        }
        catch (SQLException ex)
        {
            throw new DAException(ex);
        }
        
        return allMovies;
    }
    }*/
}
