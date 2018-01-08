package privatemoviecollection.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                tmp.id = rs.getInt("id");
                tmp.name = rs.getString("name");
                categories.add(tmp);
            }
        }
        catch(SQLException ex)
        {
            throw new DAException(ex);
        }
        return categories;
    }
}
