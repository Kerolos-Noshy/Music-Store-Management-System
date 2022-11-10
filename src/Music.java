import java.sql.*;
import java.util.Date;
import java.util.Scanner;

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
        resultSet = statement.executeQuery("SELECT music_name FROM music");
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(musicName))
                return true;
        }
        return false;
    }

    public static void musicDetails(Statement statement, String musicName) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM music WHERE music_name = '"+ musicName + "'");
        String name =  resultSet.getString(3);
        String description = resultSet.getString(6);
        String duration  = resultSet.getInt(4) + ":" + resultSet.getInt(5);
        String releaseDate = resultSet.getInt(7) + "/" + resultSet.getInt(8) + "/" + resultSet.getInt(9);
        int quantity = resultSet.getInt(10);
        float price = resultSet.getFloat(11);
        resultSet = statement.executeQuery("SELECT category_name FROM category WHERE category_id = '" + resultSet.getInt(1) + "'");
        String categoryName = resultSet.getString(1);

        System.out.println("Category: " + categoryName + "\n"
                + "Music: " + name + "\n"
                + "Description: " + description + "\n"
                + "Duration: " + duration + "\n"
                + "Release Date: " + releaseDate + "\n"
                + "Quantity: " + quantity + "\n"
                + "Price: " + price + " $");
    }

    public static void showAllMusics(Statement statement) throws SQLException {
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT music_name FROM music");
        while (resultSet.next()) {
            String musicName = resultSet.getString(1);
            System.out.println(counter + "- " + musicName);
            counter++;
        }
    }

    public static void showMusicsInStock(Statement statement) throws SQLException {
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT music_name FROM music WHERE quantity > 0");
        while (resultSet.next()) {
            String musicName = resultSet.getString(1);
            System.out.println(counter + "- " + musicName);
            counter++;
        }
    }

    public static boolean isMusicInStock(Statement statement, String musicName) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT quantity FROM music WHERE music_name ='" + musicName + "'");
        if (resultSet.getInt(1) > 0)
            return true;
        return false;
    }

    public static void searchMusic(Statement statement, String musicName) throws SQLException{
        int counter = 1;
        ResultSet resultSet = statement.executeQuery("SELECT music_name FROM music WHERE music_name LIKE '%" + musicName + "%'");
        while (resultSet.next()) {
            String musicName1 = resultSet.getString(1);
            System.out.println(counter + "- " + musicName1);
            counter++;
        }
        if (counter == 1)
            System.out.println("\nNo Music Found");
    }

    public static void showNotAvailableMusics(Statement statement) throws SQLException {
        boolean flag = false;
        ResultSet resultSet = statement.executeQuery("SELECT music_name FROM music WHERE quantity < 1");
        while (resultSet.next()) {
            flag = true;
            String musicName = resultSet.getString(1);
            System.out.println("- " + musicName);
        }
        if (!flag) {
            System.out.println("All music are available");
        }
    }
}
