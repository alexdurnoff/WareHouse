package ru.durnov.warehouse.entity;

public class ProductWrapper {
    private Product product;
    private Integer id;
    private Double weigth;
    private Double coast;
    private Double summ;
    private String title;
    private String convert;

    public ProductWrapper(Product product, int id, double weigth, double coast){
        this.product = product;
        this.id = id;
        this.weigth = weigth;
        this.coast = coast;
        this.summ = weigth * coast;
        this.title = this.product.getTitle();
        this.convert = String.format("%.2f", this.weigth);
    }

    public Product getProduct() {
        return product;
    }

    public Integer getId() {
        return id;
    }

    public void setWeigth(Double weigth) {
        this.weigth = weigth;
        this.summ =this.weigth * this.coast;
        this.convert = String.format("%.2f", this.weigth);
    }

    public void setCoast(Double coast) {
        this.coast = coast;
        this.summ =this.weigth * this.coast;
    }

    public String getTitle(){
        return this.title;
    }

    public String getWeight(){
        return String.format("%.2f", this.weigth);
    }

    public String getCoast(){
        return String.format("%.2f", this.coast);
    }

    public String getSum(){
        return String.format("%.2f", this.summ);
    }

    public String getConvert(){
        return this.convert;
    }

    public void reiseId(int number){
        this.id = this.id + number;
    }
}
