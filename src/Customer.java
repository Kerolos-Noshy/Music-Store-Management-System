import java.util.ArrayList;

public class Customer extends User{

    public Customer(String username, String password) {
        super(username, password);
    }

    private ArrayList<Music> cart = new ArrayList<>();

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void addToCart(Music m) {
        cart.add(m);
    }

    public void deleteFromCart(Music m) {
        cart.add(m);
    }
}
