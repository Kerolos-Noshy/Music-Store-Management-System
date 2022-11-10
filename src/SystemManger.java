import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemManger {
    Connection connection = DbConnection.ConnectionDB();
    PreparedStatement preparedStatement;
    ArrayList <String> cart = new ArrayList<>();

    String username;

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
        int serialNum;
        try {
            preparedStatement = connection.prepareStatement("insert into customers(username, password)values(?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT customer_id from customers WHERE username ='" + username + "'");
            serialNum = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nCongratulations you have registered your account and your serial number is " + serialNum +".\nNow please sign in to your account.\n");
        login();

    }

    public boolean signIn(char type) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease Enter Your Username");
        username = input.next();
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
                        if (resultSet.getString(2).equals(username) && resultSet.getString(3).equals(password))
                            return true;
                    }
                } catch (SQLException e) {
                    System.out.println("Wrong name or password");
                }
        }
        return false;
    }

    public void adminOperations() {
        System.out.println("""
                Please Enter Operation Number:
                
                1- Manage Categories (add, edit, remove)
                2- Manage Musics (add, edit, remove)
                3- Filter Musics (Not available items, item in stock, all items)
                4- Show Music Details
                5- Search Music
                6- Show Sales Report
                7- Sign Out
                """);

        Scanner input = new Scanner(System.in);
        int no = input.nextInt();

        switch (no) {
            case 1:
                manageCategory();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 2:
                manageMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 3:
                filterMusics();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 4:
                showMusicDetails();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 5:
                searchMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 6:
                showSalesReport();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 7:
                System.out.println("\nSigned out successfully");
                login();
                break;
        }
    }

    public void customerOperations() {
        System.out.println("\nPlease Enter The Operation Number:");

        System.out.println("""
                1- Search Music
                2- Show Music Details
                3- Show all Musics
                4- Create Order
                5- Show Cart
                6- Delete Music From Cart
                7- Checkout Orders
                8- Open Profile
                9- Open History
                10- Sign Out""");

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
                showMusicDetails();
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
            case 4:
                createOrder();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 5:
                getMusicInCart();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 6:
                deleteMusicFormCart();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 7:
                checkoutOrders();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 8:
                showProfile();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 9:
                showCustomerHistory();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                customerOperations();
                break;
            case 10:
                System.out.println("\nSigned out successfully");
                login();
                break;
        }
    }

    public void manageCategory() {
        System.out.println("""
                
                Please Enter Operation Number:
                1- Add Category
                2- Edit Category
                3- Remove Category
                """);
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
        }
    }

    public void addCategory() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease Enter Category Name");
        String category = input.nextLine();

        try {
            Statement statement = connection.createStatement();
            if (!Category.isCategoryExist(category, statement)) {
                preparedStatement = connection.prepareStatement("insert into category(category_id, category_name)values(?, ?)");
                preparedStatement.setString(2, category);
                preparedStatement.executeUpdate();
                System.out.println("\nCategory is added successfully.");
            } else {
                System.out.println("Category already exist");
            }
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
            preparedStatement = connection.prepareStatement("update category set category_name = '" + categoryName1 + "' WHERE category_name = '" + categoryName + "'");
            preparedStatement.executeUpdate();
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT category_id FROM category WHERE category_name = '" + categoryName + "'");
            int categoryId = resultSet.getInt(1);
            preparedStatement = connection.prepareStatement("DELETE FROM category where category_name = '" + categoryName + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM music WHERE category_id = '" + categoryId + "'");
            preparedStatement.executeUpdate();
            System.out.println("Category and its Events are removed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void manageMusics(){
        System.out.println("""
                
                Please Enter Operation Number:
                1- Add Music
                2- Edit Music
                3- Remove Music
                """);
        Scanner input = new Scanner(System.in);
        int no = input.nextInt();

        switch (no) {
            case 1:
                addMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 2:
                editMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;

            case 3:
                removeMusic();
                System.out.println("\nPress Enter to Continue");
                input.nextLine();
                input.nextLine();
                adminOperations();
                break;
        }
    }

    public void addMusic() {
        try {
            Statement statement = connection.createStatement();
            System.out.println("\nPlease Enter Category Name");
            Scanner input = new Scanner(System.in);
            String categoryName = input.nextLine();
            if (Category.isCategoryExist(categoryName, statement)) {
                ResultSet resultSet = statement.executeQuery("SELECT category_id FROM category Where category_name = '" + categoryName + "'");
                int categoryId = resultSet.getInt(1);
                System.out.println("Enter Music Name");
                String musicName = input.nextLine();
                if (!Music.isMusicExist(musicName, statement)) {
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
                    preparedStatement = connection.prepareStatement("insert into music(category_id, music_id, music_name, minutes, seconds, description, day, month, year,quantity, price)values(?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setInt(1, categoryId);
                    preparedStatement.setString(3, musicName);
                    preparedStatement.setInt(4, minuets);
                    preparedStatement.setInt(5, seconds);
                    preparedStatement.setString(6, description);
                    preparedStatement.setInt(7, day);
                    preparedStatement.setInt(8, month);
                    preparedStatement.setInt(9, year);
                    preparedStatement.setInt(10, quantity);
                    preparedStatement.setFloat(11, price);
                    preparedStatement.executeUpdate();
                    resultSet = statement.executeQuery("SELECT music_id FROM music Where music_name = '" + musicName + "'");
                    int musicId = resultSet.getInt(1);
                    preparedStatement = connection.prepareStatement("insert into sold_items(music_id, sold_times)values(?,?)");
                    preparedStatement.setInt(1, musicId);
                    preparedStatement.setInt(2, 0);
                    System.out.println("\nMusic added successfully.");
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("Music already exist");
                }
            } else
                System.out.println("Category Not Found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editMusic() {
        try {
            Statement statement = connection.createStatement();
            Scanner input = new Scanner(System.in);
            System.out.println("Please Enter Music Name");
            String musicName = input.nextLine();
            if (Music.isMusicExist(musicName, statement)){
                System.out.println("Please Choose An Option:");
                System.out.println("1- Edit Music Category");
                System.out.println("2- Edit Music Name");
                System.out.println("3- Edit Description");
                System.out.println("4- Edit Release Date");
                System.out.println("5- Edit Duration");
                System.out.println("6- Edit Quantity");
                System.out.println("7- Edit Price");

                int no = input.nextInt();
                input.nextLine();

                switch (no) {
                    case 1:
                        editMusicCategory(musicName);
                        break;

                    case 2:
                        editMusicName(musicName);
                        break;

                    case 3:
                        editMusicDescription(musicName);
                        break;

                    case 4:
                        editMusicReleaseDate(musicName);
                        break;

                    case 5:
                        editMusicDuration(musicName);
                        break;

                    case 6:
                        editMusicQuantity(musicName);
                        break;

                    case 7:
                        editMusicPrice(musicName);
                        break;
                }
            }
            else
                System.out.println("\nMusic Not Found");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicCategory(String musicName){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter New Category Name: ");
        String newCategoryName = input.nextLine();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT category_id FROM catgory WHERE category_name = '" + newCategoryName + "'");
            int newCategoryId = resultSet.getInt(1);
            preparedStatement = connection.prepareStatement("update music set category_id = '" + newCategoryId + "' WHERE music_name = '" + musicName + "'");
            preparedStatement.executeUpdate();
            System.out.println("Category changed successfully.");
            input.close();
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicName(String musicName) {
        System.out.println("Enter Music Name: ");
        Scanner input = new Scanner(System.in);
        String newMusicName = input.nextLine();
        try {
            preparedStatement = connection.prepareStatement("update music set music_name = '" + newMusicName + "' WHERE music_name = '" + musicName + "'");
            preparedStatement.executeUpdate();
            System.out.println("Name changed successfully.");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicReleaseDate(String musicName) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter New Date");
        System.out.println("Enter Day");
        int day = input.nextInt();
        System.out.println("Enter Month");
        int month = input.nextInt();
        System.out.println("Enter Year");
        int year = input.nextInt();
        try {
            preparedStatement = connection.prepareStatement("update music set day = '" + day + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update music set month = '" + month + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update music set year = '" + year + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            System.out.println("Release Date changed successfully.");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicDuration(String musicName) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter New Duration");
        System.out.println("Enter no. of Minuets: ");
        int minuets = input.nextInt();
        System.out.println("Enter no. of Seconds: ");
        int seconds = input.nextInt();
        try {
            preparedStatement = connection.prepareStatement("update music set minutes = '" + minuets + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update music set seconds = '" + seconds + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            System.out.println("Duration changed successfully.");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicQuantity(String musicName) {
        System.out.println("Enter Quantity: ");
        Scanner input = new Scanner(System.in);
        String quantity = input.nextLine();
        try {
            preparedStatement = connection.prepareStatement("update music set quantity = '" + quantity + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            System.out.println("Quantity changed successfully.");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicPrice(String musicName) {
        System.out.println("Enter Price: ");
        Scanner input = new Scanner(System.in);
        String price = input.nextLine();
        try {
            preparedStatement = connection.prepareStatement("update music set price = '" + price + "' WHERE music_name = '" + musicName  + "'");
            preparedStatement.executeUpdate();
            System.out.println("Price changed successfully.");
        } catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }

    public void editMusicDescription(String musicName){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter new description");
            String description = input.next();
            input.nextLine();
            preparedStatement = connection.prepareStatement("update music set description = '" + description + "' WHERE music_name = '" + musicName + "'");
            preparedStatement.executeUpdate();
            System.out.println("\nDescription updated successfully.");
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
                preparedStatement = connection.prepareStatement("DELETE FROM music WHERE music_name = '" + musicName + "'");
                preparedStatement.executeUpdate();
                System.out.println("Music Removed Successfully");
            } else
                System.out.println("\nMusic Not Found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showSalesReport() {
        try {
            int noOfSoldItems = 0;
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT music_name, sold_times FROM sold_items JOIN music on music.music_id = sold_items.music_id");
            System.out.println("--------- Sold Times ---------");
            while (resultSet.next()) {
                noOfSoldItems += resultSet.getInt(2);
                System.out.println("- " + resultSet.getString(1) + ": " + resultSet.getInt(2));
            }
            resultSet = statement.executeQuery("SELECT max(sold_times) FROM sold_items");
            int maxTimes = resultSet.getInt(1);
            resultSet = statement.executeQuery("SELECT music_name FROM sold_items JOIN music on music.music_id = sold_items.music_id WHERE sold_times = " + maxTimes);
            System.out.println("-----------------------------\nBest Seller: " + resultSet.getString(1));
            System.out.println("Total sold musics: "  + noOfSoldItems);
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

    public boolean isMusicInCart(String musicName) {
        boolean flag = false;
        for (String music : cart) {
            if (music.equals(musicName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void createOrder() {
        System.out.print("Enter music name: ");
        Scanner input = new Scanner(System.in);
        String musicName = input.nextLine();
        try {
            Statement statement = connection.createStatement();
            if (Music.isMusicExist(musicName, statement))
                if (Music.isMusicInStock(statement, musicName))
                    if (isMusicInCart(musicName))
                        System.out.println(musicName + " Already Exist in Cart");
                    else {
                        cart.add(musicName);
                        System.out.println("\nMusic Added to cart\n" +
                                "To continue shopping enter 's'\n" +
                                "To checkout orders enter 'c'");
                        char op = input.nextLine().charAt(0);
                        switch (op) {
                            case 'S':
                            case 's':
                                customerOperations();
                                break;
                            case 'C':
                            case 'c':
                                checkoutOrders();
                                break;
                        }
                    }
                else
                    System.out.println("This Music isn't Available Now");
            else
                System.out.println("This Music Doesn't Exist");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void getMusicInCart() {
        if (cart.size() > 0) {
            for (int i = 0; i < cart.size(); i++) {
                System.out.println(i + 1 + " - " + cart.get(i));
            }
            System.out.println("Your Bill: " + getBill() + "$");
        } else
            System.out.println("Your Cart is empty");
    }

    public float getBill() {
        float total = 0;
        for (String musicName : cart) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT price FROM music WHERE music_name = '" + musicName + "'");
                float price = resultSet.getFloat(1);
                total += price;
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
        return total;
    }

    public void deleteMusicFormCart() {
        if (cart.size() > 1) {
            System.out.println("Enter music name");
            Scanner input = new Scanner(System.in);
            String musicName = input.nextLine();
            for (int i = 0; i < cart.size(); i++) {
                if (isMusicInCart(musicName)) {
                    cart.remove(i);
                    System.out.println("Music removed successfully");
                    break;
                } else {
                    System.out.println("This Music doesn't exist in your cart");
                }
            }
        } else {
            System.out.println("Your Cart is Empty");
        }
    }
    
    public void checkoutOrders() {
        if (cart.size() > 0) {
            System.out.println("------ Your Cart ------");
            getMusicInCart();
            System.out.println("To Confirm your Order Enter 'y' else Enter 'n'");
            Scanner input = new Scanner(System.in);
            char s = input.nextLine().charAt(0);
            if (s == 'y' || s == 'Y') {
                try {
                    Statement statement = connection.createStatement();
                    for (String musicName : cart) {
                        ResultSet resultSet = statement.executeQuery("SELECT quantity FROM music WHERE music_name = '" + musicName + "'");
                        int quantity = resultSet.getInt(1);
                        preparedStatement = connection.prepareStatement("UPDATE music SET quantity = '" + (quantity - 1) + "'WHERE music_name = '" + musicName + "'");
                        preparedStatement.executeUpdate();
                        resultSet = statement.executeQuery("SELECT music_id FROM music WHERE music_name = '" + musicName +"'");
                        int musicId = resultSet.getInt(1);
                        ResultSet resultSet1 = statement.executeQuery("SELECT sold_times FROM sold_items WHERE music_id = '" + musicId + "'");
                        int soldTimes = resultSet1.getInt(1);
                        preparedStatement = connection.prepareStatement("UPDATE sold_items SET sold_times = '" + (soldTimes + 1) + "'WHERE music_id = '" + musicId + "'");
                        preparedStatement.executeUpdate();
                        resultSet = statement.executeQuery("SELECT customer_id FROM customers WHERE username = '" + username + "'");
                        int customerId = resultSet.getInt(1);
                        preparedStatement = connection.prepareStatement("insert into customers_history(customer_id, music_id)values(?,?)");
                        preparedStatement.setInt(1, customerId);
                        preparedStatement.setInt(2, musicId);
                        preparedStatement.executeUpdate();
                    }
                    cart.clear();
                    System.out.println("Purchase Completed Successfully");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else
            System.out.println("Your Cart is Empty");
    }

    public void showProfile() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers WHERE username = '" + username + "'");
            String password = resultSet.getString(3);
            String stars = "";
            for (int i = 0; i < password.length() - 2; i++) {
                stars += "*";
            }
            System.out.println("\nUsername: " + resultSet.getString(2)
                    + "\n" + "ID: " + resultSet.getInt(1) + "\n"
                    + "Password: " + stars + password.charAt(password.length()-2) + password.charAt(password.length() - 1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCustomerHistory() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT customer_id from customers WHERE username = '" + username + "'");
            int customerId = resultSet.getInt(1);
            resultSet = statement.executeQuery("SELECT music_name FROM customers_history c join music m on c.music_id = m.music_id WHERE customer_id = '" + customerId + "'");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println(counter + "- " + resultSet.getString(1));
                counter++;
            }
            if (counter < 2) {
                System.out.println("No History");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterMusics() {
        System.out.println("""
                Enter number of sorting type you want:
                1- Not Available musics
                2- Music in stock
                3- All musics""");
        Scanner input = new Scanner(System.in);
        int no = input.nextInt();

        switch (no) {
            case 1:
                showNotAvailableMusics();
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

    public void showNotAvailableMusics() {
        try {
            Statement statement = connection.createStatement();
            Music.showNotAvailableMusics(statement);
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