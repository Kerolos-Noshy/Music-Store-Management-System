import java.sql.*;
public class Category {

    public static boolean isCategoryExist(String categoryName, Statement statement) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM category");
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(categoryName))
                return true;
        }
        return false;
    }
}
