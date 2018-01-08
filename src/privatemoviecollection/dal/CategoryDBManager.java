package privatemoviecollection.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Category;

/**
 * Handles operations such as saving and retrieving categories from the database
 * @author sebok
 */
public class CategoryDBManager
{
    ConnectionManager cm = new ConnectionManager();
    
    /**
     * Retrieve the list of categories, that are stored in the database
     * @return The list of categories, that are stored in the database
     * @throws DAException If an error occurs during database access
     */
    public List<Category> getCategoriesFromDatabase() throws DAException
    {
        List<Category> categories = new ArrayList();
        
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Category");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Category tmp = new Category();
                tmp.setId(rs.getInt("id"));
                tmp.setCategories(rs.getString("name"));
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
        return categories;
    }
    
    /**
     * Saves a category to the database
     * @param cat The category that will be saved
     * @throws DAException If an error occurs during database access
     */
    public void save(Category cat) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSER INTO Category(id, name) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cat.getId());
            ps.setString(2, cat.getCategories());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException("Category could not be saved");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                cat.setId(rs.getInt("id"));
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
    }
    
    /**
     * Delete the specified category from the database
     * @param cat The category that will be deleted
     * @throws DAException If an error occurs during database access
     */
    public void delete(Category cat) throws DAException
    {
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Category WHERE id=?");
            ps.setInt(1, cat.getId());
            int affected = ps.executeUpdate();
            if (affected < 1)
            {
                throw new DAException(String.format("The Category with the ID of %d could not be deleted", cat.getId()));
            }
            
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
    }
}
