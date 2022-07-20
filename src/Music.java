import java.sql.*;
import java.util.Date;

public class Music {
    private String musicName;
    private String duration;
    private String description;
    private Date releaseDate;
    private int quantity;
    private float price;

    public Music(String musicName, String duration, String description, Date releaseDate, int quantity, float price) {
        this.musicName = musicName;
        this.duration = duration;
        this.description = description;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
        this.price = price;
    }


    public static boolean isMusicExist(String musicName, Statement statement) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT musicName FROM music");
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(musicName))
                return true;
        }
        return false;
    }

    public static void musicDetails(Statement statement, String musicName) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM music WHERE musicName = '"+ musicName + "'");
        while (resultSet.next()) {
            System.out.println("Category: " + resultSet.getString(1) + "\n"
                    + "Music: " + resultSet.getString(2) + "\n"
                    + "Description: " + resultSet.getString(5) + "\n"
                    + "Duration: " + resultSet.getInt(3) + ":" + resultSet.getInt(4) + "\n"
                    + "Release Date: " + resultSet.getInt(6) + "/" + resultSet.getInt(7) + "/" + resultSet.getInt(8) + "\n"
                    + "Quantity: " + resultSet.getInt(9) + "\n"
                    + "Price: " + resultSet.getFloat(10) + " $");
        }
    }

    public static void showAllMusics(Statement statement) throws SQLException {
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT musicName FROM music");
        while (resultSet.next()) {
            String musicName = resultSet.getString(1);
            System.out.println(counter + "- " + musicName);
            counter++;
        }
    }

    public static void showMusicsInStock(Statement statement) throws SQLException {
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT musicName FROM music WHERE quantity > 0");
        while (resultSet.next()) {
            String musicName = resultSet.getString(1);
            System.out.println(counter + "- " + musicName);
            counter++;
        }
    }

    public static void searchMusic(Statement statement, String musicName) throws SQLException{
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM music WHERE musicName LIKE '%" + musicName + "%'");
        while (resultSet.next()) {
            String musicName1 = resultSet.getString(2);
            System.out.println(counter + "- " + musicName1);
            counter++;
        }
        if (counter == 1)
            System.out.println("\nNo Music Found");
    }

    public static void showSoldMusics(Statement statement) throws SQLException {
        int counter = 1;
        int noOfTotalSoldItems = 0;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM soldItems");
        while (resultSet.next()) {
            String musicName = resultSet.getString(1);
            int soldTimes = resultSet.getInt(2);
            System.out.println(counter + "- " + musicName + "\t Sold times: " + soldTimes);
            noOfTotalSoldItems += soldTimes;
            counter++;
        }
        System.out.println("Total sold musics: "  + noOfTotalSoldItems);
    }
}
