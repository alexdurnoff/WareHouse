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
    private EntityDao storeDao;

    public StorePane(EntityDao storeDao){
        super();
        super.setEntityButtonTitle("Добавить магазин");
        this.storeDao = storeDao;
        this.storeList = storeDao.getAllEntity();
    }

    public void refresh(){
        this.getChildren().clear();
        this.show();
    }

    public void constructNewStore(){

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
            this.add(new RemoveStoreButton(store), 2, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    class RemoveStoreButton extends Button{
        private Store store;

        RemoveStoreButton(Store store){
            super("Удалить магазин");
            this.store = store;
            this.setOnAction(ae -> removeStoreFromStoreList(this.store));
        }
    }

    private void removeStoreFromStoreList(Store store) {
        String message = "Вы уверены, что хотите удалить этот магазин?";
        new AYouSurePane(message, this, store).show();
        this.storeList = this.storeDao.getAllEntity();
        //this.refresh();
    }

    @Override
    public void addEntityToEntityList(){

    }
    public void removeEntityByTitle(Entity entity){
        this.storeDao.removeEntityByTitle(entity.getTitle());
        this.storeList.remove(entity);
    }
}
