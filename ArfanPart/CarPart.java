package ArfanPart;

public class CarPart extends Item {
    private String modelCompatibility;

    public CarPart(String id, String name, int quantity, double price, String modelCompatibility) {
        super(id, name, quantity, price);
        this.modelCompatibility = modelCompatibility;
    }

    public String getModelCompatibility() {
        return modelCompatibility;
    }

    public void setModelCompatibility(String modelCompatibility) {
        this.modelCompatibility = modelCompatibility;
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + getQuantity() + "," + getPrice() + "," + modelCompatibility;
    }
}