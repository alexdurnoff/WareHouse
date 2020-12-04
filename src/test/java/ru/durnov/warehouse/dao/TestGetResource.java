package ru.durnov.warehouse.dao;

import org.junit.Test;
import ru.durnov.warehouse.ui.WareHouseApplication;

public class TestGetResource {
    @Test
    public void testGetResource(){
        String url = getClass().getResource("/resource/db/db.sqlite3").toExternalForm();
        System.out.println(url);
    }
}
