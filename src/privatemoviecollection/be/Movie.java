package privatemoviecollection.be;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * An object that represent a .mp4 or .mpeg4 file
 *
 * @author Bence
 */
public class Movie {

    private final IntegerProperty id = new SimpleIntegerProperty(); // The uniqui identifier of the movie. Used for storing/retrieving movies from the database
    private final StringProperty name = new SimpleStringProperty(); // The name (title) of the movie
    private final FloatProperty imdbRating = new SimpleFloatProperty();
    private final FloatProperty personalRating = new SimpleFloatProperty();
    private final StringProperty path = new SimpleStringProperty(); // The location of the .mp4 or .mpeg4 file on the harddrive
    private final StringProperty imagePath = new SimpleStringProperty();    // The location of the image file (such as a cover) associted with aóthe movie.
    private Image image;    // An image object created using the imagePath property.
    private Timestamp fileAccessDate;   // The last time this move was played from within the program.
    private ObservableList<Category> categories = FXCollections.observableArrayList();  //The categories associated with the movie.
    private String categoriesAsString;  // A string representation of the categories associated with the movie, created using the categories observable list.
    
    public Movie() {
    }

    public Movie(int id, String name, float imdbRating, float personalRating, String path, Media media) {
        this.id.set(id);
        this.name.set(name);
        this.imdbRating.set(imdbRating);
        this.personalRating.set(personalRating);
        this.path.set(path);
    }

    /**
     * Return the path of the .mp4 or .mpeg4 file that is represented by this Movie object.
     * @return The path of the .mp4 or .mpeg4 file that is represented by this Movie object.
     */
    public String getPath() {
        return path.get();
    }

    /**
     * Set the path of the move (the place it is found on the hard drive)
     * @param value The path of the .mp4 or .mpeg4 file that is represented by this Movie object.
     */
    public void setPath(String value) {
        path.set(value);
    }

    /**
     * Return the personal rating of the movie.
     * @return The personal rating of the movie.
     */
    public float getPersonalRating() {
        return personalRating.get();
    }

    /**
     * Set the personal rating of the movie.
     * @param value The personal rating of the movie.
     */
    public void setPersonalRating(float value) {
        personalRating.set(value);
    }

    /**
     * Return the IMDb rating of the movie.
     * @return The IMDb rating of the movie.
     */
    public float getImdbRating() {
        return imdbRating.get();
    }

    /**
     * Set the IMDb rating of the movie to the specified value.
     * @param value The specified IMDb rating.
     */
    public void setImdbRating(float value) {
        imdbRating.set(value);
    }

    /**
     * Return the name (title) of the movie.
     * @return The name (title) of the movie.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Set the name (title) of the movie to the given values.
     * @param value The name (title) that will be set.
     */
    public void setName(String value) {
        name.set(value);
    }
    
    /**
     * Retrieve the ID of the movie.
     * @return The ID of the movie.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Set the ID of the movie to the specified value. Used when saving/retrieving movies from the database.
     * @param value The value of the ID.
     */
    public void setId(int value) {
        id.set(value);
    }

    /**
     * Retrieve the list of categories associated with the movie.
     * @return The list of categories associated with the movie.
     */
    public ObservableList<Category> getCategories() {
        return this.categories;
    }

    /**
     * Associate a new category with this movie.
     * @param category The new category, that will be associated with the movie.
     */
    public void addCategory(Category category) {
        this.categories.add(category);
    }

    /**
     * Update the last time this movie was played from within the program.
     * @param fileAccessDate A Timestamp object indicating the last time this movie was played.
     */
    public void setFileAccessDate(Timestamp fileAccessDate) {
        this.fileAccessDate = fileAccessDate;
    }

    /**
     * Get the last time the movie was played from within the program.
     * @return A Timestamp object indicating the last time this movie was played from within the program.
     */
    public Timestamp getTimeStamp() {
        return this.fileAccessDate;
    }
    
    /**
     * Return the path of the cover image.
     * @return The path of the cover image.
     */
    public String getImagePath()
    {
        return imagePath.get();
    }

    /**
     * Set the path of the cover image.
     * @param value The path of the cover image.
     */
    public void setImagePath(String value)
    {
        imagePath.set(value);
    }
    
    /**
     * Return the cover image of the movie.
     * @return The cover image associated with the movie. 
     */
    public Image getImage()
    {
        return this.image;
    }
    
    /**
     * If we store an image path, attempt to create an image from it
     */
    public void createImage()
    {
        if (this.imagePath.get().equals("None"))
        {
            image = new Image("/img/no_cover.png");
            return;
        }
        image = new Image(this.imagePath.get());
    }
    
    /**
     * Returns the last access time formatted as a string
     *
     * @return The last access time formatted as a string
     */
    public String getFileAccessDate() {
        return fileAccessDate.toString();
    }

    /**
     * Returns the list of categories associated with this movie as a formatted string
     *
     * @return The list of categories associated with this movie as a formatted string
     */
    public String getCategoriesAsString() {
        categoriesAsString = "";
        List<String> catList = new ArrayList<>();
        for (Category category : categories) {
            catList.add(category.getName());
        }
        categoriesAsString = String.join(", ", catList);
        return categoriesAsString;
    }

    /**
     * Checks if the categories list contains a given category
     *
     * @param selectedCat The category that is tested
     * @return True if the category list contains the given category, false
     * otherwise
     */
    public boolean hasCategory(Category selectedCat) {
        for (Category category : categories) {
            if (category.getId() == selectedCat.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a given category from the categories list
     *
     * @param selectedCat The category that will be removed
     */
    public void removeCategory(Category selectedCat) {
        categories.remove(selectedCat);
    }

}
