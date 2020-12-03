package ru.durnov.warehouse.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.durnov.warehouse.daoservice.StoreDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.List;

public class TestStoreDaoService {

    @Test
    public void testThatEntityListFromStoreDaoServiceIsNotNull() throws SQLException {
        StoreDaoService storeDaoService = new StoreDaoService(new RealDataBase());
        List<Entity> storeList = storeDaoService.getAllEntity();
        System.out.println(storeDaoService);
        Assert.assertNotNull(storeDaoService.getAllEntity());
        System.out.println(storeDaoService.getAllEntity());
    }
}
