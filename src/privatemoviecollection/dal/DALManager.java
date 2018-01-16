package privatemoviecollection.dal;

import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;

/**
 * This class provides an interface to database operations
 *
 * @author sebok
 */
public class DALManager {

    private MovieDBManager movieDBManager = new MovieDBManager();
    private CategoryDBManager categoryDBManager = new CategoryDBManager();

    /**
     * Movie database methods***************************************************
     */
    
    /**
     * Gets the list of movies in the database
     *
     * @return The list of movies in the database
     * @throws DAException If an error occurs in the MovieDBManager, during
     * database access
     */
    public List<Movie> getMovies() throws DAException {
        return movieDBManager.getMoviesFromDatabase();
    }

    /**
     * Saves a movie to the database
     *
     * @param movie The movie that will be saved
     * @throws DAException If an error occurs in the MovieDBManager, during
     * database access
     */
    public void saveMovie(Movie movie) throws DAException {
        movieDBManager.save(movie);
    }

    /**
     * Updates an already existing movie in the database
     *
     * @param movie The movie that will be updated
     * @throws DAException If an error occurs in the MovieDBManager, during
     * database access
     */
    public void editMovie(Movie movie) throws DAException {
        movieDBManager.edit(movie);
    }

    /**
     * Adds a category to a move
     *
     * @param editedMovie The movie that will be updated with a new category
     * @param newCategory The new category, that will be added to the movie
     * @throws DAException If an error occurs in the MovieDBManager, during
     * database access
     */
    public void addCategoryToMovie(Movie editedMovie, Category newCategory) throws DAException {
        movieDBManager.addCategoryToMovie(editedMovie, newCategory);
    }

    /**
     * Deletes a movie from the database
     *
     * @param movie The movie that will be deleted
     * @throws DAException If an error occurs in the MovieDBManager, during
     * database access
     */
    public void deleteMovie(Movie movie) throws DAException {
        movieDBManager.delete(movie);
    }

    /**
     * Removes the given category from the given movie
     *
     * @param selectedMovie The selected movie
     * @param selectedCat The selected category, that will be removed from the
     * given movie
     * @throws DAException If an error occurs during database access
     */
    public void removeCategoryFromMovie(Movie selectedMovie, Category selectedCat) throws DAException {
        movieDBManager.removeCategoryFromMovie(selectedMovie, selectedCat);
    }

    /**
     * Category database methods************************************************
     */
    
    /**
     * Gets the list of categories from the database
     *
     * @return The list of categories stored in the database
     * @throws DAException If an error occurs in the CategoryDBManager, during
     * database access
     */
    public List<Category> getCategories() throws DAException {
        return categoryDBManager.getCategoriesFromDatabase();
    }

    /**
     * Saves a category to the database
     *
     * @param category The category that will be saved
     * @throws DAException If an error occurs in the CategoryDBManager, during
     * database access
     */
    public void saveCategory(Category category) throws DAException {
        categoryDBManager.save(category);
    }

    /**
     * Deletes a category from the database
     *
     * @param category The category that will be deleted from the database
     * @throws DAException If an error occurs in the CategoryDBManager, during
     * database access
     */
    public void deleteCategory(Category category) throws DAException {
        categoryDBManager.delete(category);
    }

}
