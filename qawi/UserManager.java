import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> userList = new ArrayList<>();
    private User loggedInUser;

    public UserManager() {
        // Hardcoded users for testing
        userList.add(new Admin("admin", "admin123"));
        userList.add(new Staff("staff", "staff123"));
    }

    // Login system
    public boolean authenticate(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Permission checking (used by Main/Menu)
    public boolean canAddItem() {
        return loggedInUser != null && loggedInUser.canAddItem();
    }

    public boolean canUpdateQuantity() {
        return loggedInUser != null && loggedInUser.canUpdateQuantity();
    }

    // Optional: register new users (Admin feature)
    public void registerUser(User user) {
        userList.add(user);
    }
}

