class ComputerPart extends Item {
    private String brand;
    private String specs;

    public ComputerPart(String id, String name, int quantity, double price, String brand, String specs) {
        super(id, name, quantity, price);
        this.brand = brand;
        this.specs = specs;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + getQuantity() + "," + getPrice() + "," + brand + "," + specs;
    }
}