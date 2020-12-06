package ru.durnov.warehouse.ui;

import javafx.scene.control.ChoiceDialog;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.Optional;

public class StoreChoiceDialog implements StoreSetupForOrderPane{
    private OrderForm orderForm;

    public StoreChoiceDialog(OrderForm orderForm){
        this.orderForm = orderForm;
    }

    @Override
    public void setStoreForOrderPane() throws SQLException {
        ChoiceDialog<Entity> storeChoiceDialog = new ChoiceDialog<>();
        storeChoiceDialog.setHeaderText("Выберите магазин для создания накладной");
        storeChoiceDialog.setTitle("Выбор магазина");
        storeChoiceDialog.setContentText("Магазин");
        storeChoiceDialog.getItems().addAll(this.orderForm.getStoreDaoService().getAllEntity());
        storeChoiceDialog.setSelectedItem(storeChoiceDialog.getItems().get(0));
        Optional<Entity> entity = storeChoiceDialog.showAndWait();
        Store store = (Store) entity.get();
        this.orderForm.setStore(store);
    }
}
