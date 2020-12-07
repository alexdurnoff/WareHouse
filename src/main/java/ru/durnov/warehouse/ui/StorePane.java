package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.entity.StoreComparator;

import java.sql.SQLException;
import java.util.List;

public class StorePane extends AbstractPane {
    private List<Entity> storeList;

    public StorePane(EntityDaoService storeDao){
        super();
        super.setEntityButtonTitle("Добавить магазин");
        this.entityDaoService = storeDao;
        this.message = "Вы действительно хотите удалить этот магазин?";
        this.removeEntityMessage = "Удалить";
    }


    public void constructNewStore(){
        new StoreCreator(this).show();
    }

    @Override
    public void addEntityToEntityList() throws SQLException {
        constructNewStore();
        this.storeList = this.entityDaoService.getAllEntity();
        this.storeList.sort(new StoreComparator());
    }

    @Override
    public void show() throws SQLException {
        this.storeList = entityDaoService.getAllEntity();
        this.storeList.sort(new StoreComparator());
        addHeader();
        for (int i = 0; i < storeList.size(); i++){
            Store store = (Store) storeList.get(i);
            Label label = new Label(String.valueOf(i+1));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i+1);
            this.add(new TextField(storeList.get(i).getTitle()), 1, i+1);
            //this.add(new EditStoreButton(store), 2, i+1); Отказался. Бессмысленно. Только название редактировать. Проще добавить новый...
            this.add(new RemoveEntityButton(store), 2, i+1);
        }
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addHeader() {
        Label numberLabel = new Label("№ п.п");
        numberLabel.setPrefWidth(40);
        numberLabel.setPrefHeight(40);
        numberLabel.setAlignment(Pos.CENTER);
        this.add(numberLabel, 0 ,0);
        Label storeTitleLabel = new Label("Наименование магазина");
        storeTitleLabel.setAlignment(Pos.CENTER);
        storeTitleLabel.setPrefWidth(200);
        this.add(storeTitleLabel, 1, 0);
    }

    class EditStoreButton extends Button {
        private Store store;

        EditStoreButton(Store store){
            super("Редактировать магазин");
            this.store = store;
            this.setOnAction(ae -> editStore(store));
        }

    }

    private void editStore(Store store) {
        new StoreEditor(this, store).show();
    }
}
