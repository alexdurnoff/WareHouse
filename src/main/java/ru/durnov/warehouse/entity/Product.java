package ru.durnov.warehouse.entity;

public class Product extends Entity{
    private double weight;
    private double coast;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj.getClass() == Product.class)) return false;
        Product product = (Product) obj;
        return this.getTitle().equals(product.getTitle());
    }
}
