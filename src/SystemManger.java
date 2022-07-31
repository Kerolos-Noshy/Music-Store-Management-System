import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemManger {
    Connection connection = DbConnection.ConnectionDB();
    PreparedStatement preparedStatement;
    ArrayList <String> cart = new ArrayList<>();


    public void startProgram() {
        System.out.println("\nWelcome to Music Store");
        login();
    }

    public void login() {
        System.out.println("\nEnter 'A' for Administrator or 'C' for Customer sign in account or Enter 'X' to Exit");

        Scanner input = new Scanner(System.in);
        char s = input.nextLine().charAt(0);

        switch (s) {
            case 'A':
            case 'a':

                if (signIn(s)) {
                    System.out.println("\nWelcome");
                    adminOperations();
                } else {
                    System.out.println("Wrong Username or Password");
                    login();
                }
                break;

            case 'C':
            case 'c':

                System.out.println("Enter 'R' for Registration or 'S' for Sign in");
                char t = input.next().charAt(0);

                switch (t) {
                    case 'R':
                    case 'r':

                        register();

                    case 'S':
                    case 's':

                        if (signIn(s)) {
                            System.out.println("\nWelcome");
                            customerOperations();

                        } else {
                            System.out.println("\nWrong Username or Password");
                            login();
                        }
                        break;
                }
                break;
            case 'X':
            case 'x':
                System.out.println("\nThanks for Using our App.");
                System.exit(0);
        }
    }

    public void register() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter Your Username");
        String username = input.next();
        System.out.println("Please Enter Your Password");
        String password = input.next();

        try {
            preparedStatement = connection.prepareStatement("insert into customers(username, password)values(?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nCongratulations you have registered your account.\nNow please sign in to your account.\n");
        login();
    }

    public boolean signIn(char type) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease Enter Your Username");
        String username = input.next();
        System.out.println("Please Enter Your Password");
        String password = input.next();

        switch (type) {

            case 'A':
            case 'a':
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM admins");
                    while (resultSet.next()) {
                        if (resultSet.getString(1).equals(username) && resultSet.getString(2).equals(password))
                            return true;
                    }
                } catch (SQLException e) {
                    System.out.println("Wrong name or password");
                }

            case 'C':
            case 'c':
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
                    while (resultSet.next()) {
                        if (resultSet.getString(1).equals(username) && resultSet.getString(2).equals(password))
                            return true;
                    }
                } catch (SQLException e) {
                    System.out.println("Wrong name or password");
                }
        }
        return false;
    }

    public void adminOperations() {
        System.out.println("\nPlease Enter The Operation Number:");
        System.out.println("1- Add Category");
        System.out.println("2- Edit Category");
        System.out.println("3- Remove Category");
        System.out.println("4- Add Music");
        System.out.println("5- Edit Music Description");
        System.out.println("6- Remove Music");
        System.out.println("7- Show Music Details");
        System.out.println("8- Show all Music");
        System.out.println("9- Show Musics in Stock");
        System.out.println("10- Sign Out");

        Scanner input = new Scanner(System.in);
        int no = input.nextInt();

        switch (no) {
            case 1:
                addCategory();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 2:
                editCategory();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 3:
                removeCategory();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 4:
                AddMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 5:
                editMusicDescription();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 6:
                removeMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 7:
                showMusicDetails();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 8:
                showAllMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 9:
                showMusicsInStock();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 10:
                System.out.println("\nSigned out successfully");
                login();
                break;
        }
    }

    public void addCategory() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease Enter Category Name");
        String category = input.nextLine();

        try {
            preparedStatement = connection.prepareStatement("insert into category(categoryName)values(?)");
            preparedStatement.setString(1, category);
            preparedStatement.executeUpdate();
            System.out.println("\nCategory is added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editCategory() {
        System.out.println("\nPlease Enter Category Name");
        Scanner input = new Scanner(System.in);
        String categoryName = input.nextLine();
        String categoryName1;
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(categoryName, statement)) {
                System.out.println("Enter New Category Name");
                categoryName1 = input.nextLine();
            } else {
                System.out.println("\nCategory Not Found");
                return;
            }
            ResultSet resultSet;
            preparedStatement = connection.prepareStatement("update category set categoryName = '" + categoryName1 + "' WHERE categoryName = '" + categoryName + "'");
            preparedStatement.executeUpdate();
            resultSet = statement.executeQuery("SELECT * FROM music");
            while (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update music set category = '" + categoryName1 + "' WHERE category = '" + categoryName + "'");
                preparedStatement.executeUpdate();
            }
            System.out.println("\nCategory Name Changed Successfully");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void removeCategory() {
        System.out.println("Warning! Removing category will remove all related musics");
        System.out.println("\nPlease Enter Category Name");
        Scanner input = new Scanner(System.in);
        String categoryName = input.nextLine();

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM category where categoryName = '" + categoryName + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM music WHERE category = '" + categoryName + "'");
            preparedStatement.executeUpdate();
            System.out.println("Category and its Events are removed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddMusic() {
        try {
            Statement statement = connection.createStatement();
            System.out.println("\nPlease Enter Category Name");
            Scanner input = new Scanner(System.in);
            String categoryName = input.nextLine();
            if (Category.isCategoryExist(categoryName, statement)) {
                System.out.println("Enter Music Name");
                String musicName = input.nextLine();
                System.out.println("Enter Number of minuets");
                int minuets = input.nextInt();
                System.out.println("Enter Number of seconds");
                int seconds = input.nextInt();
                System.out.println("Enter a description");
                String description = input.next();
                input.nextLine();
                System.out.println("Enter Day of release date");
                int day = input.nextInt();
                System.out.println("Enter Month");
                int month = input.nextInt();
                System.out.println("Enter Year");
                int year = input.nextInt();
                System.out.println("Enter Quantity of This Music in Store");
                int quantity = input.nextInt();
                System.out.println("Enter price");
                float price = input.nextInt();

                preparedStatement = connection.prepareStatement("insert into music(category, musicName, minutes, seconds, description, day, month, year,quantity, price)values(?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, categoryName);
                preparedStatement.setString(2, musicName);
                preparedStatement.setInt(3, minuets);
                preparedStatement.setInt(4, seconds);
                preparedStatement.setString(5, description);
                preparedStatement.setInt(6, day);
                preparedStatement.setInt(7, month);
                preparedStatement.setInt(8, year);
                preparedStatement.setInt(9, quantity);
                preparedStatement.setFloat(10, price);
                preparedStatement.executeUpdate();
                System.out.println("\nMusic added successfully.");

            } else
                System.out.println("Category Not Found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editMusicDescription(){
        try {
            Statement statement = connection.createStatement();
            System.out.println("\nPlease Enter Music Name");
            Scanner input = new Scanner(System.in);
            String musicName = input.nextLine();
            if (Music.isMusicExist(musicName, statement)) {
                System.out.println("Enter new description");
                String description = input.nextLine();
                preparedStatement = connection.prepareStatement("update music set description = '" + description + "' WHERE musicName = '" + musicName + "'");
                preparedStatement.executeUpdate();
                System.out.println("\nDescription updated successfully.");
            } else
                System.out.println("Category Not Found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeMusic() {
        try {
            Statement statement = connection.createStatement();
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Music name");
            String musicName = input.nextLine();
            if (Music.isMusicExist(musicName, statement)) {
                preparedStatement = connection.prepareStatement("DELETE FROM music WHERE musicName = '" + musicName + "'");
                preparedStatement.executeUpdate();
                System.out.println("Music Removed Successfully");
            } else
                System.out.println("\nMusic Not Found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMusicDetails() {
        try {
            Statement statement = connection.createStatement();
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Music name");
            String musicName = input.nextLine();
            if (Music.isMusicExist(musicName, statement)) {
                Music.musicDetails(statement, musicName);
            } else
                System.out.println("Music not Exist");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllMusics() {
        try {
            Statement statement = connection.createStatement();
            Music.showAllMusics(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMusicsInStock() {
        try {
            Statement statement = connection.createStatement();
            Music.showMusicsInStock(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void customerOperations() {
        System.out.println("\nPlease Enter The Operation Number:");

        System.out.println("1- Search Music");
        System.out.println("2- Filter Musics (sold items, item in stock, all items)");
        System.out.println("3- Show Music Details");
        System.out.println("4- Show all Musics");
        System.out.println("5- Create Order");
        System.out.println("6- Show Cart");
        System.out.println("7- Delete Music From Cart");
        System.out.println("8- Checkout Orders");
        System.out.println("9- Sign Out");

        Scanner input = new Scanner(System.in);
        int no = input.nextInt();

        switch (no) {
            case 1:
                searchMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 2:
                filterMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 3:
                showMusicDetails();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 4:
                showAllMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 5:
                createOrder();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 6:
                getMusicInCart();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 7:
                deleteMusicFormCart();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 8:
                checkoutOrders();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 9:
                System.out.println("\nSigned out successfully");
                login();
                break;
        }
    }

    public void createOrder() {
        System.out.print("Enter music name: ");
        Scanner input = new Scanner(System.in);
        String musicName = input.nextLine();
        try {
            Statement statement = connection.createStatement();
            if (Music.isMusicExist(musicName, statement)) {
               cart.add(musicName);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void getMusicInCart() {
        int i;
        for (i = 0; i < cart.size(); i++) {
            System.out.println(i + 1 + " - " + cart.get(i) + "\n");
        }
        if (cart.size() > 0)
            System.out.println("Your Bill: " + getBill() + "$");
    }

    public float getBill() {
        float total = 0;
        for (int i = 0; i < cart.size(); i++){
            String musicName = cart.get(i);
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT price FROM music WHERE musicName = '" + musicName + "'");
                float price = resultSet.getFloat(1);
                total += price;
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
        return total;
    }
    public void deleteMusicFormCart() {
        System.out.println("Enter music name");
        Scanner input = new Scanner(System.in);
        String musicName = input.nextLine();
        for (int i = 0; i < cart.size(); i++){
            if (cart.get(i).equals(musicName)) {
                cart.remove(i);
                break;
            } else {
                System.out.println("This Music doesn't exist in your cart");
            }
        }
    }
    
    public void checkoutOrders() {
        System.out.println("------ Your Cart ------");
        getMusicInCart();
        System.out.println("To Confirm your Order Enter y else Enter n");
        Scanner input = new Scanner(System.in);
        char s = input.nextLine().charAt(0);
        if (s == 'y' || s == 'Y') {
            try {
                Statement statement = connection.createStatement();
                if (cart.size() > 0) {
                    for (int i = 0; i < cart.size(); i++) {
                        ResultSet resultSet = statement.executeQuery("SELECT quantity FROM music WHERE musicName = '" + cart.get(i) + "'");
                        int quantity = resultSet.getInt(1);
                        preparedStatement = connection.prepareStatement("UPDATE music SET quantity = '"+ (quantity - 1) +"'WHERE musicName = '" + cart.get(i) + "'");
                        preparedStatement.executeUpdate();
                        System.out.println("Purchase Completed Successfully");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void filterMusics() {
        System.out.println("Enter number of sorting you want\n" +
                "1- sold musics\n" +
                "2- music in stock" +
                "\n3- all musics");
        Scanner input = new Scanner(System.in);
        int no = input.nextInt();


        switch (no) {
            case 1:
                showSoldMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 2:
                showMusicsInStock();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 3:
                showAllMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            default:
                System.out.println("Wrong input");
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
        }
    }

    public void showSoldMusics() {
        try {
            Statement statement = connection.createStatement();
            Music.showSoldMusics(statement);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void searchMusic() {
        try {
            Statement statement = connection.createStatement();
            System.out.println("\nPlease Enter Music Name");
            Scanner input = new Scanner(System.in);
            String musicName = input.nextLine();
            Music.searchMusic(statement, musicName);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

