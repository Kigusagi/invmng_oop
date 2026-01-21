public class Staff extends User {

    public Staff(String username, String password) {
        super(username, password, "STAFF");
    }

    @Override
    public boolean canAddItem() {
        return false;
    }

    @Override
    public boolean canUpdateQuantity() {
        return true;
    }
}

