package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.util.List;

public class StorePane extends AbstractPane {
    private List<Entity> storeList;

    public StorePane(EntityDao storeDao){
        super();
        super.setEntityButtonTitle("Добавить магазин");
        this.entityDao = storeDao;
        this.storeList = storeDao.getAllEntity();
        this.message = "Вы действительно хотите удалить этот магазин?";
        this.removeEntityMessage = "Удалить";
    }

    /*public void refresh(){
        this.getChildren().clear();
        this.show();
    }*/

    public void constructNewStore(){
        Store store = new Store("");
    }

    @Override
    public void addEntityToEntityList() {
        constructNewStore();
    }

    @Override
    public void show(){
        for (int i = 0; i < storeList.size(); i++){
            Store store = (Store) storeList.get(i);
            Label label = new Label(String.valueOf(i+1));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(storeList.get(i).getTitle()), 1, i);
            //this.add(new EditStoreButton(store), 2, i);
            this.add(new RemoveEntityButton(store), 3, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
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

    /*class RemoveEntityButton extends Button{
        private Store store;

        RemoveEntityButton(Store store){
            super("Удалить магазин");
            this.store = store;
            this.setOnAction(ae -> removeEntityFromEntityList(this.store));
        }
    }

    private void removeEntityFromEntityList(Store store) {
        //String message = "Вы уверены, что хотите удалить этот магазин?";
        new AYouSurePane(message, this, store).show();
        this.storeList = this.entityDao.getAllEntity();
        //this.refresh();
    }*/

    /*public void removeEntityByTitle(Entity entity){
        this.storeDao.removeEntityByTitle(entity.getTitle());
        this.storeList.remove(entity);
    }*/
}
