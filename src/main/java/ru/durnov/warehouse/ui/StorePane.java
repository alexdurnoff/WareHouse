package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.util.List;

public class StorePane extends AbstractPane {
    private List<Entity> storeList;
    private EntityDao storeDao;
    private Button addStore;

    public StorePane(EntityDao storeDao){
        super();
        super.setEntityButtonTitle("Добавить магазин");
        this.storeDao = storeDao;
        this.storeList = storeDao.getAllEntity();
        this.addStore = new Button("Добавить магазин");
    }

    public void constructNewStore(){

    }

    private void  setColumns(){
        ObservableList<ColumnConstraints> columns = this.getColumnConstraints();
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
    }

    @Override
    public void show(){
        for (int i = 0; i < storeList.size(); i++){
            Store store = (Store) storeList.get(i);
            Label label = new Label(String.valueOf(i));
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
        this.storeDao.getEntityByTitle(store.getTitle());
        this.storeList = this.storeDao.getAllEntity();
    }

    public Button getAddStore() {
        return addStore;
    }

    @Override
    public void addEntityToEntityList(){

    }
}
