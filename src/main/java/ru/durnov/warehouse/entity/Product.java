package ru.durnov.warehouse.entity;

public class Product extends Entity{
    private double weight;
    private double coast;
    private int numberInOrder;

    public Product(String title){
        super(title);
    }

    public Product(String title, double coast){
        super(title);
        this.coast = coast;
        this.weight = 0.;
    }

    public Product(String title, double coast, double weight){
        super(title);
        this.coast = coast;
        this.weight =weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCoast() {
        return coast;
    }

    public void setCoast(double coast) {
        this.coast = coast;
    }

    public void setNumberInOrder(int numberInOrder){
        this.numberInOrder = numberInOrder;
    }

    public int getNumberInOrder(){return numberInOrder;}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj.getClass() == Product.class)) return false;
        Product product = (Product) obj;
        return this.getTitle().equals(product.getTitle());
    }
}
