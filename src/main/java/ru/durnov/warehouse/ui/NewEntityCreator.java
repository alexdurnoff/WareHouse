package ru.durnov.warehouse.ui;

import javafx.scene.control.Button;
import ru.durnov.warehouse.entity.Entity;

import java.sql.SQLException;

public abstract class NewEntityCreator extends SimpleEntityEdit {
    /**
     * Abstract class for create new Entity and add this Entity to the DAO
     * @param pane
     * @param entity
     */
    private Button buttonAd;
    protected String addButtonTitle;

    protected NewEntityCreator(AbstractPane pane, Entity entity) {
        super(pane, entity);
    }

    @Override
    public void show() {
        super.show();
        this.button.setText(addButtonTitle);
        this.button.setOnAction(ae -> {
            setupEntityProrepties(entity);
            try {
                pane.entityDaoService.addEntity(entity);
            } catch (SQLException ignored) {

            }
            try {
                pane.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            stage.close();
        });
    }
}
