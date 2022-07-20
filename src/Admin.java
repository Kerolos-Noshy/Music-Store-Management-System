public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
