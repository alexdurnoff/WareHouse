package ru.durnov.warehouse.entity;

public class StoreProductPair {
    private Store store;
    private Product product;

    public StoreProductPair(Store store, Product product){
        this.store = store;
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj.getClass() == StoreProductPair.class)) return false;
        StoreProductPair storeProductPair = (StoreProductPair) obj;
        return ((this.store.equals(storeProductPair.store)) && (this.product.equals(storeProductPair.product)));
    }
}
