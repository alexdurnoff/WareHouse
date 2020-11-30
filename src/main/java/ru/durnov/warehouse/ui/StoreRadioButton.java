package ru.durnov.warehouse.ui;

import javafx.scene.control.RadioButton;
import ru.durnov.warehouse.entity.Store;

public class StoreRadioButton extends RadioButton {
    private Store store;

    public StoreRadioButton(Store store){
        this.store = store;
    }

    public Store getStore(){return store;}
}
