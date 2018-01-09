package privatemoviecollection.dal;

import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;

/**
 * This class provides an interface to database operations
 * @author sebok
 */
public class DALManager
{
    private MovieDBManager movieDBManager = new MovieDBManager();
    private CategoryDBManager categoryDBManager = new CategoryDBManager();
    
    /****************************************************************************************/
    //Movie database methods
    
    /**
     * Get the list of movies in the database
     * @return The list of movies in the database
     * @throws DAException If an error occurs in the MovieDBManager, during database access
     */
    public List<Movie> getMovies() throws DAException
    {
        return movieDBManager.getMoviesFromDatabase();
    }
    
    /**
     * Save a movie to the database
     * @param movie The movie that will be saved
     * @throws DAException If an error occurs in the MovieDBManager, during database access
     */
    public void save(Movie movie) throws DAException
    {
        movieDBManager.save(movie);
    }
    
    /**
     * Update an already existing movie in the database
     * @param movie The movie that will be updated
     * @throws DAException If an error occurs in the MovieDBManager, during database access
     */
    public void edit(Movie movie) throws DAException
    {
        movieDBManager.edit(movie);
    }
    
    /**
     * Delete a movie from the database
     * @param movie The movie that will be deleted
     * @throws DAException If an error occurs in the MovieDBManager, during database access
     */
    public void delet(Movie movie) throws DAException
    {
        movieDBManager.delete(movie);
    }
    
    /****************************************************************************************/
    //Cateopry database methods
    
    /**
     * Get the list of categories from the database
     * @return The list of categories stored in the database
     * @throws DAException If an error occurs in the CategoryDBManager, during database access
     */
    public List<Category> getCategories() throws DAException
    {
        return categoryDBManager.getCategoriesFromDatabase();
    }
    
    /**
     * Save a category to the database
     * @param category The category that will be saved
     * @throws DAException If an error occurs in the CategoryDBManager, during database access
     */
    public void save(Category category) throws DAException
    {
        categoryDBManager.save(category);
    }
    
    /**
     * Delete a category from the database
     * @param category The category that will be deleted from the database
     * @throws DAException If an error occurs in the CategoryDBManager, during database access
     */
    public void delete(Category category) throws DAException
    {
        categoryDBManager.delete(category);
    }
}
