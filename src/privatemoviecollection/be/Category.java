package privatemoviecollection.be;

/**
 * A class that represent a category assigned to a movie. A movie can contain multiple categories.
 * @author Bence
 */
public class Category {

    private int id; //Unique identifier of the category. Used for storing/retrieving categories from the database.
    private String name; //The name of the category (for example: action, drama, etc.)

    public Category() {
    }

    public Category(int id, String categories) {
        this.id = id;
        this.name = categories;
    }

    /**
     * Return the name of the category.
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the category to the specified value.
     * @param name The name of the category that will be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
