/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;


/**
 *
 * @author Bence
 */
public class Categories {

    private int id;
    private String categories;

    public Categories(int id, String categories) {
        this.id = id;
        this.categories = categories;
    }
    
   

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    
   
}
