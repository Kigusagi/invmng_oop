public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override
    public boolean canAddItem() {
        return true;
    }

    @Override
    public boolean canUpdateQuantity() {
        return true;
    }
}

