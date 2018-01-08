/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;

/**
 *
 * @author Dominik
 */
public class Movie {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final FloatProperty imdbRating = new SimpleFloatProperty();
    private final FloatProperty personalRating = new SimpleFloatProperty();
    private final StringProperty path = new SimpleStringProperty();

    public Movie()
    {
    }
    
     public Movie(int id, String name, float imdbRating , float personalRating, String path) {
        this.id.set(id);
        this.name.set(name);
        this.imdbRating.set(imdbRating);
        this.personalRating.set(personalRating);
        this.path.set(path);
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
    
    
}
