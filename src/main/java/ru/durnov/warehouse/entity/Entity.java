package ru.durnov.warehouse.entity;

public abstract class Entity {
    private String title;

    protected Entity(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){this.title = title;}

    protected void SetTitle(String title){
        this.title = title;
    }




}
