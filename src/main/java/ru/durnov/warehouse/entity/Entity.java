package ru.durnov.warehouse.entity;

public abstract class Entity {
    private String title;

    protected Entity(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected void SetTitle(String title){
        this.title = title;
    }




}
