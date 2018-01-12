package privatemoviecollection.be;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
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
import javafx.scene.media.Media;

/**
 * An object that represent a .mp4 or .mpeg4 file
 * @author Bence
 */
public class Movie {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final FloatProperty imdbRating = new SimpleFloatProperty();
    private final FloatProperty personalRating = new SimpleFloatProperty();
    private final StringProperty path = new SimpleStringProperty();
    private FileTime lastAccessTime;
    
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    
    private String categoriesAsString;
    private Media media;

    public Movie() {
    }

    public Movie(int id, String name, float imdbRating, float personalRating, String path, Media media) {
        this.id.set(id);
        this.name.set(name);
        this.imdbRating.set(imdbRating);
        this.personalRating.set(personalRating);
        this.path.set(path);
        this.media = media;
    }

    /**
     * Try to create a media object from the specified path, and check when the file was last accessed
     */
    public void createMovieFromPath() {
        try {
            File f = new File(path.get());
            this.media = new Media(f.toURI().toString());
            BasicFileAttributes bfa = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
            this.lastAccessTime = bfa.lastAccessTime();
        }
        catch (Exception ex) {
            //If the save did not occure on the current machine, an error will occur, and the Media object will no be created
            //The data, hovewer, will not be displayed (but it will appear on the tableView)
            System.out.println(ex.getMessage());
        }
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String value) {
        path.set(value);
    }

    public StringProperty pathProperty() {
        return path;
    }

    public float getPersonalRating() {
        return personalRating.get();
    }

    public void setPersonalRating(float value) {
        personalRating.set(value);
    }

    public FloatProperty personalRatingProperty() {
        return personalRating;
    }

    public float getImdbRating() {
        return imdbRating.get();
    }

    public void setImdbRating(float value) {
        imdbRating.set(value);
    }

    public FloatProperty imdbRatingProperty() {
        return imdbRating;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public ObservableList<Category> getCategories() {
        return this.categories;
    }
    
    public String getCategoriesAsString() {
        categoriesAsString = "";
        
        List<String> catList = new ArrayList<>();
        for (Category category : categories) {
            catList.add(category.getName());
        }
        categoriesAsString = String.join(", ", catList);
        return categoriesAsString;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    /**
     * Check if the categories list contains a given category
     * @param selectedCat The category that is tested 
     * @return True if the category list contains the given category, false otherwise
     */
    public boolean hasCategory(Category selectedCat)
    {
        for (Category category : categories)
        {
            if (category.getId() == selectedCat.getId())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Remove a given category from the categories list
     * @param selectedCat The category that will be removed
     */
    public void removeCategory(Category selectedCat)
    {
        categories.remove(selectedCat);
    }
    
    /**
     * Return a string representation of the last time the file associated with this Movie object was accessed
     * @return A string representation of the last time the file associated with this Movie object was accessed
     */
    public String getLastAccessTime()
    {
        return lastAccessTime.toString();
    }
    
    public FileTime getAccesFileTime()
    {
        return this.lastAccessTime;
    }
}
