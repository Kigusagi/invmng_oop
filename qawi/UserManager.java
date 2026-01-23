package qawi;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> userList = new ArrayList<>();
    private User loggedInUser;

    public UserManager() {
        userList.add(new Admin("admin", "admin123"));
        userList.add(new Staff("staff", "staff123"));
    }

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

    public boolean canAddItem() {
        return loggedInUser != null && loggedInUser.canAddItem();
    }

    public boolean canUpdateQuantity() {
        return loggedInUser != null && loggedInUser.canUpdateQuantity();
    }

    public void registerUser(User user) {
        userList.add(user);
    }

    public void simulateAdminLogin() {
        authenticate("admin", "admin123");
    }

    public String getUserStatusMessage() {
        User currentUser = getLoggedInUser();
        if (currentUser != null) {
            return "Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")";
        }
        return "Welcome to the Inventory Management System";
    }
}

